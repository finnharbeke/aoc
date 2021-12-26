import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Twentieth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input20.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int n = 10;
		int nTiles = 4000;
		int mapN = 12; 
		
		String[][] tiles = new String[nTiles][n];
		int[] inds = new int[mapN*mapN];
		
		int ind = -1;
		int y = 0;
		int countT = 0;
		while (console.hasNextLine()) {
			String line = console.nextLine().strip();
			if (line.startsWith("Tile")) {
				ind = Integer.valueOf(line.substring("Tile ".length(), line.indexOf(':')));
				//System.out.println(ind);
				inds[countT] = ind;
				countT++;
				y = 0;
			} else if (line.equals("")) {
				continue;
			} else {
				
				tiles[ind][y] = line;
				System.out.println("\"" + tiles[ind][y] + "\"");
				y++;
			}
		}
		System.out.println("\"" + tiles[0][0] + "\"");
		System.out.println("\"" + reverse(tiles[3347][0]) + "\"");
		
		long result = 1;
		int normal = 0;
		
		String[][][] map = new String[mapN][mapN][n-2];
		int[][] possibleNeighbors = new int[nTiles][4];
		int topLeft;
		
		for (int i : inds) {
			if (tiles[i][0] == null) continue;
			
			String myLeft = "";
			String myRight = "";
			for (int k = 0; k < n; k++) {
				myLeft += tiles[i][k].charAt(0);
				myRight += tiles[i][k].charAt(n-1);
				
			}
			String myTop = tiles[i][0];
			String myBottom = tiles[i][n-1];
			
			boolean foundLeft = false;
			boolean foundRight = false;
			boolean foundTop = false;
			boolean foundBottom = false;
			
			for (int j : inds) {
				if (tiles[j][0] == null || i == j) continue;
				
				String otherLeft = "";
				String otherRight = "";
				
				for (int k = 0; k < n; k++) {
					otherLeft += tiles[j][k].charAt(0);
					otherRight += tiles[j][k].charAt(n-1);
				}
				
				String otherTop = tiles[j][0];
				String otherBottom = tiles[j][n-1];
				
				for (String my : new String[]{myLeft, myRight, myTop, myBottom}) {
					for (String other : new String[] {otherLeft, otherRight, otherTop, otherBottom}) {
						if (my.equals(other) || reverse(my).equals(other)) {
							if (my.equals(myLeft)) {
								foundLeft = true;
								possibleNeighbors[i][0] = j;
							} else if (my.equals(myRight)) {
								foundRight = true;
								possibleNeighbors[i][1] = j;
							} else if (my.equals(myTop)) {
								foundTop = true;
								possibleNeighbors[i][2] = j;
							} else if (my.equals(myBottom)) {
								foundBottom = true;
								possibleNeighbors[i][3] = j;
							}
						}
					}
				}
				
				if (foundLeft && foundRight && foundTop && foundBottom) break;
				
			}
			
			int neighbors = foundLeft ? 1 : 0;
			neighbors += foundRight ? 1 : 0;
			neighbors += foundTop ? 1 : 0;
			neighbors += foundBottom ? 1 : 0;
			
			if (neighbors == 2) {
				System.out.println("found corner");
				topLeft = i;
				result *= i;
			} else if (neighbors == 4) {
				 normal++;
			} else {
				System.out.println("found only " + neighbors + " neighbors?!");
			}
			
		}
		System.out.println(normal);
		System.out.println(result);
	}
	
	public static String reverse(String s) {
		String r = "";
		for (int i = s.length() -1; i >= 0; i--) {
			r += s.charAt(i);
		}
		return r;
	}
	
	public static void part2(Scanner console) {
		int n = 10;
		int nTiles = 4000;
		int mapN = 12; 
		
		String[][] tiles = new String[nTiles][n];
		int[] inds = new int[mapN*mapN];
		
		int ind = -1;
		int y = 0;
		int countT = 0;
		while (console.hasNextLine()) {
			String line = console.nextLine().strip();
			if (line.startsWith("Tile")) {
				ind = Integer.valueOf(line.substring("Tile ".length(), line.indexOf(':')));
				//System.out.println(ind);
				inds[countT] = ind;
				countT++;
				y = 0;
			} else if (line.equals("")) {
				continue;
			} else {
				
				tiles[ind][y] = line;
				System.out.println("\"" + tiles[ind][y] + "\"");
				y++;
			}
		}
		System.out.println("\"" + tiles[0][0] + "\"");
		System.out.println("\"" + reverse(tiles[3347][0]) + "\"");
		
		long result = 1;
		int normal = 0;
		
		int[][] possibleNeighbors = new int[nTiles][4];
		int topLeft = 0;
		
		for (int i : inds) {
			if (tiles[i][0] == null) continue;
			
			String myLeft = "";
			String myRight = "";
			for (int k = 0; k < n; k++) {
				myLeft += tiles[i][k].charAt(0);
				myRight += tiles[i][k].charAt(n-1);
				
			}
			String myTop = tiles[i][0];
			String myBottom = tiles[i][n-1];
			
			boolean foundLeft = false;
			boolean foundRight = false;
			boolean foundTop = false;
			boolean foundBottom = false;
			
			for (int j : inds) {
				if (tiles[j][0] == null || i == j) continue;
				
				String otherLeft = "";
				String otherRight = "";
				
				for (int k = 0; k < n; k++) {
					otherLeft += tiles[j][k].charAt(0);
					otherRight += tiles[j][k].charAt(n-1);
				}
				
				String otherTop = tiles[j][0];
				String otherBottom = tiles[j][n-1];
				
				for (String my : new String[]{myLeft, myRight, myTop, myBottom}) {
					for (String other : new String[] {otherLeft, otherRight, otherTop, otherBottom}) {
						if (my.equals(other) || reverse(my).equals(other)) {
							if (my.equals(myLeft)) {
								if (foundLeft) System.out.println("double!!");
								foundLeft = true;
								possibleNeighbors[i][0] = j;
								
							} else if (my.equals(myRight)) {
								foundRight = true;
								possibleNeighbors[i][1] = j;
							} else if (my.equals(myTop)) {
								foundTop = true;
								possibleNeighbors[i][2] = j;
							} else if (my.equals(myBottom)) {
								foundBottom = true;
								possibleNeighbors[i][3] = j;
							}
						}
					}
				}
				
				if (foundLeft && foundRight && foundTop && foundBottom) break;
				
			}
			
			int neighbors = foundLeft ? 1 : 0;
			neighbors += foundRight ? 1 : 0;
			neighbors += foundTop ? 1 : 0;
			neighbors += foundBottom ? 1 : 0;
			
			if (neighbors == 2) {
				System.out.println("found corner");
				topLeft = i;
				result *= i;
			} else if (neighbors == 4) {
				 normal++;
			} else {
				System.out.println("found only " + neighbors + " neighbors?!");
			}
			
		}
		
		System.out.println(String.join("\n", rotate(tiles[1321])));
		
		//map[0][0] = topLeft;
		String[][][] map = new String[mapN][mapN][n];
		int[][] indMap = new int[mapN][mapN];
		int cur = topLeft;
		for (int i = 0; i < mapN; i++) {
			
			for (int j = 0; j < mapN; j++) {
				indMap[i][j] = cur;
				
				if (cur == topLeft) {
					if (possibleNeighbors[cur][0] != 0) {
						if (possibleNeighbors[cur][2] != 0) {
							// top and left
							// rotate me 180
							map[i][j] = rotate(rotate(tiles[cur]));
							cur = possibleNeighbors[cur][0]; // left
						} else {
							// rotate me 90
							map[i][j] = rotate(tiles[cur]);
							cur = possibleNeighbors[cur][3]; // bottom
						}
					} else {
						if (possibleNeighbors[cur][2] != 0) {
							// top and right
							// flip me vert
							map[i][j] = flipV(tiles[cur]);
							cur = possibleNeighbors[cur][1]; // right
							
						} else {
							// me nothing ;)
							map[i][j] = copy(tiles[cur]);
							cur = possibleNeighbors[cur][1]; // right
						}
					}
				} else {
					map[i][j] = copy(tiles[cur]);
					if (j > 0) {
						if (indMap[i][j-1] == possibleNeighbors[cur][0]) {
							// left to left
							boolean flipped = false;
							if (leftOfTile(map[i][j]).equals(rightOfTile(map[i][j-1]))) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipV(map[i][j]);
								flipped = true;
							}
							if (j < mapN - 1) cur = possibleNeighbors[cur][1]; // right
							else cur = !flipped ? possibleNeighbors[cur][3] : possibleNeighbors[cur][2];
						} else if (indMap[i][j-1] == possibleNeighbors[cur][1]) {
							// right to left 
							map[i][j] = flipH(map[i][j]);
							boolean flipped = false;
							if (leftOfTile(map[i][j]).equals(rightOfTile(map[i][j-1]))) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipV(map[i][j]);
								flipped = true;
							}
							if (j < mapN - 1) cur = possibleNeighbors[cur][0]; // left
							else cur = !flipped ? possibleNeighbors[cur][3] : possibleNeighbors[cur][2];
						} else if (indMap[i][j-1] == possibleNeighbors[cur][2]) {
							// top to left
							map[i][j] = rotate(map[i][j]);
							boolean flipped = false;
							if (leftOfTile(map[i][j]).equals(rightOfTile(map[i][j-1]))) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipV(map[i][j]);
								flipped = true;
							}
							if (j < mapN - 1) cur = possibleNeighbors[cur][3]; // bottom
							else cur = !flipped ? possibleNeighbors[cur][0] : possibleNeighbors[cur][1];
						} else {
							// bottom to left
							map[i][j] = rotate(rotate(rotate(map[i][j])));
							boolean flipped = false;
							if (leftOfTile(map[i][j]).equals(rightOfTile(map[i][j-1]))) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipV(map[i][j]);
								flipped = true;
							}
							if (j < mapN - 1) cur = possibleNeighbors[cur][2]; // top
							else cur = !flipped ? possibleNeighbors[cur][1] : possibleNeighbors[cur][0];
						}
					} else {
						if (indMap[i-1][j] == possibleNeighbors[cur][0]) {
							// left to top
							map[i][j] = rotate(rotate(rotate(map[i][j])));
							boolean flipped = false;
							if (map[i][j][0].equals(map[i-1][j][n-1])) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipH(map[i][j]);
								flipped = true;
							}
							cur = !flipped ? possibleNeighbors[cur][2] : possibleNeighbors[cur][3];
						} else if (indMap[i-1][j] == possibleNeighbors[cur][1]) {
							// right to top 
							map[i][j] = rotate(map[i][j]);
							boolean flipped = false;
							if (map[i][j][0].equals(map[i-1][j][n-1])) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipH(map[i][j]);
								flipped = true;
							}
							cur = !flipped ? possibleNeighbors[cur][3] : possibleNeighbors[cur][2];
						} else if (indMap[i-1][j] == possibleNeighbors[cur][2]) {
							// top to top
							//map[i][j] = rotate(map[i][j]);
							boolean flipped = false;
							if (map[i][j][0].equals(map[i-1][j][n-1])) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipH(map[i][j]);
								flipped = true;
							}
							cur = !flipped ? possibleNeighbors[cur][1] : possibleNeighbors[cur][0];
						} else {
							// bottom to top
							map[i][j] = flipV(map[i][j]);
							boolean flipped = false;
							if (map[i][j][0].equals(map[i-1][j][n-1])) {
								//map[i][j] = copy(tiles[cur]);
							} else {
								map[i][j] = flipH(map[i][j]);
								flipped = true;
							}
							cur = !flipped ? possibleNeighbors[cur][1] : possibleNeighbors[cur][0];
						}
					}
				}
			}
		}
		
		for (int i = 0; i < mapN; i++) {
			for (int inner = 0; inner < n; inner++) {
				for (int j = 0; j < mapN; j++) {
					System.out.print(map[i][j][inner] + " ");
				}
				System.out.println();
			}
		}
		
		System.out.println(normal);
		System.out.println(result);
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
