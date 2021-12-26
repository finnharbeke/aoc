import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Arrays;

public class Fourteenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input14.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashMap<Long, Long> mem = new HashMap<Long, Long>();
		String mask = console.nextLine().substring("mask = ".length());
		while (console.hasNextLine()) {
			String line = console.nextLine();
			if (line.startsWith("mask")) {
				mask = line.substring("mask = ".length());
			} else {
				long add = Integer.valueOf(line.substring(4, line.indexOf(']')));
				long value = Integer.valueOf(line.substring(line.indexOf('=')+2));
				long[] binary = new long[36];
				int i = 35;
				while (value > 0) {
					binary[i] = value % 2;
					value = value / 2;
					i--;
				}
				for (i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) == 'X') {
						continue;
					} else if (mask.charAt(i) == '1') {
						binary[i] = 1;
					} else if (mask.charAt(i) == '0') {
						binary[i] = 0;
					} else {
						System.out.println("WTF");
					}
				}
				System.out.print(value + ": ");
				System.out.print(Arrays.toString(binary));
				long base = 1;
				long result = 0;
				for (i = 35; i >= 0; i--) {
					result += base * binary[i];
					base *= 2;
				}
				System.out.println(" = " + result);
				mem.put(add, result);
			}
			
		}
		long sum = 0;
		for (long key : mem.keySet()) {
			sum += mem.get(key);
			System.out.print(key + ";" + mem.get(key) + " ");
		}
		System.out.println();
		System.out.println(sum);
	}
	
	public static void part2(Scanner console) {
		HashMap<Long, Long> mem = new HashMap<Long, Long>();
		String mask = console.nextLine().substring("mask = ".length());
		while (console.hasNextLine()) {
			String line = console.nextLine();
			if (line.startsWith("mask")) {
				mask = line.substring("mask = ".length());
			} else {
				long add = Integer.valueOf(line.substring(4, line.indexOf(']')));
				long value = Integer.valueOf(line.substring(line.indexOf('=')+2));
				long[] binary = new long[36];
				int i = 35;
				while (add > 0) {
					binary[i] = add % 2;
					add = add / 2;
					i--;
				}
				recursion(mem, mask, binary, value, 0);
			}
			
		}
		long sum = 0;
		for (long key : mem.keySet()) {
			sum += mem.get(key);
			System.out.print(key + ";" + mem.get(key) + " ");
		}
		System.out.println();
		System.out.println(sum);
	}

	private static void recursion(HashMap<Long, Long> mem, String mask, long[] binary, long value, int start) {
		for (int i = start; i < mask.length(); i++) {
			if (mask.charAt(i) == 'X') {
				binary[i] = 0;
				recursion(mem, mask, binary.clone(), value, i+1);
				binary[i] = 1;
			} else if (mask.charAt(i) == '1') {
				binary[i] = 1;
			} else if (mask.charAt(i) == '0') {
				continue;
			} else {
				System.out.println("WTF");
			}
		}
		long result = 0;
		long base = 1;
		for (int i = 35; i >= 0; i--) {
			result += base * binary[i];
			base *= 2;
		}
		mem.put(result, value);
	}

}
