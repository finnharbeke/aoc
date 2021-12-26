import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Eighth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input8.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int n = 625;
		String[] lines = new String[n];
		for (int i = 0; i < n; i++) {
			lines[i] = console.nextLine(); 
		}
		int pos = 0;
		int acc = 0;
		while (!lines[pos].equals("")) {
			System.out.println(pos);
			Scanner line = new Scanner(lines[pos]);
			lines[pos] = "";
			switch (line.next()) {
				case "acc":
					int inc = line.nextInt();
					acc += inc;
					pos++;
					break;
				case "jmp":
					int num = line.nextInt();
					pos += num;
					break;
				case "nop":
					pos++;
					break;
			}
			line.close();
		}
		System.out.println(pos);
		System.out.println(acc);
	}
	
	public static void part2(Scanner console) {
		int n = 625;
		String[] lines = new String[n];
		for (int i = 0; i < n; i++) {
			lines[i] = console.nextLine(); 
		}
		int acc = recursion(lines, 0, 0, false);
		System.out.println(acc);
	}
	
	public static int recursion(String[] lines, int pos, int acc, boolean changed) {
		while (pos != lines.length && !lines[pos].equals("")) {
			Scanner line = new Scanner(lines[pos]);
			lines[pos] = "";
			switch (line.next()) {
				case "acc":
					int inc = line.nextInt();
					acc += inc;
					pos++;
					break;
				case "jmp":
					if (!changed) {
						int rec = recursion(lines.clone(), pos+1, acc, true);
						if (rec != -1111111) {
							line.close();
							return rec;
						}
					}
					int num = line.nextInt();
					pos += num;
					break;
				case "nop":
					if (!changed) {
						num = line.nextInt();
						int rec = recursion(lines.clone(), pos+num, acc, true);
						if (rec != -1111111) {
							line.close();
							return rec;
						}
					}
					pos++;
					break;
			}
			line.close();
		}
		if (pos == lines.length) {
			System.out.println("success " + pos);
			System.out.println(acc);
			return acc;
		} else {
			System.out.println("fail " + pos);
			return -1111111;
		}
	}

}
