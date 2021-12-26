import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

public class Seventh {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input7.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashMap<String, HashMap<String, Integer>> d = new HashMap<String, HashMap<String, Integer>>();
		while (console.hasNext()) {
			Scanner line = new Scanner(console.nextLine());
			String color = "";
			String word = line.next();
			while (!word.equals("bags")) {
				if (!color.equals("")) color += " ";
				color += word;
				word = line.next();
			}
			line.next(); //contain
			while (line.hasNext()) {
				if (line.hasNextInt()) {
					int amount = line.nextInt();
					String childcolor = "";
					word = line.next();
					while (!word.startsWith("bag")) {
						if (!childcolor.equals("")) childcolor += " ";
						childcolor += word;
						word = line.next();
					}
					if (!d.containsKey(childcolor)) {
						d.put(childcolor, new HashMap<String, Integer>());
					}
					d.get(childcolor).put(color, amount);
					
				} else if (line.next().equals("no")) {
					break;
				} else {
					System.out.println("WTF");
				}
			}
			
		}
		HashSet<String> possibilities = recur("shiny gold", d);
		System.out.println(possibilities.size());
		System.out.println(d.get("dotted turquoise"));
	}
	
	private static HashSet<String> recur(String key, HashMap<String, HashMap<String, Integer>> d) {
		HashSet<String> myset = new HashSet<String>();
		if (!key.equals("shiny gold")) myset.add(key);
		if (!d.containsKey(key)) return myset;
		for (String nextKey : d.get(key).keySet()) {
			//System.out.println(myset);
			myset.addAll(recur(nextKey, d));
			//System.out.println(myset);
		}
		return myset;
	} 

	public static void part2(Scanner console) {
		HashMap<String, HashMap<String, Integer>> d = new HashMap<String, HashMap<String, Integer>>();
		while (console.hasNext()) {
			Scanner line = new Scanner(console.nextLine());
			String color = "";
			String word = line.next();
			while (!word.equals("bags")) {
				if (!color.equals("")) color += " ";
				color += word;
				word = line.next();
			}
			d.put(color, new HashMap<String, Integer>());
			line.next(); //contain
			while (line.hasNext()) {
				if (line.hasNextInt()) {
					int amount = line.nextInt();
					String childcolor = "";
					word = line.next();
					while (!word.startsWith("bag")) {
						if (!childcolor.equals("")) childcolor += " ";
						childcolor += word;
						word = line.next();
					}
					d.get(color).put(childcolor, amount);
					
				} else if (line.next().equals("no")) {
					break;
				} else {
					System.out.println("WTF");
				}
			}
			
		}
		int possibilities = recur2("shiny gold", 1, d) -1;
		System.out.println(possibilities);
	}
	
	private static int recur2(String key, int multiplier, HashMap<String, HashMap<String, Integer>> d) {
		int total = multiplier;
		for (String nextKey : d.get(key).keySet()) {
			//System.out.println(myset);
			total += recur2(nextKey, d.get(key).get(nextKey) * multiplier, d);
			//System.out.println(myset);
		}
		return total;
	} 

}
