import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		//Keys keys = new Keys(new BigInteger("143"), new BigInteger("67"), new BigInteger("13"), new BigInteger("11"), new BigInteger("43"));
		Keys keys = new Keys();
		String message = "ciao";
		System.out.println("Message sent: " + message);
		keys.encrypt(message);
		BigInteger [] msg = keys.getEncryptedMessage();
		keys.decrypt(msg);
		String deMsg = keys.getDecryptedMessage();
		System.out.println("Message decripted : " + deMsg); 

	}

}
