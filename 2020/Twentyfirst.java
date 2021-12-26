 import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Twentyfirst {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input21.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		HashMap<String, Integer> allIngrs = new HashMap<String, Integer>();
		while (console.hasNextLine()) {
			HashSet<String> set = new HashSet<String>();
			String line = console.nextLine();
			int ind = line.indexOf('(');
			String[] ingrs;
			if (ind == -1) ingrs = line.split(" ");
			else ingrs = line.substring(0, ind).strip().split(" ");
			for (String word : ingrs) {
				set.add(word);
			}
			ind += "(contains ".length();
			String[] allergens = line.substring(ind, line.length()-1).split(", ");
			
			for (String ingr : set) 
				allIngrs.put(ingr, allIngrs.getOrDefault(ingr, 0) + 1);
			
			for (String all : allergens) {
				if (map.containsKey(all)) {
					map.get(all).retainAll(set);
				} else {
					map.put(all, (HashSet<String>)set.clone());
				}
			}
		}
		
		System.out.println(map);
		System.out.println(allIngrs);
		
		int total = 0;
		for (String ingr : allIngrs.keySet()) {
			boolean found = false;
			for (String all : map.keySet()) {
				if (map.get(all).contains(ingr)) {
					found = true;
					break;
				}
			}
			if (!found) {
				total += allIngrs.get(ingr);
			}
		}
		
		System.out.println(total);
	}
	
	public static void part2(Scanner console) {
		HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		HashMap<String, Integer> allIngrs = new HashMap<String, Integer>();
		while (console.hasNextLine()) {
			HashSet<String> set = new HashSet<String>();
			String line = console.nextLine();
			int ind = line.indexOf('(');
			String[] ingrs;
			if (ind == -1) ingrs = line.split(" ");
			else ingrs = line.substring(0, ind).strip().split(" ");
			for (String word : ingrs) {
				set.add(word);
			}
			ind += "(contains ".length();
			String[] allergens = line.substring(ind, line.length()-1).split(", ");
			
			for (String ingr : set) 
				allIngrs.put(ingr, allIngrs.getOrDefault(ingr, 0) + 1);
			
			for (String all : allergens) {
				if (map.containsKey(all)) {
					map.get(all).retainAll(set);
				} else {
					map.put(all, (HashSet<String>)set.clone());
				}
			}
		}
		
		System.out.println(map);
		System.out.println(allIngrs);
		
		HashSet<String> done = new HashSet<String>();
		
		boolean all1 = false;
		while (!all1) {
			all1 = true;
			for (String all : map.keySet()) {
				if (!done.contains(all) && map.get(all).size() == 1) {
					System.out.println("found 1 ingr all: " + all + "; " + map.get(all).toArray()[0]);
					for (String other : map.keySet()) {
						if (other.equals(all)) continue;
						else map.get(other).remove(map.get(all).toArray()[0]);
					}
					done.add(all);
					break;
				} else {
					all1 = false;
				}
			}
		}
		
		Object[] alls = map.keySet().toArray(); 
		Arrays.sort(alls);
		
		String dangerous = "";
		
		for (Object all : alls) {
			System.out.println(all + ": " + map.get(all));
			dangerous += map.get(all).toArray()[0] + ",";
		}
		
		dangerous = dangerous.substring(0, dangerous.length()-1);
		
		System.out.println(dangerous);
	}

}
