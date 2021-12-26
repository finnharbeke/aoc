import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Eighteenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input18.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		long total = 0;
		while (console.hasNextLine()) {
			long res = eval(console.nextLine());
			System.out.println(res);
			total += res;
		}
		System.out.println("====");
		System.out.println(total);
	}
	
	public static long eval(String expr) {
		System.out.println();
		System.out.print("eval: " + expr + "; ");
		long result = 0;
		boolean plus = true;
		int i = 0;
		while (i < expr.length()) {
			if (expr.charAt(i) == ' ') {}
			else if (expr.charAt(i) == '+') {
				plus = true;
			} else if (expr.charAt(i) == '*') {
				plus = false;
			} else {
				long num;
				if (expr.charAt(i) == '(') {
					int start = i + 1;
					int open = 1;
					while (open > 0) {
						i++;
						if (expr.charAt(i) == '(') open++;
						else if (expr.charAt(i) == ')') open--;
					}
					num = eval(expr.substring(start, i));
				} else {
					num = expr.charAt(i) - '0';
				}
				
				if (plus) result += num;
				else result *= num;
				
				System.out.print(plus ? "+" : "*" + num + "=" + result +", ");
			}
			i++;
		}
		System.out.println();
		return result;
	}
	
	public static void part2(Scanner console) {
		long total = 0;
		while (console.hasNextLine()) {
			long res = eval2(console.nextLine());
			System.out.println(res);
			total += res;
		}
		System.out.println("====");
		System.out.println(total);
	}
	
	public static long eval2(String expr) {
		//System.out.println();
		//System.out.print("eval: " + expr + "; ");
		
		long[] mults = new long[100];
		int mult_ind = 0;
		
		int i = 0;
		while (i < expr.length()) {
			if (expr.charAt(i) == ' ') {}
			else if (expr.charAt(i) == '+') {
				//plus = true;
			} else if (expr.charAt(i) == '*') {
				//plus = false;
				mult_ind++;
			} else {
				long num;
				if (expr.charAt(i) == '(') {
					int start = i + 1;
					int open = 1;
					while (open > 0) {
						i++;
						if (expr.charAt(i) == '(') open++;
						else if (expr.charAt(i) == ')') open--;
					}
					num = eval2(expr.substring(start, i));
				} else {
					num = expr.charAt(i) - '0';
				}
				
				mults[mult_ind] += num;
				
				//System.out.print(plus ? "+" : "*" + num + "=" + result +", ");
			}
			i++;
		}
		System.out.print("[");
		long result = 1;
		for (i = 0; i <= mult_ind; i++) {
			System.out.print(mults[i] + " * ");
			result *= mults[i];
		}
		System.out.println("] = " + result);
		//System.out.println();
		return result;
	}

}
