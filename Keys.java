import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;

public class Keys {

	private BigInteger publicKey[]; /* two element: 	f : euler function of pq = (p-1)(q-1)
							 							r : random coprime with f */
	private String encodedPublicKey[];

	private BigInteger N;

	private BigInteger phi;

	private BigInteger r = new BigInteger("65537"); //2^16 +1

	private BigInteger privateKey; //  founded with bezout identity

	private String encodedPrivateKey;

	private static final int keyLenght = 3; 

	private BigInteger p;		// first prime

	private BigInteger q; 		//second prime

	private Base64.Encoder encoder = Base64.getEncoder();

	private String message;

	private BigInteger[] encryptedMessage;

	private String decryptedMessage;

	public BigInteger[] getPublicKey() {
		return publicKey;
	}
	public String[] getEncodedPublicKey() {
		return encodedPublicKey;
	}
	public BigInteger getPrivateKey() {
		return privateKey;
	}
	public String getEncodedPrivateKey() {
		return encodedPrivateKey;
	}
	public BigInteger getN() {
		return N;
	}
	public BigInteger getPhi() {
		return phi;
	}
	public BigInteger getR() {
		return r;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigInteger[] getEncryptedMessage() {
		return encryptedMessage;
	}
	public String getDecryptedMessage() {
		return decryptedMessage;
	}

	public Keys() {
		this.publicKey = new BigInteger[2];
		this.encodedPublicKey = new String[2];
		generateKeys();
	}

	public Keys(BigInteger N, BigInteger r, BigInteger p, BigInteger q, BigInteger s) {
		this.publicKey = new BigInteger[2];
		this.encodedPublicKey = new String[2];
		this.N = N;
		this.publicKey[0] = p.multiply(q);
		this.publicKey[1] = r;
		this.privateKey = s; 
		this.encodedPublicKey[0] = encode(N);
		this.encodedPublicKey[1] = encode(r);
		this.encodedPrivateKey = encode(s);
		this.p = p;
		this.q = q;
		System.out.println("Your public key is the couple (N, r) = ");
		System.out.println(encodedPublicKey[0] + ", " + encodedPublicKey[1]);
		System.out.println("Your private key is: " + encodedPrivateKey);
	}

	public void generateKeys() {
		p = generatePrime(keyLenght/2);
		q = generatePrime(keyLenght/2);

		N = p.multiply(q);
		BigInteger P = p.subtract(BigInteger.ONE);
		BigInteger Q = q.subtract(BigInteger.ONE);
		phi = P.multiply(Q);
		publicKey[0] = N;
		publicKey[1] = r;
		encodedPublicKey[0] = encode(N);
		encodedPublicKey[1] = encode(r);
		
		System.out.println("Your public key is the couple (N, r) = ");
		System.out.println(encodedPublicKey[0] + ", " + encodedPublicKey[1]);
		System.out.println();
		generatePrivateKey();
		encodedPrivateKey = encode(privateKey);
		System.out.println("Your private key is: " + encodedPrivateKey);

	}

	private static BigInteger generatePrime(int length) {
		boolean testPrime = true;
		BigInteger prime;
		Random rnd;
		do {
			rnd = new Random();
			byte[] b = new byte[length];
			rnd.nextBytes(b);
			prime = new BigInteger(b);
			if(isPrime(prime))
				testPrime = false;
			else
				testPrime = true;
		} while(testPrime);
		return prime;
	}

	private static boolean isPrime(BigInteger n) {
		// Corner case
		if (n.compareTo(BigInteger.ONE) == -1) //n <= 1
			return false;

		// Check from 2 to sqrt(n)
		BigInteger i = new BigInteger("2");
		while (i.compareTo(n.sqrt()) == 1){
			if (n.mod(i) == BigInteger.ZERO)
				return false;
		}

		return true;
	}

	private void generatePrivateKey(){
		BigInteger tmpPrivateKey = Division.bezoutIdentity(phi, r);
		if (tmpPrivateKey.compareTo(BigInteger.ZERO) == -1)
			tmpPrivateKey = tmpPrivateKey.add(phi);

		privateKey = tmpPrivateKey;

	}

	private String encode(BigInteger num) {
		byte[] bigIntegerBytes = num.toByteArray();
		return encoder.encodeToString(bigIntegerBytes);
	}

	public void encrypt (String message) {
		// encrypt the message using message^r mod N = encryptedMessage
		// divide the string in char[] and encrypt every char
		encryptedMessage = new BigInteger[message.length()];
		char [] tmpMessage = new char[message.length()];
		tmpMessage = message.toCharArray();
		int [] intMessage = new int[tmpMessage.length];
		for (int i = 0; i < intMessage.length; i++) {
			intMessage[i] = tmpMessage[i];
		}
		BigInteger tmpNumber;
		for (int i = 0; i < intMessage.length; i++) {
			//System.out.println("calcolo: " + intMessage[i]+ "^" + r + " mod " + N);
			tmpNumber = BigInteger.valueOf(intMessage[i]);
			SquareAndMultiply modular = new SquareAndMultiply(tmpNumber, r, N);
			encryptedMessage[i] = modular.getResult();
		}
		
	}

	public void decrypt (BigInteger[] en) {
		//System.out.println("calcolo: " + message + "^" + privateKey + " mod " + N);
		decryptedMessage = "";
		char [] tmpMsg = new char[en.length];
		for (int i = 0; i < en.length; i++) {
			SquareAndMultiply exp = new SquareAndMultiply(en[i], privateKey, N);
			tmpMsg[i] = (char) exp.getResult().intValue();
		}
		for (int i = 0; i < en.length; i++) {
			decryptedMessage += tmpMsg[i];
		}
		
		
	}

}
