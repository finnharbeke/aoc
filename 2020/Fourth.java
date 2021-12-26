import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Fourth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("message-3.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		HashMap<String, Boolean> HT = new HashMap<String, Boolean>();
		Set<String> a = new HashSet<String>();  
        a.addAll(Arrays.asList(new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}));  
        Set<String> b = new HashSet<String>();  
        b.addAll(Arrays.asList(new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"})); 
		int valid = 0; 
		while (console.hasNext()) {
			String line = console.nextLine();
			//System.out.println(line);
			if (line.equals("")) {
				for (String i : HT.keySet()) {
				      System.out.println("key: " + i + " value: " + HT.get(i));
				    }
				Set<String> s = HT.keySet();
				if (s.equals(a) || s.equals(b)) {
					valid++;
					System.out.print("1");
				} else {
					System.out.print("0");
				}
				
				HT = new HashMap<String, Boolean>();
			} else {
				 while (line.indexOf(' ') != -1) {
					 String part = line.substring(0, line.indexOf(' '));
					 HT.put(part.substring(0, part.indexOf(':')), true);
					 line = line.substring(line.indexOf(' ') + 1);
				 }
				 HT.put(line.substring(0, line.indexOf(':')), true);
			}
		}
		Set<String> s = HT.keySet();
		if (s.equals(a) || s.equals(b)) {
			valid++;
			System.out.print("1");
		} else {
			System.out.print("0");
		}
		System.out.println(valid);
	}
	
	private static HashSet<String> check(HashSet<String> hs, String s) {
		String key = s.substring(0, s.indexOf(':'));
		String value = s.substring(s.indexOf(':') + 1);
		if (key.equals("byr")) {
			int year = Integer.valueOf(value);
			if (1920 <= year && year <= 2002) hs.add(key);
		} else if (key.equals("iyr")) {
			int year = Integer.valueOf(value);
			if (2010 <= year && year <= 2020) hs.add(key);
		} else if (key.equals("eyr")) {
			int year = Integer.valueOf(value);
			if (2020 <= year && year <= 2030) hs.add(key);
		} else if (key.equals("hgt")) {
			if (value.substring(value.length() - 2).equals("cm")) {
				int number = Integer.valueOf(value.substring(0, value.length() - 2));
				if (150 <= number && number <= 193) hs.add(key);
			} else if (value.substring(value.length() - 2).equals("in")) {
				int number = Integer.valueOf(value.substring(0, value.length() - 2));
				if (59 <= number && number <= 76) hs.add(key);
			}
		} else if (key.equals("hcl")) {
			if (value.charAt(0) == '#' && value.length() == 7) {
				boolean correct = true;
				Set<String> fine = new HashSet<String>();  
		        fine.addAll(Arrays.asList(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"})); 
				for (int i = 1; i < 6; i++) {
					if (!fine.contains(value.substring(i, i+1))) {
						correct = false;
						break;
					}
				}
				if (correct) hs.add(key);
			}
		} else if (key.equals("ecl")) {
			Set<String> fine = new HashSet<String>();  
	        fine.addAll(Arrays.asList(new String[] {"amb", "blu", "brn" ,"gry", "grn", "hzl", "oth"}));
	        if (fine.contains(value)) hs.add(key);
			
		} else if (key.equals("pid")) {
			if (value.length() == 9) {
				boolean correct = true;
				for (char ch : value.toCharArray()) {
					if (!Character.isDigit(ch)) {
						correct = false;
						break;
					}
				}
				if (correct) hs.add(key);
			}
		} else if (key.equals("cid")) {
		} else {
			hs.add(key);
		}
		return hs;
	}

	public static void part2(Scanner console) {
		HashSet<String> HS = new HashSet<String>();
		Set<String> a = new HashSet<String>();  
        a.addAll(Arrays.asList(new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}));
		int valid = 0; 
		while (console.hasNext()) {
			String line = console.nextLine();
			//System.out.println(line);
			if (line.equals("")) {
				if (HS.equals(a)) {
					valid++;
					System.out.print("1");
				} else {
					System.out.print("0");
				}
				HS = new HashSet<String>();
			} else {
				 while (line.indexOf(' ') != -1) {
					 String part = line.substring(0, line.indexOf(' '));
					 HS = check(HS, part);
					 line = line.substring(line.indexOf(' ') + 1);
				 }
				 HS = check(HS, line);
			}
		}
		if (HS.equals(a)) {
			valid++;
			System.out.print("1");
		} else {
			System.out.print("0");
		}
		System.out.println();
		System.out.println(valid);
	}

}
