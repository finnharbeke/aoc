import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Fifteenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input15.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int n = 7;
		int[] starting = new int[n];
		int i = 0;
		for (String num : console.nextLine().split(",")) {
			starting[i] = Integer.valueOf(num);
			i++;
		}
		HashMap<Integer, Integer> lasttime = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		int last = 0;
		for (i = 1; i <= 2020; i++) {
			if (i <= n) {
				count.put(starting[i-1], count.getOrDefault(starting[i-1], 0)+1);
				lasttime.put(starting[i-1], i);
				last = starting[i-1];
			} else {
				int curr;
				if (count.get(last) == 1) {
					curr = 0;
				} else {
					curr = i - 1 - lasttime.get(last);
				}
				System.out.println(i + ": " + curr);
				count.put(curr, count.getOrDefault(curr, 0)+1);
				lasttime.put(last, i-1);
				last = curr;
			}
		}
	}
	
	public static void part2(Scanner console) {
		int[] start = {2,0,1,7,4,14,18};
		HashMap<Integer, Integer> lasttime = new HashMap<Integer, Integer>();
		for (int i = 0; i < start.length; i++) lasttime.put(start[i], i+1);
		int last = start[start.length-1], curr;
		for (int i = start.length; i < 30000000; i++) {
			curr = i - lasttime.getOrDefault(last, i);
			lasttime.put(last, i);
			last = curr;
		}
		System.out.println(last);
	}

}
