import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Twentysecond {
	
	public static void main(String[] args) throws FileNotFoundException {
		final Scanner console = new Scanner(new File("input22.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		LinkedList<Integer> player1 = new LinkedList<Integer>();
		LinkedList<Integer> player2 = new LinkedList<Integer>();
		
		console.nextLine();
		String line = console.nextLine();
		while (console.hasNextLine()) {
			if (line.equals("")) break;
			System.out.println("\"" + line + "\"");
			player1.addLast(Integer.valueOf(line));
			line = console.nextLine();
		}
		console.nextLine();
		while (console.hasNextLine()) {
			line = console.nextLine();
			if (line.equals("")) break;
			System.out.println("\"" + line + "\"");
			player2.addLast(Integer.valueOf(line));
		}
		
		System.out.println(player1);
		System.out.println(player2);
		
		while (!player1.isEmpty() && !player2.isEmpty()) {
			int card1 = player1.pollFirst();
			int card2 = player2.pollFirst();
			
			if (card1 > card2) {
				player1.addLast(card1);
				player1.addLast(card2);
			} else {
				player2.addLast(card2);
				player2.addLast(card1);
			}
		}
		
		LinkedList<Integer> winner = player1.isEmpty() ? player2 : player1;
		
		long score = 0;
		for (int i = 0; i < winner.size(); i++) {
			score += winner.get(i) * (winner.size() - i);
		}
		
		System.out.println(score);
		
		
	}
	
	public static void part2(Scanner console) {
		LinkedList<Integer> player1 = new LinkedList<Integer>();
		LinkedList<Integer> player2 = new LinkedList<Integer>();
		
		console.nextLine();
		String line = console.nextLine();
		while (console.hasNextLine()) {
			if (line.equals("")) break;
			System.out.println("\"" + line + "\"");
			player1.addLast(Integer.valueOf(line));
			line = console.nextLine();
		}
		console.nextLine();
		while (console.hasNextLine()) {
			line = console.nextLine();
			if (line.equals("")) break;
			System.out.println("\"" + line + "\"");
			player2.addLast(Integer.valueOf(line));
		}
		
		System.out.println(player1);
		System.out.println(player2);
		
		recursiveCombat(player1, player2, 0);
		
		//System.out.println(score);
	}

	private static boolean recursiveCombat(LinkedList<Integer> player1, LinkedList<Integer> player2, int i) {
		System.out.println("Starting Game: level " + i);
		LinkedList<LinkedList<LinkedList<Integer>>> history = new LinkedList<LinkedList<LinkedList<Integer>>>();
		boolean player1def = false;
		while (!player1.isEmpty() && !player2.isEmpty()) {
			if (inhistory(player1, player2, history)) {
				System.out.println("in history!!");
				player1def = true;
				break;
			}
			LinkedList<LinkedList<Integer>> state = new LinkedList<LinkedList<Integer>>();
			state.addLast(copy(player1, player1.size()));
			state.addLast(copy(player2, player2.size()));
			
			history.addLast(state);
			
			int card1 = player1.pollFirst();
			int card2 = player2.pollFirst();
			
			if (card1 > player1.size() || card2 > player2.size()) {
				// normal
				if (card1 > card2) {
					player1.addLast(card1);
					player1.addLast(card2);
				} else {
					player2.addLast(card2);
					player2.addLast(card1);
				}
			} else {
				if (recursiveCombat(copy(player1, card1), copy(player2, card2), i+1)) {
					// player1's win
					player1.addLast(card1);
					player1.addLast(card2);
				} else {
					player2.addLast(card2);
					player2.addLast(card1);
				}
			}
		}
		
		if (i == 0) {
			LinkedList<Integer> winner = player2.isEmpty() || player1def ? player1 : player2;
			
			long score = 0;
			for (int ind = 0; ind < winner.size(); ind++) {
				score += winner.get(ind) * (winner.size() - ind);
			}
			
			System.out.println(score);
		}
		
		System.out.println("player1 won: " + (player1def || player2.isEmpty()));
		return player1def || player2.isEmpty();
		
	}
	
	private static boolean inhistory(LinkedList<Integer> player1, LinkedList<Integer> player2,
			LinkedList<LinkedList<LinkedList<Integer>>> history) {
		
		for (int i = 0; i < history.size(); i++) {
			if (player1.equals(history.get(i).get(0)) && player2.equals(history.get(i).get(1))) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean same(LinkedList<Integer> first, LinkedList<Integer> other) {
		if (first.size() != other.size()) return false;
		
		for (int i = 0; i < first.size(); i++) {
			if (!first.get(i).equals(other.get(i))) return false;
		}
		
		return true;
	}

	private static LinkedList<Integer> copy(LinkedList<Integer> original, int number) {
		LinkedList<Integer> res = new LinkedList<Integer>();
		for (int i = 0; i < number; i++) {
			res.addLast(original.get(i));
		}
		return res;
		
	}

}
