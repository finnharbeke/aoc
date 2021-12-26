import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Twentieth2 {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input20.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		
	}
	
	public static void part2(Scanner console) {
		int n = 10;
		int m = 12;
		HashMap<Integer, String[]> tiles = new HashMap<Integer, String[]>();
		
		int ind = -1;
		int l = -1;
		while (console.hasNextLine()) {
			String line = console.nextLine().strip();
			if (line.startsWith("Tile")) {
				ind = Integer.valueOf(line.substring("Tile ".length(), line.indexOf(':')));
				tiles.put(ind, new String[n]);
				l = 0;
			} else if (line.equals("")) {
				continue;
			} else {
				tiles.get(ind)[l] = line;
				l++;
			}
		}
		
		HashMap<Integer, Integer> neighborsCount = new HashMap<Integer, Integer>();
		HashMap<Integer, HashMap<String, Integer>> neighbors = new HashMap<Integer, HashMap<String, Integer>>();
		
		for (Integer i : tiles.keySet()) {
			neighborsCount.put(i, 0);
			neighbors.put(i, new HashMap<String, Integer>());
			for (Integer j : tiles.keySet()) {
				if (i.equals(j)) continue;
				String[] me = tiles.get(i);
				String[] you = tiles.get(j);
				for (String my : new String[]{leftOfTile(me), rightOfTile(me), me[0], me[n-1]}) {
					for (String other : new String[] {leftOfTile(you), rightOfTile(you), you[0], you[n-1]}) {
						if (my.equals(other) || reverse(my).equals(other)) {
							neighborsCount.put(i, neighborsCount.get(i) + 1);
							if (my.equals(leftOfTile(me))) {
								neighbors.get(i).put("left", j);
							} else if (my.equals(rightOfTile(me))) {
								neighbors.get(i).put("right", j);
							} else if (my.equals(me[0])) {
								neighbors.get(i).put("top", j);
							} else if (my.equals(me[n-1])) {
								neighbors.get(i).put("bottom", j);
							}
						}
					}
				}
			}
		}
		
		Integer c = -1;
		for (Integer i : neighborsCount.keySet()) {
			if (neighborsCount.get(i) == 2) {
				c = i;
				break;
			}
		}
		
		String[][][] map = new String[m][m][n];
		Integer[][] indMap = new Integer[m][m];
		
		Integer nextLine = -1;
		
		for (int y = 0; y < m; y++) {
			for (int x = 0; x < m; x++) {
				map[y][x] = copy(tiles.get(c));
				indMap[y][x] = c;
				HashMap<String, Integer> ns = neighbors.get(c);
				System.out.println(c + ": " + ns.toString());
				
				if (x > 0) {
					int rot = 0;
					boolean fH = false;
					if (ns.getOrDefault("left", -1).equals(indMap[y][x-1])) {
						// nice
					} else if (ns.getOrDefault("right", -1).equals(indMap[y][x-1])) {
						map[y][x] = flipH(map[y][x]);
						fH = true;
					} else if (ns.getOrDefault("top", -1).equals(indMap[y][x-1])) {
						map[y][x] = rotate(map[y][x]);
						rot++;
					} else if (ns.getOrDefault("bottom", -1).equals(indMap[y][x-1])) {
						map[y][x] = flipH(rotate(map[y][x]));
						rot++; fH = true;
					} else {
						System.out.println("illegal 1");
					}
					if (leftOfTile(map[y][x]).equals(rightOfTile(map[y][x-1]))) {
						// nice
					} else if (reverse(leftOfTile(map[y][x])).equals(rightOfTile(map[y][x-1]))) {
						map[y][x] = flipV(map[y][x]);
					} else {
						System.out.println("illegal 2");
					}
					
					if (x == m-1) c = nextLine;
					else {
						if (rot == 0) {
							if (fH) {
								c = ns.get("left");
							} else {
								c = ns.get("right");
							}
						} else {
							if (fH) {
								c = ns.get("top");
							} else {
								c = ns.get("bottom");
							}
						}
					}
				} else {
					if (y > 0) {
						int rot = 0;
						boolean fH = false;
						boolean fV = false;
						if (ns.getOrDefault("left", -1).equals(indMap[y-1][x])) {
							map[y][x] = flipV(rotate(map[y][x]));
							fV = true;
							rot++;
						} else if (ns.getOrDefault("right", -1).equals(indMap[y-1][x])) {
							map[y][x] = rotate(map[y][x]);
							rot++;
						} else if (ns.getOrDefault("top", -1).equals(indMap[y-1][x])) {
							// nice
						} else if (ns.getOrDefault("bottom", -1).equals(indMap[y-1][x])) {
							map[y][x] = flipV(map[y][x]);
							fV = true;
						} else {
							System.out.println("illegal 11");
						}
						if (map[y][x][0].equals(map[y-1][x][n-1])) {
							// nice
						} else if (reverse(map[y][x][0]).equals(map[y-1][x][n-1])) {
							map[y][x] = flipH(map[y][x]);
							fH = true;
						} else {
							System.out.println("illegal 21");
						}
						
						if (rot == 0) {
							if (fH) {
								c = ns.get("left");
							} else {
								c = ns.get("right");
							}
							if (fV) {
								nextLine = ns.get("top");
							} else {
								nextLine = ns.get("bottom");
							}
						} else {
							if (fH) {
								c = ns.get("top");
							} else {
								c = ns.get("bottom");
							}
							if (fV) {
								nextLine = ns.get("right");
							} else {
								nextLine = ns.get("left");
							}
						}
						
					} else {
						if (ns.containsKey("left")) {
							if (ns.containsKey("top")) {
								map[y][x] = rotate(rotate(map[y][x]));
								c = ns.get("left");
								nextLine = ns.get("top");
							} else if (ns.containsKey("bottom")) {
								map[y][x] = rotate(map[y][x]);
								c = ns.get("bottom");
								nextLine = ns.get("left");
							} else {
								System.out.println("illegal 3");
							}
						} else {
							if (ns.containsKey("top")) {
								map[y][x] = flipV(map[y][x]);
								c = ns.get("right");
								nextLine = ns.get("top");
							} else if (ns.containsKey("bottom")) {
								c = ns.get("right");
								nextLine = ns.get("bottom");
							} else {
								System.out.println("illegal 4");
							}
						}
					}
				}
			}
		}
		
		for (int i = 0; i < m; i++) {
			for (int inner = 0; inner < n; inner++) {
				for (int j = 0; j < m; j++) {
					System.out.print(map[i][j][inner] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
		
		System.out.println(neighborsCount.toString());
		
		int s = m*(n-2);
		String[] sea = new String[s];
		
		for (int y = 0; y < s; y++) {
			sea[y] = "";
			for (int x = 0; x < s; x++) {
				int i = y / (n-2);
				int j = x / (n-2);
				String[] t = map[i][j];
				sea[y] += t[y % (n-2) + 1].charAt(x % (n-2) + 1);
			}
		}
		
		for (int y = 0; y < s; y++) {
			System.out.println(flipV(rotate(rotate(rotate(sea))))[y]);
			//System.out.println();
		}
		
        final String mon1 = "..................#.";
        final String mon2 = "#....##....##....###";
        final String mon3 = ".#..#..#..#..#..#...";  
        int ml = mon1.length();
        
        
        String[] theOneSea = {""};
        int monsterMax = -1;
        for (String[] thisSea : new String[][]{sea, rotate(sea), rotate(rotate(sea)), rotate(rotate(rotate(sea))),
        									   flipH(sea), rotate(flipH(sea)), rotate(rotate(flipH(sea))), rotate(rotate(rotate(flipH(sea))))}) {
        	int monsters = 0;
	        for (int y = 0; y < s - 2; y++) {
	        	for (int x = 0; x < s - ml + 1; x++) {
	        		boolean monster = true;
	        		for (int i = 0; i < ml; i++) {
	        			if ((mon1.charAt(i) == '#' && thisSea[y].charAt(x+i) != '#') || 
	        				(mon2.charAt(i) == '#' && thisSea[y+1].charAt(x+i) != '#') ||
	        				(mon3.charAt(i) == '#' && thisSea[y+2].charAt(x+i) != '#')) {
	        				monster = false;
	        				break;
	        			}
	        		}
	        		if (monster) monsters++;
	        	}
	        }
	        System.out.println(monsters);
	        if (monsters > monsterMax) {
	        	theOneSea = thisSea;
	        	monsterMax = monsters;
	        }
        }
        
        int countHash = 0;
        for (int y = 0; y < s; y++) {
        	for (int x = 0; x < s; x++) {
        		if (theOneSea[y].charAt(x) == '#') countHash++;
        	}
        }
        
        System.out.println(countHash - 15 * monsterMax);
        
	}
	
	public static String reverse(String s) {
		String r = "";
		for (int i = s.length() -1; i >= 0; i--) {
			r += s.charAt(i);
		}
		return r;
	}
	
	private static String leftOfTile(String[] s) {
		String res = "";
		for (int i = 0; i < s.length; i++) res += s[i].charAt(0);
		return res;
	}
	
	private static String rightOfTile(String[] s) {
		String res = "";
		for (int i = 0; i < s.length; i++) res += s[i].charAt(s[i].length()-1);
		return res;
	}

	private static String[] rotate(String[] old) {
		String[] neww = new String[old.length];
		for (int i = 0; i < neww.length; i++) neww[i] = "";
		
		for (int i = 0; i < old.length; i++) {
			for (int j = 0; j < old[i].length(); j++) {
				neww[neww.length - 1 - j] += old[i].charAt(j);
			}
		}
		
		return neww;
	}
	
	private static String[] flipV(String[] old) {
		String[] neww = new String[old.length];
		
		for (int i = 0; i < old.length; i++) {
			neww[old.length - 1 - i] = old[i];
		}
		
		return neww;
	}
	
	private static String[] flipH(String[] old) {
		String[] neww = new String[old.length];
		
		for (int i = 0; i < old.length; i++) {
			neww[i] = reverse(old[i]);
		}
		
		return neww;
	}

	private static String[] copy(String[] old) {
		String[] neww = new String[old.length];
		
		for (int i = 0; i < old.length; i++) {
			neww[i] = old[i];
		}
		
		return neww;
	}

}
