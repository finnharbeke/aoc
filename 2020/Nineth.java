import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Nineth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input9.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		long[] previous = new long[25];
		for (int i = 0; i<25; i++) {
			previous[i] = console.nextLong();
		}
		while (console.hasNextLong()) {
			long current = console.nextLong();
			boolean found = false;
			for (int i = 0; i<25; i++) {
				for (int j = 0; j<25; j++) {
					if (previous[i] + previous[j] == current) {
						System.out.println(current + " == " + previous[i] +" + " + previous[j]);
						found = true;
						break;
					}
				}
				if (found) break;
			}
			if (!found) {
				System.out.println(current);
				break;
			} else {
				for (int i = 0; i < 24; i++) {
					previous[i] = previous[i+1];
				}
				previous[24] = current;
			}
		}
	}
	
	public static void part2(Scanner console) {
		long[] previous = new long[25];
		long[] sums = new long[1001];
		sums[0] = 0;
		for (int i = 0; i<25; i++) {
			previous[i] = console.nextLong();
			sums[i+1] = sums[i] + previous[i];
		}
		int pos = 25;
		while (console.hasNextLong()) {
			long current = console.nextLong();
			sums[pos+1] = sums[pos] + current;
			boolean found = false;
			for (int i = 0; i<25; i++) {
				for (int j = 0; j<25; j++) {
					if (previous[i] + previous[j] == current) {
						System.out.println(current + " == " + previous[i] +" + " + previous[j]);
						found = true;
						break;
					}
				}
				if (found) break;
			}
			if (!found) {
				System.out.println(current);
				for (int i = 0; i < sums.length-2; i++) {
					for (int j = i+2; j < sums.length; j++) {
						if (sums[j] - sums[i] == current) {
							System.out.println((sums[i+1]-sums[i]));
							System.out.println((sums[j]-sums[j-1]));
							long min = Long.MAX_VALUE;
							long max = Long.MIN_VALUE;
							for (int a = i+1; a <= j; a++) {
								if (sums[a] - sums[a-1] > max) max = sums[a] - sums[a-1];
								if (sums[a] - sums[a-1] < min) min = sums[a] - sums[a-1];
							}
							System.out.println(min + " " + max);
							System.out.println(min+max);
						}
					}
				}
				
				break;
			} else {
				for (int i = 0; i < 24; i++) {
					previous[i] = previous[i+1];
				}
				previous[24] = current;
			}
			pos++;
		}
	}

}
