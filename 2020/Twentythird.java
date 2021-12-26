import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Twentythird {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner("739862541");
		//Scanner console = new Scanner("123456789");
		//Scanner console = new Scanner("389125467");
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		int m = 10;
		int mod = 9;
		
		char[] chars = console.next().toCharArray();
		int[] cups = new int[chars.length];
		for (int i = 0; i < chars.length; i++) {
			cups[i] = chars[i] - '0';
		}
		
		int n = cups.length;
		
		print(cups);
		
		int current = 0;
		
		for (int r = 0; r < m; r++) {
			
			System.out.print("curr: " + current);
			
			int dest_val = wrap(cups[current] - 1, mod);
			
			int dest = -1;
			while (dest == -1) {
				System.out.print(" dest_val: " + dest_val);
				for (int i = 0; i < n; i++) {
					if (dest_val == cups[i]) {
						if (i == (current + 1) % n || i == (current + 2) % n || i == (current + 3) % n) {
							dest_val = wrap(dest_val - 1, mod);
							break;
						} else {
							dest = i;
							break;
						}
						
					}
				}
			}
			System.out.println(" dest: " + dest);
			
			int pick1 = cups[(current + 1) % n];
			int pick2 = cups[(current + 2) % n];
			int pick3 = cups[(current + 3) % n];
			
			int i = (current + 4) % n;
			
			while (i != (dest + 1) % n) {
				cups[(i - 3 + n) % n] = cups[i];
				i = (i + 1) % n;
			}
			
			cups[(i - 3 + n) % n] = pick1;
			i = (i + 1) % n;
			cups[(i - 3 + n) % n] = pick2;
			i = (i + 1) % n;
			cups[(i - 3 + n) % n] = pick3;
			
			current = (current + 1) % n;
			
			print(cups);
		}
		
		String res = "";
		int i = 0;
		while (cups[i] != 1) i++;
		
		i = (i + 1) % n;
		
		while (cups[i] != 1) {
			res += cups[i];
			i = (i + 1) % n;
		}
		System.out.println(res);
	}
	
	public static void part2(Scanner console) {
		int m = 10000000;
		int n = 1000000;
		int C = 9;
		
		char[] chars = console.next().toCharArray();
		
		Node current = new Node(chars[0] - '0');
		Node one = null;
		Node runner = current;
		Node prev;
		Node max = current;
		
		HashMap<Integer, Node> firstDests = new HashMap<Integer, Node>();
		firstDests.put(current.label, current);
		
		for (int i = 1; i < C; i++) {
			prev = runner;
			runner = new Node(chars[i] - '0');
			if (runner.label == 1) one = runner;
			firstDests.put(runner.label, runner);
			prev.setNext(runner);
			
			if (runner.label > max.label) max = runner;
		}
		
		runner = current;
		
		for (int i = 0; i < C; i++) {
			if (runner != one) runner.setDest(firstDests.get(runner.label - 1));
			if (i != chars.length - 1) runner = runner.next();
		}
		
		prev = runner;
		
		runner = new Node(C + 1);
		prev.setNext(runner);
		runner.setDest(max);
		
		for (int i = C + 2; i <= n; i++) {
			prev = runner;
			runner = new Node(i);
			runner.setDest(prev);
			prev.setNext(runner);
		}
		
		runner.setNext(current);
		if (C == n)
			one.setDest(max);
		else
			one.setDest(runner);
		
		//print2(current);
		
		for (int r = 0; r < m; r++) {
			
			Node dest = current.dest();
			Node pick1 = current.next();
			Node pick2 = pick1.next();
			Node pick3 = pick2.next();
			
			while (dest == pick1 || dest == pick2 || dest == pick3) {
				dest = dest.dest();
			}
			
			current.setNext(pick3.next());
			pick3.setNext(dest.next());
			dest.setNext(pick1);
			
			//print2(current);
			current = current.next();
		}
		
		Node label1 = one.next();
		Node label2 = label1.next();
		long res = (long)label1.label * label2.label;
		System.out.println(label1.label + " * " + label2.label + " = " + res);
		
	}
	
	public static int wrap(int in, int mod) {
		int res = (in + mod) % mod;
		if (res == 0) res = mod;
		return res;
	}

	public static void print(int[] arr) {
		System.out.print("[");
		for (int i = 0; i < arr.length - 1; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println(arr[arr.length - 1] + "]");
	}
	
	public static void print2(Node n) {
		System.out.print("[" + n.label + ";" + n.dest().label + " ");
		Node r = n.next();
		while (r != n) {
			System.out.print(r.label + ";" + r.dest().label + " ");
			r = r.next();
			
		}
		System.out.println("]");
	}
}

class Node {
	private Node next;
	private Node dest;
	public int label;
	
	Node(int l)  {
		this.label = l;
	}
	
	public Node next() {
		return this.next;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	

	public Node dest() {
		return this.dest;
	}
	
	public void setDest(Node dest) {
		this.dest = dest;
	}
}
