import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Fifth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input5.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int maxId = 0;
		while (console.hasNext()) {
			String line = console.nextLine();
			int a = 0;
			int b = 127;
			for (int i = 0; i < 7; i++) {
				int mid = (a+b) / 2;
				if (line.charAt(i) == 'F') {
					b = mid;
				} else {
					a = mid+1;
				}
			}
			System.out.println(a + " " + b);
			int c = 0;
			int d = 7;
			for (int i = 0; i < 3; i++) {
				int mid = (c + d) / 2;
				if (line.charAt(7+i) == 'L') {
					d = mid;
				} else {
					c = mid + 1;
				}
			}
			System. out.println(c + " " + d);
			int id = a * 8 + c;
			System.out.println("id: " + id);
			if (id > maxId) maxId = id;
		}
		System.out.println(maxId);
	}
	
	public static void part2(Scanner console) {
		boolean[] visited = new boolean[128 * 8];
		while (console.hasNext()) {
			String line = console.nextLine();
			int a = 0;
			int b = 127;
			for (int i = 0; i < 7; i++) {
				int mid = (a+b) / 2;
				if (line.charAt(i) == 'F') {
					b = mid;
				} else {
					a = mid+1;
				}
			}
			System.out.println(a + " " + b);
			int c = 0;
			int d = 7;
			for (int i = 0; i < 3; i++) {
				int mid = (c + d) / 2;
				if (line.charAt(7+i) == 'L') {
					d = mid;
				} else {
					c = mid + 1;
				}
			}
			System. out.println(c + " " + d);
			int id = a * 8 + c;
			System.out.println("id: " + id);
			visited[id] = true;
		}
		for (int i = 8; i < 127 * 8; i++) {
			if (!visited[i]) System.out.println(i);
		}
	}

}
