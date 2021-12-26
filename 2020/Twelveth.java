import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Twelveth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input12.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		double north = 0;
		double east = 0;
		double angle = 0;
		while (console.hasNextLine()){
			String line = console.nextLine();
			int number = Integer.valueOf(line.substring(1));
			switch (line.charAt(0)) {
				case 'N':
					north += number;
					break;
				case 'W':
					east -= number;
					break;
				case 'S':
					north -= number;
					break;
				case 'E':
					east += number;
					break;
				case 'R':
					angle -= number;
					break;
				case 'L':
					angle += number;
					break;
				case 'F':
					north += Math.sin(angle / 180.0 * Math.PI) * number;
					east += Math.cos(angle / 180.0 * Math.PI) * number;
					break;
			}
			System.out.println(line + " " + number + " " + north + " " + east);
		}
		System.out.println(north + " " + east);
		System.out.println(Math.abs(north) + Math.abs(east));
	}
	
	public static void part2(Scanner console) {
		double northWP = 1.0;
		double eastWP = 10.0;
		double shipN = 0;
		double shipE = 0;
		while (console.hasNextLine()){
			String line = console.nextLine();
			int number = Integer.valueOf(line.substring(1));
			double angle, norm;
			char c = line.charAt(0);
			switch (c) {
				case 'N':
					northWP += number;
					break;
				case 'W':
					eastWP -= number;
					break;
				case 'S':
					northWP -= number;
					break;
				case 'E':
					eastWP += number;
					break;
				case 'R':case'L':
					angle = eastWP == 0.0 ? (northWP > 0 ? Math.PI/2 : -Math.PI/2) : Math.atan(northWP / eastWP);
					if (eastWP < 0) angle += Math.PI;
					int sign = c == 'L' ? 1 : -1;
					angle += sign * number / 180.0 * Math.PI;
					norm = Math.sqrt(northWP * northWP + eastWP * eastWP);
					northWP = Math.sin(angle) * norm;
					eastWP = Math.cos(angle) * norm;
					break;
				case 'F':
					shipN += northWP * number;
					shipE += eastWP * number;
					break;
			}
			System.out.println(line + " " + number + " " + northWP + " " + eastWP + " " + shipN + " " + shipE);
		}
		System.out.println(shipN + " " + shipE);
		System.out.println(Math.round(Math.abs(shipN) + Math.abs(shipE)));
	}

}