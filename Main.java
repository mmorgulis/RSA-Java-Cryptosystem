import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		//Keys keys = new Keys(new BigInteger("187"), new BigInteger("49"), new BigInteger("11"), new BigInteger("17"));
		Keys keys = new Keys();
		String message = "Hello World!";
		System.out.println("Message sent: " + message);
		BigInteger [] msg = keys.encrypt(message);
		String deMsg = keys.decrypt(msg);
		System.out.println("Message decripted : " + deMsg); 

	}

}
