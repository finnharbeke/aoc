import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Thirteenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input13.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int mytime = console.nextInt();
		console.nextLine();
		String line = console.nextLine();
		int min = Integer.MAX_VALUE;
		int minId = -1;
		for (String part : line.split(",")) {
			if (part.equals("x")) continue;
			else {
				int id = Integer.valueOf(part);
				int wait = id - (mytime % id);
				if (wait < min) {
					min = wait;
					minId = id;
				}
			}
		}
		System.out.println(min + " " + minId);
		System.out.println(min * minId);
	}
	
	public static void part2(Scanner console) {
		//console.nextInt();
		console.nextLine();
		String line = console.nextLine();
		String[] temp = line.split(",");
		int c = 9;
		int[] m = new int[c];
		int[] a = new int[c];
		int j = 0;
		int i = 0;
		for (String bus : temp) {
			if (bus.equals("x"));
			else {
				m[j] = Integer.valueOf(bus);
				a[j] = m[j] - (i % m[j]);
				j++;
			}
			i++;
		}
		long MM = 1;
		for (int mi : m) {
			MM *= mi;
		}
		long[] M = new long[c];
		for (i = 0; i < m.length; i++) {
			M[i] = MM / m[i];
		}
		int[] N = new int[c];
		for (i = 0; i < m.length; i++) {
			//long R = M[i] % m[i];
			for (int n = 0; n < m[i]; n++) {
				if ((M[i] * n) % m[i] == 1) {
					N[i] = n;
					break;
				}
			}
		}
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(m));
		System.out.println(Arrays.toString(N));
		long sum = 0;
		for (i = 0; i < m.length; i++) {
			sum += a[i] * M[i] * N[i];
		}
		//System.out.println(busses[0] + " " + busses[19] + " " + busses[29] + " " + busses[42] + " " + busses[43] + " " + busses[52] + " " + busses[60] + " " + busses[66] + " " + busses[79]);
		// 0 : 29, 19: 41, 29 : 661, 42 : 13, 43 : 17, 52 : 23, 60 : 521, 66 : 37, 79 : 19
		System.out.println(sum % MM);
		
		//System.out.println(answer);
	}

}
