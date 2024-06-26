import java.math.BigInteger;

public class SquareAndMultiply {
	//efficient resolution for equation like a ^ n mod m
	
	public static BigInteger modularExponentation(BigInteger a, BigInteger n, BigInteger m) {
		//return a.modPow(n, m);
		String tmp = n.toString(2);
		int [] binary = new int[tmp.length()];
		for (int i = 0; i < tmp.length(); i++) {
			binary[i] = tmp.charAt(i)-48;
		}
		return recursiveExp(a, binary, 0, BigInteger.ONE, m); 
	}

	private static BigInteger recursiveExp(BigInteger a2, int [] binary, int index, BigInteger partial, BigInteger m2) {
		if (index == binary.length) {
			return partial;
		} else {
			//System.out.println("calcolo: " + partial + "^" + 2  + " * " + a2 + "^" + binary[index] + " mod " + m2);
			partial = (partial.pow(2).multiply(a2.pow(binary[index]))).mod(m2);
			index++;
			return recursiveExp(a2, binary, index, partial, m2);
		}
	}
}
