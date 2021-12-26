import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class Twentyfifth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner("");
		part1(console);
		
	}
	
	public static void part1(Scanner console) {
		//BigInteger card = new BigInteger("5764801");
		BigInteger card = new BigInteger("5099500");
		//BigInteger door = new BigInteger ("17807724");
		BigInteger door = new BigInteger ("7648211");
		long mod = 20201227;
		
		BigInteger subject = new BigInteger("7");
		
		BigInteger val = new BigInteger("1");
		
		BigInteger loopD = new BigInteger("-1");
		BigInteger loopC = new BigInteger("-1");
		
		for (int i = 0; i < mod; i++) {
			val = subject.modPow(new BigInteger(String.valueOf(i)), new BigInteger(String.valueOf(mod)));
			if (val.equals(card)) loopC = new BigInteger(String.valueOf(i));
			if (val.equals(door)) loopD = new BigInteger(String.valueOf(i));
			if (!loopC.equals(new BigInteger("-1")) && !loopD.equals(new BigInteger("-1"))) break;
		}
		
		System.out.println(card + " " + loopC);
		System.out.println(door + " " + loopD);
		
		val = card.modPow(loopD, new BigInteger(String.valueOf(mod)));
		System.out.println(val);
		val = door.modPow(loopC, new BigInteger(String.valueOf(mod)));
		System.out.println(val);
	}
	
	public static void part2(Scanner console) {
		
	}

}
