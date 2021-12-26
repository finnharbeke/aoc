import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class First {
	public static void main(String[] args) throws FileNotFoundException {
		// part1();
		part2();
	}
	
	private static void part2() throws FileNotFoundException {
		Scanner console = new Scanner(new File("input1.txt"));
		int[] numbers = new int[200];
		int i = 0;
		while (console.hasNextInt()) {
			numbers[i] = console.nextInt();
			i++;
		}
		boolean done = false;
		
		for (i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				for (int k = 0; k < numbers.length; k++) {
					if (numbers[i] + numbers[j] + numbers[k] == 2020) {
						System.out.println(String.valueOf(numbers[i]) + " + " + String.valueOf(numbers[j]) + " + " + String.valueOf(numbers[k]));
						System.out.println(numbers[i] * numbers[j] * numbers[k]);
						done = true;
						break;
					}
				}
				if (done) break;
			}
			if (done) break;
		}
	}

	public static void part1() throws FileNotFoundException {
		Scanner console = new Scanner(new File("input1.txt"));
		int[] numbers = new int[200];
		int i = 0;
		while (console.hasNextInt()) {
			numbers[i] = console.nextInt();
			i++;
		}
		boolean done = false;
		
		for (i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				if (numbers[i] + numbers[j] == 2020) {
					System.out.println(String.valueOf(numbers[i]) + " + " + String.valueOf(numbers[j]));
					System.out.println(numbers[i] * numbers[j]);
					done = true;
					break;
				}
			}
			if (done) break;
		}
	}
}
