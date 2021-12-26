import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class Sixth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input6.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashSet<Character> set = new HashSet<Character>();
		int total = 0;
		boolean isFirst = true;
		while (console.hasNext()) {
			String line = console.nextLine();
			if (line.equals("")) {
				total += set.size();
				set = new HashSet<Character>();
				isFirst = true;
			} else {
				if (isFirst) {
					for (int i = 0; i < line.length(); i++) {
						set.add(line.charAt(i));
					}
					isFirst = false;
				} else {
					
					for (int i = 0; i < line.length(); i++) {
						
						set.add(line.charAt(i));
					}
				}
			}
		}
		total += set.size();
		System.out.println(total);
		
	}
	
	public static void part2(Scanner console) {
		HashSet<Character> set = new HashSet<Character>();
		int total = 0;
		boolean isFirst = true;
		while (console.hasNext()) {
			String line = console.nextLine();
			if (line.equals("")) {
				total += set.size();
				set = new HashSet<Character>();
				isFirst = true;
			} else {
				if (isFirst) {
					for (int i = 0; i < line.length(); i++) {
						set.add(line.charAt(i));
					}
					isFirst = false;
				} else {
					char[] to_remove = new char[set.size()];
					int j = 0;
					for (char key : set) {
						boolean found = false;
						for (int i = 0; i < line.length(); i++) {
							if (key == line.charAt(i)) {
								found = true;
								break;
							}
						}
						if (!found) to_remove[j++] = key;
					}
					
					for (int i = 0; i < j; i++) {
						set.remove(to_remove[i]);
					}
				}
			}
		}
		total += set.size();
		System.out.println(total);
		
	}

}
