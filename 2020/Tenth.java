import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input10.txt"));
		part2(console);
		
	}
	
	public static void part2(Scanner console) {
		int n = 93;
		int[] jolts = new int[n];
		int i = 0;
		while (console.hasNextInt()) {
			jolts[i++] = console.nextInt();
		}
		long mult1 = 1;
		long mult2 = 0;
		long mult3 = 0;
		int prev1 = 0;
		int prev2 = -4;
		int prev3 = -4;
		int min;
		int minInd;
		for (int start = 0; start < n; start++) {
			min = Integer.MAX_VALUE;
			minInd = 0;
			for (i = start; i < n; i++) {
			if (jolts[i] < min) {
				min = jolts[i];
				minInd = i;
				}
			}
			int temp = jolts[start];
			jolts[start] = min;
			jolts[minInd] = temp;
			long mymult = 0;
			if (prev1 >= min-3) mymult += mult1;
			if (prev2 >= min-3) mymult += mult2;
			if (prev3 >= min-3) mymult += mult3;
			mult3 = mult2;
			mult2 = mult1;
			mult1 = mymult;
			prev3 = prev2;
			prev2 = prev1;
			prev1 = min;
		}
		System.out.println(mult1);
		
	}
	
	public static void part1(Scanner console) {
		int n = 93;
		int[] jolts = new int[n];
		int i = 0;
		while (console.hasNextInt()) {
			jolts[i++] = console.nextInt();
		}
		int ones = 0;
		int twos = 0;
		int threes = 1;
		int previous = 0;
		int min;
		int minInd;
		for (int start = 0; start < n; start++) {
			min = Integer.MAX_VALUE;
			minInd = 0;
			for (i = start; i < n; i++) {
			if (jolts[i] < min) {
				min = jolts[i];
				minInd = i;
				}
			}
			int temp = jolts[start];
			jolts[start] = min;
			jolts[minInd] = temp;
			if (min - previous == 1) ones++;
			else if (min - previous ==2) twos++;
			else if (min - previous == 3) threes++;
			else {
				System.out.println("too big jump");
			}
			previous = min;
		}
		
		System.out.println(ones + " " + twos + " " + threes);
		System.out.println(ones * threes);
		
	}

}
