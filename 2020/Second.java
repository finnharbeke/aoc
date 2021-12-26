import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Second {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input2.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int total = 0;
		while (console.hasNext()) {
			String line = console.nextLine();
			int concat = line.indexOf('-');
			int min = Integer.parseInt(line.substring(0, concat));
			int concat2 = line.indexOf(' ');
			int max = Integer.parseInt(line.substring(concat+1, concat2));
			line = line.substring(concat2+1);
			String letter = line.substring(0, 1);
			String s = line.substring(3);
			int count = 0;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == letter.charAt(0)) count++;
			}
			System.out.print(count);
			if (min <= count && count <= max) {
				total++;
				System.out.println(": yes");
			} else {
				System.out.println(": no");
			}
		}
		System.out.print("total: ");
		System.out.println(total);
	}
	
	public static void part2(Scanner console) {
		int total = 0;
		while (console.hasNext()) {
			String line = console.nextLine();
			int concat = line.indexOf('-');
			int first = Integer.parseInt(line.substring(0, concat));
			int concat2 = line.indexOf(' ');
			int sec = Integer.parseInt(line.substring(concat+1, concat2));
			line = line.substring(concat2+1);
			String letter = line.substring(0, 1);
			String s = line.substring(3);
			int count = 0;
			if (s.charAt(first-1) == letter.charAt(0)) count++;
			if (s.charAt(sec-1) == letter.charAt(0)) count++;
			System.out.print(count);
			if (count == 1) {
				total++;
				System.out.println(": yes");
			} else {
				System.out.println(": no");
			}
		}
		System.out.print("total: ");
		System.out.println(total);
	}

}
