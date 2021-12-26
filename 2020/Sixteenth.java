import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Sixteenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input16.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		String line;
		HashMap<String, int[]> fields = new HashMap<String, int[]>();
		while (console.hasNextLine()) {
			line = console.nextLine();
			System.out.println("\"" + line + "\"");
			if (line.equals("")) break;
			String field = line.substring(0, line.indexOf(':'));
			line = line.substring(line.indexOf(':')+2);
			String first = line.split(" ")[0];
			int a = Integer.valueOf(first.substring(0, first.indexOf('-')));
			int b = Integer.valueOf(first.substring(first.indexOf('-')+1));
			String second = line.split(" ")[2];
			int c = Integer.valueOf(second.substring(0, second.indexOf('-')));
			int d = Integer.valueOf(second.substring(second.indexOf('-')+1));
			fields.put(field, new int[] {a, b, c, d});
		}
		
		console.nextLine(); // yt
		console.nextLine(); // yt numbers
		console.nextLine(); // empty
		console.nextLine(); // nt
		long total = 0;
		while (console.hasNextLine()) {
			String[] nums = console.nextLine().split(",");
			for (String num : nums) {
				boolean valid = false;
				int n = Integer.valueOf(num);
				for (String field : fields.keySet()) {
					int[] arr = fields.get(field);
					System.out.print("{" + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + "} ");
					if ((arr[0] <= n && n <= arr[1]) || (arr[2] <= n && n <= arr[3])) {
						valid = true;
						break;
					}
				}
				if (!valid) {
					System.out.print(n + " ");
					total += n;
				}
			}
		}
		System.out.println();
		System.out.println(total);
	}
	
	public static void part2(Scanner console) {
		String line;
		HashMap<String, int[]> fields = new HashMap<String, int[]>();
		while (console.hasNextLine()) {
			line = console.nextLine();
			System.out.println("\"" + line + "\"");
			if (line.equals("")) break;
			String field = line.substring(0, line.indexOf(':'));
			line = line.substring(line.indexOf(':')+2);
			String first = line.split(" ")[0];
			int a = Integer.valueOf(first.substring(0, first.indexOf('-')));
			int b = Integer.valueOf(first.substring(first.indexOf('-')+1));
			String second = line.split(" ")[2];
			int c = Integer.valueOf(second.substring(0, second.indexOf('-')));
			int d = Integer.valueOf(second.substring(second.indexOf('-')+1));
			fields.put(field, new int[] {a, b, c, d});
		}
		
		console.nextLine(); // yt
		String[] mynums = console.nextLine().split(","); // yt numbers
		console.nextLine(); // empty
		console.nextLine(); // nt
		HashMap<String, HashSet<Integer>> sets = new HashMap<String, HashSet<Integer>>();
		for (String field : fields.keySet()) {
			sets.put(field, new HashSet<Integer>());
			for (int i = 0; i < fields.keySet().size(); i++) {
				sets.get(field).add(i);
			}
		}
		while (console.hasNextLine()) {
			String[] nums = console.nextLine().split(",");
			boolean validline = true;
			for (String num : nums) {
				boolean valid = false;
				int n = Integer.valueOf(num);
				for (String field : fields.keySet()) {
					int[] arr = fields.get(field);
//					System.out.print("{" + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + "} ");
					if ((arr[0] <= n && n <= arr[1]) || (arr[2] <= n && n <= arr[3])) {
						valid = true;
						break;
					}
				}
				if (!valid) {
					validline = false;
					break;
				}
			}
			if (!validline) continue;
			for (int i = 0; i < fields.keySet().size(); i++) {
				int n = Integer.valueOf(nums[i]);
				for (String field : fields.keySet()) {
					int[] arr = fields.get(field);
//					System.out.print("{" + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + "} ");
					if (!((arr[0] <= n && n <= arr[1]) || (arr[2] <= n && n <= arr[3]))) {
						sets.get(field).remove(i);
					}
				}
			}
		}
		boolean all1s = false;
		while (!all1s) {
			all1s = true;
			for (String field : fields.keySet()) {
				if (sets.get(field).size() == 1) {
					int ind = (int) sets.get(field).toArray()[0];
					for (String field2 : fields.keySet()) {
						if (!field.equals(field2)) sets.get(field2).remove(ind);
					}
				} else {
					all1s = false;
				}
			}
		}
		long result = 1;
		for (String field : fields.keySet()) {
			if (field.startsWith("departure")) {
				System.out.println(field + ": " + sets.get(field).toString());
				int ind = (int) sets.get(field).toArray()[0];
				int n = Integer.valueOf(mynums[ind]);
				result *= n;
				System.out.println(n);
			}
		}
		System.out.println();
		System.out.println(result);
	}

}
