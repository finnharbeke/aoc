import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Eleventh {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input11.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int n = 91;
		int m = 98;
		char[][] seats1 = new char[91][98];
		char[][] seats2 = new char[91][98];
		
		for (int i = 0; i < n; i++) {
			String line = console.nextLine();
			for (int j = 0; j < m; j++) {
				char c = line.charAt(j);
				seats1[i][j] = c;
				seats2[i][j] = c;
			}
		}
		
		boolean changes = true;
		while (changes) {
			changes = false;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					switch (seats1[i][j]) {
						case 'L':
							boolean allempty = true;
							if (!(i == 0 || j == 0 || seats1[i-1][j-1] != '#')) allempty = false;
							if (allempty && !(i == 0 || seats1[i-1][j] != '#')) allempty = false;
							if (allempty && !(i == 0 || j == m-1 || seats1[i-1][j+1] != '#')) allempty = false;
							if (allempty && !(j == m-1 || seats1[i][j+1] != '#')) allempty = false;
							if (allempty && !(i == n-1 || j==m-1 || seats1[i+1][j+1] != '#')) allempty = false;
							if (allempty && !(i == n-1 || seats1[i+1][j] != '#')) allempty = false;
							if (allempty && !(i == n-1 || j == 0 || seats1[i+1][j-1] != '#')) allempty = false;
							if (allempty && !(j == 0 || seats1[i][j-1] != '#')) allempty = false;
							if (allempty) {
								seats2[i][j] = '#';
								changes = true;
							}
							
							break;
						case '#':
							int occupied = 0;
							if (!(i == 0 || j == 0 || seats1[i-1][j-1] != '#')) occupied++;
							if (!(i == 0 || seats1[i-1][j] != '#')) occupied++;
							if (!(i == 0 || j == m-1 || seats1[i-1][j+1] != '#')) occupied++;
							if (!(j == m-1 || seats1[i][j+1] != '#')) occupied++;
							if (!(i == n-1 || j==m-1 || seats1[i+1][j+1] != '#')) occupied++;
							if (!(i == n-1 || seats1[i+1][j] != '#')) occupied++;
							if (!(i == n-1 || j == 0 || seats1[i+1][j-1] != '#')) occupied++;
							if (!(j == 0 || seats1[i][j-1] != '#')) occupied++;
							if (occupied >= 4) {
								seats2[i][j] = 'L';
								changes = true;
							}
							
							break;
							
						case '.':
							
							break;
					}
					
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					seats1[i][j] = seats2[i][j];
				}
			}
			
		}
		
		int endoccupied = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (seats1[i][j] == '#') endoccupied++;
			}
		}
		System.out.println(endoccupied);
	}
	
	public static void part2(Scanner console) {
		int n = 91;
		int m = 98;
		char[][] seats1 = new char[n][m];
		char[][] seats2 = new char[n][m];
		
		for (int i = 0; i < n; i++) {
			String line = console.nextLine();
			for (int j = 0; j < m; j++) {
				char c = line.charAt(j);
				seats1[i][j] = c;
				seats2[i][j] = c;
			}
		}
		
		boolean changes = true;
		while (changes) {
			changes = false;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					switch (seats1[i][j]) {
						case 'L':
							boolean allempty = true;
							for (int y = -1; y <= 1; y++) {
								for (int x = -1; x <= 1; x++) {
									if (y == 0 && x == 0) continue;
									int steps = 1;
									while (i + steps*y >= 0 && i + steps*y < n && j + steps*x >= 0 && j + steps*x < m) {
										if (seats1[i + steps*y][j + steps*x] == 'L') {
											break;
										} else if (seats1[i + steps*y][j + steps*x] == '#') {
											allempty = false;
											break;
										}
										steps++;
									}
									if (!allempty) break;
								}
								if (!allempty) break;
							}
							if (allempty) {
								seats2[i][j] = '#';
								changes = true;
							}
							
							break;
						case '#':
							int occupied = 0;
							for (int y = -1; y <= 1; y++) {
								for (int x = -1; x <= 1; x++) {
									if (y == 0 && x == 0) continue;
									int steps = 1;
									while (i + steps*y >= 0 && i + steps*y < n && j + steps*x >= 0 && j + steps*x < m) {
										if (seats1[i + steps*y][j + steps*x] == 'L') {
											break;
										} else if (seats1[i + steps*y][j + steps*x] == '#') {
											occupied++;
											break;
										}
										steps++;
									}
									if (i == 1 && j == 1) System.out.println(occupied);
								}
							}
							if (occupied >= 5) {
								seats2[i][j] = 'L';
								changes = true;
							}
							
							break;
							
						case '.':
							
							break;
					}
					
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					seats1[i][j] = seats2[i][j];
					System.out.print(seats1[i][j]);
				}
				System.out.println();
			}
			System.out.println();
			
		}
		
		int endoccupied = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (seats1[i][j] == '#') endoccupied++;
			}
		}
		System.out.println(endoccupied);
	}

}
