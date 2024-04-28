import java.util.Map;
import java.math.BigInteger;
import java.util.LinkedHashMap;

public class Division {
	public static Map<Integer, BigInteger> previousCoefficient1;
	public static Map<Integer, BigInteger> previousCoefficient2;
	

	public static BigInteger bezoutIdentity(BigInteger phi, BigInteger r) {
		BigInteger[] bezoutCoeffincient = new BigInteger[3];
		previousCoefficient1 = new LinkedHashMap<Integer, BigInteger>();
		previousCoefficient2 = new LinkedHashMap<Integer, BigInteger>();
		BigInteger tempNum = BigInteger.ZERO;
		int counter = 0;
		BigInteger tmp, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7;
		// first initialization
		bezoutCoeffincient[0] = phi.mod(r);
		bezoutCoeffincient[1] = BigInteger.ONE;
		bezoutCoeffincient[2] = (phi.divide(r)).negate();
		while (phi.mod(r).compareTo(BigInteger.ZERO) != 0) {
			previousCoefficient1.put(counter, bezoutCoeffincient[1]);
			previousCoefficient2.put(counter, bezoutCoeffincient[2]);
			//System.out.println(bezoutCoeffincient[0] + " = " + bezoutCoeffincient[1]+ "x" + phi + " + " + bezoutCoeffincient[2] + "x" + r);
			tempNum = phi;
			phi = r;
			r = tempNum.mod(r);
			bezoutCoeffincient[0] = phi.mod(r);
			if (counter == 0) {
				tmp = phi.divide(r);
				bezoutCoeffincient[1] = bezoutCoeffincient[1].negate().multiply(tmp);
				tmp1 = bezoutCoeffincient[2].negate();
				tmp2 = tmp1.multiply(tmp);
				bezoutCoeffincient[2] = tmp2.add(BigInteger.ONE);
			} else {
				tmp3 = previousCoefficient1.get(counter-1);
				tmp4 = phi.divide(r);
				tmp5 = bezoutCoeffincient[1].multiply(tmp4);
				tmp6 = previousCoefficient2.get(counter-1);
				tmp7 = bezoutCoeffincient[2].multiply(tmp4);
				bezoutCoeffincient[1] =  tmp3.subtract(tmp5);
				bezoutCoeffincient[2] =  tmp6.subtract(tmp7);
			}
			counter++;
		}
		
		return previousCoefficient2.get(counter-1);
	} 
}
