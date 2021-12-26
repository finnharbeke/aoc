import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Twentyfourth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input24.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		
		while (console.hasNextLine()) {
			String line = console.nextLine();
			int x = 0;
			int y = 0;
			
			int i = 0;
			while (i < line.length()) {
				char c1 = line.charAt(i);
				char c2 = '0';
				if (c1 == 's' || c1 == 'n') {
					i++;
					c2 = line.charAt(i);
				}
				if (c1 == 'e') {
					x--;
				} else if (c1 == 'w') {
					x++;
				} else if (c1 == 's') {
					if (c2 == 'e') {
						y++;
						x--;
					} else if (c2 == 'w') {
						y++;
					} else {
						System.out.println("impossible");
					}
				} else if (c1 == 'n') {
					if (c2 == 'e') {
						y--;
					} else if (c2 == 'w') {
						y--;
						x++;
					} else {
						System.out.println("impossible");
					}
				} else {
					System.out.println("impossible");
				}
				i++;
			}
			String key = String.valueOf(x) + "&" + String.valueOf(y);
			if (map.containsKey(key)) {
				map.put(key, !map.get(key));
			} else {
				map.put(key, true);
			}
		}
		
		int black = 0;
		for (String key : map.keySet()) {
			System.out.print(key + ";" + map.get(key) + " ");
			if (map.get(key)) black++;
		}
		System.out.println();
		System.out.println(black);
	}
	
	public static void part2(Scanner console) {
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		
		int xMin = 0;
		int xMax = 0;
		int yMin = 0;
		int yMax = 0;
		
		while (console.hasNextLine()) {
			String line = console.nextLine();
			int x = 0;
			int y = 0;
			
			int i = 0;
			while (i < line.length()) {
				char c1 = line.charAt(i);
				char c2 = '0';
				if (c1 == 's' || c1 == 'n') {
					i++;
					c2 = line.charAt(i);
				}
				if (c1 == 'e') {
					x--;
				} else if (c1 == 'w') {
					x++;
				} else if (c1 == 's') {
					if (c2 == 'e') {
						y++;
						x--;
					} else if (c2 == 'w') {
						y++;
					} else {
						System.out.println("impossible");
					}
				} else if (c1 == 'n') {
					if (c2 == 'e') {
						y--;
					} else if (c2 == 'w') {
						y--;
						x++;
					} else {
						System.out.println("impossible");
					}
				} else {
					System.out.println("impossible");
				}
				i++;
			}
			if (y < yMin) yMin = y;
			if (x < xMin) xMin = x;
			if (y > yMax) yMax = y;
			if (x > xMax) xMax = x;
			
			String key = String.valueOf(x) + "&" + String.valueOf(y);
			if (map.containsKey(key)) {
				map.put(key, !map.get(key));
			} else {
				map.put(key, true);
			}
		}
		
		int days = 100;
		yMin--;
		xMin--;
		yMax++;
		xMax++;
		
		for (int d = 0; d < days; d++) {
			yMin--;
			xMin--;
			yMax++;
			xMax++;
			
			HashMap<String, Boolean> newMap = copy(map);
			
			for (int y = yMin; y < yMax; y++) {
				for (int x = xMin; x < xMax; x++) {
					boolean ne = map.getOrDefault(String.valueOf(x) + "&" + String.valueOf(y - 1), false);
					boolean nw = map.getOrDefault(String.valueOf(x+1) + "&" + String.valueOf(y - 1), false);
					boolean e = map.getOrDefault(String.valueOf(x-1) + "&" + String.valueOf(y), false);
					boolean w = map.getOrDefault(String.valueOf(x+1) + "&" + String.valueOf(y), false);
					boolean se = map.getOrDefault(String.valueOf(x-1) + "&" + String.valueOf(y + 1), false);
					boolean sw = map.getOrDefault(String.valueOf(x) + "&" + String.valueOf(y + 1), false);
					
					int black = 0;
					if (ne) black++;
					if (nw) black++;
					if (e) black++;
					if (w) black++;
					if (se) black++;
					if (sw) black++;
					
					if (map.getOrDefault(String.valueOf(x) + "&" + String.valueOf(y), false)) {
						// black currently
						if (black == 0 || black > 2) newMap.put(String.valueOf(x) + "&" + String.valueOf(y), false);
					} else {
						// white currently
						if (black == 2) newMap.put(String.valueOf(x) + "&" + String.valueOf(y), true);
					}
				}
			}
			
			map = copy(newMap);
			
			int black = 0;
			for (String key : map.keySet()) {
				//System.out.print(key + ";" + map.get(key) + " ");
				if (map.get(key)) black++;
			}
			System.out.println("Day " + (d+1) + ": " + black);
		}
	}
	
	static HashMap<String, Boolean> copy(HashMap<String, Boolean> map) {
		HashMap<String, Boolean> nMap = new HashMap<String, Boolean>();
		for (String key : map.keySet()) {
			nMap.put(key, map.get(key));
		}
		return nMap;
	}

}