import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Third {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input3.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int trees = 0;
		int n = console.nextLine().length();
		int x = 0;
		while (console.hasNext()) {
			String line = console.nextLine();
			x = (x+7) % n;
			if (line.charAt(x) == '#') {
				trees++;
			}
		}
		System.out.println(trees);
		
	}
	
	public static void part2(Scanner console) {
		int trees1 = 0;
		int trees2 = 0;
		int trees3 = 0;
		int trees4 = 0;
		int trees5 = 0;
		int n = console.nextLine().length();
		int x1 = 0;
		int x2 = 0;
		int x3 = 0;
		int x4 = 0;
		int x5 = 0;
		int i = 0;
		while (console.hasNext()) {
			i++;
			String line = console.nextLine();
			x1 = (x1+1) % n;
			if (line.charAt(x1) == '#') {
				System.out.print(1);
				trees1++;
			}
			x2 = (x2+3) % n;
			if (line.charAt(x2) == '#') {
				System.out.print(2);
				trees2++;
			}
			x3 = (x3+5) % n;
			if (line.charAt(x3) == '#') {
				System.out.print(3);
				trees3++;
			}
			x4 = (x4+7) % n;
			if (line.charAt(x4) == '#') {
				System.out.print(4);
				trees4++;
			}
			if (i % 2 == 0) {
				x5 = (x5+1) % n;
				if (line.charAt(x5) == '#') {
					System.out.print(5);
					trees5++;
				}
			}
			System.out.println();
		}
		System.out.println("====");
		System.out.println(trees1);
		System.out.println(trees2);
		System.out.println(trees3);
		System.out.println(trees4);
		System.out.println(trees5);
		long total = (long)trees1*trees2*trees3*trees4*trees5;
		System.out.println(total);
		
	}

}
