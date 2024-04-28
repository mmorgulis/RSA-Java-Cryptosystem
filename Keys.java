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

	private static final int keyLenght = 100; //617 for 2048 bit key

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

	public Keys(BigInteger N, BigInteger r, BigInteger p, BigInteger q) {
		this.publicKey = new BigInteger[2];
		this.encodedPublicKey = new String[2];
		this.N = N;
		this.r = r;
		this.publicKey[0] = p.multiply(q);
		this.publicKey[1] = r;
		this.phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		generatePrivateKey();
		this.encodedPublicKey[0] = encode(N);
		this.encodedPublicKey[1] = encode(r);
		this.encodedPrivateKey = encode(privateKey);
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
			//prime = BigInteger.probablePrime(length, rnd);
			prime = new BigInteger(length, 99, rnd);
			if (isPrime(prime))
				testPrime = false;
			else
				testPrime = true;
		} while (testPrime);
		return prime;
	}
	
	private static boolean isPrime(BigInteger n) {
		// Corner case
		if (n.compareTo(BigInteger.ONE) == -1) 
			return false;
	
		// Check from 2 to sqrt(n)
		BigInteger i = new BigInteger("2");
		while (i.compareTo(n.sqrt()) == -1 || i.compareTo(n.sqrt()) == 0) {
			if (n.mod(i).equals(BigInteger.ZERO))
				return false;
			i = i.add(BigInteger.ONE);
		}
		return true;
	}
	
	private void generatePrivateKey(){
		BigInteger tmpPrivateKey = Division.bezoutIdentity(phi, r);
		if (tmpPrivateKey.compareTo(BigInteger.ZERO) == -1){
			tmpPrivateKey = tmpPrivateKey.add(phi);
		}
		privateKey = tmpPrivateKey;

	}

	private String encode(BigInteger num) {
		byte[] bigIntegerBytes = num.toByteArray();
		return encoder.encodeToString(bigIntegerBytes);
	}

	public BigInteger[] encrypt (String message) {
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
		BigInteger modular;
		for (int i = 0; i < intMessage.length; i++) {
			tmpNumber = BigInteger.valueOf(intMessage[i]);	
			modular = SquareAndMultiply.modularExponentation(tmpNumber, r, N);
			encryptedMessage[i] = modular;
		}
		return encryptedMessage;
	}

	public String decrypt (BigInteger[] en) {
		
		decryptedMessage = "";
		char [] tmpMsg = new char[en.length]; //modifica
		BigInteger exp;
		for (int i = 0; i < en.length; i++) {
			exp = SquareAndMultiply.modularExponentation(en[i], privateKey, N);
			tmpMsg[i] = (char) exp.intValue();
		}
		for (int i = 0; i < en.length; i++) {
			decryptedMessage += tmpMsg[i];
		}
		return decryptedMessage;
	}

}
