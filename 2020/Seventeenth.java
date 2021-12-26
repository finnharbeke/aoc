import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Seventeenth {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(new File("input17.txt"));
		part2(console);
		
	}
	
	public static void part1(Scanner console) {
		//int n = 8;
		int zOff = 0;
		int xOff = 0;
		int yOff = 0;
		int zSpan = 1;
		int xSpan = 8;
		int ySpan = 8;
		int steps = 6;
		
		ArrayList<ArrayList<ArrayList<Character>>> map = new ArrayList<ArrayList<ArrayList<Character>>>();
		
		int z = zOff;
		int y = yOff;
		map.add(new ArrayList<ArrayList<Character>>());
		while (console.hasNextLine()) {
			map.get(z-zOff).add(new ArrayList<Character>());
			String line = console.nextLine();
			for (int x = xOff; x < line.length() + xOff; x++) {
				map.get(z-zOff).get(y-yOff).add(line.charAt(x-xOff));
			}
			y++;
		}
		
		ArrayList<ArrayList<ArrayList<Character>>> newMap = myclone(map);
		
		int total = 0;
		
		for (int step = 0; step < steps; step++) {
			System.out.println ("new Step");
			total = 0;
			zOff--; xOff--; yOff--;
			zSpan += 2; xSpan += 2; ySpan += 2;
			newMap.add(0, new ArrayList<ArrayList<Character>>());
			newMap.add(new ArrayList<ArrayList<Character>>());
			// fill new z-Planes
			for (y = yOff; y < yOff + ySpan; y++) {
				newMap.get(0).add(new ArrayList<Character>());
				newMap.get(newMap.size()-1).add(new ArrayList<Character>());
				for (int x = xOff; x < xOff + xSpan; x++) {
					newMap.get(0).get(y-yOff).add('.');
					newMap.get(newMap.size()-1).get(y-yOff).add('.');
				}
			}
			// enlarge old z-Planes
			for (z = zOff + 1; z < zOff + zSpan - 1; z++) {
				// new ys
				newMap.get(z-zOff).add(0, new ArrayList<Character>());
				newMap.get(z-zOff).add(new ArrayList<Character>());
				for (y = yOff; y < yOff + ySpan; y++) {
					for (int x = xOff; x < xOff + xSpan; x++) {
						if (y - yOff == 0 || y - yOff == ySpan - 1) newMap.get(z-zOff).get(y-yOff).add('.');
						else if (x - xOff == 0) newMap.get(z-zOff).get(y-yOff).add(0, '.');
						else if (x - xOff == xSpan -1) newMap.get(z-zOff).get(y-yOff).add('.');
					}
				}
			}
			
			for (z = zOff; z < zOff + zSpan; z++) {
				//if (z == zOff || z == zOff + zSpan - 1) newMap.add(z - zOff, new ArrayList<ArrayList<Character>>());
				for (y = yOff; y < yOff + ySpan; y++) {
					//if (z == zOff || z == zOff + zSpan - 1 ||
					//	y == yOff || y == yOff + ySpan - 1) newMap.get(z-zOff).add(y-yOff, new ArrayList<Character>());
					for (int x = xOff; x < xOff + xSpan; x++) {
						//if (z == zOff || z == zOff + zSpan - 1 ||
						//	y == yOff || y == yOff + ySpan - 1 ||
						//	x == xOff || x == xOff + xSpan - 1) newMap.get(z-zOff).get(y-yOff).add(z-zOff, '.');
						System.out.println ("at new Tile");
						
						int neighbors = 0;
						for (int zLook = -1; zLook <= 1; zLook++) {
							if (z + zLook <= zOff || z + zLook >= zOff + zSpan -1) continue;
							for (int yLook = -1; yLook <= 1; yLook++) {
								if (y + yLook <= yOff || y + yLook >= yOff + ySpan -1) continue;
								for (int xLook = -1; xLook <= 1; xLook++) {
									if (x + xLook <= xOff || x + xLook >= xOff + xSpan -1) continue;
									else if (zLook == 0 && yLook == 0 && xLook == 0) continue;
									else {
										//System.out.println("zOff: " + zOff + " zSpan: " + zSpan + " z: " + z + " zLook: " + zLook);
										//System.out.println("yOff: " + yOff + " ySpan: " + ySpan + " y: " + y + " yLook: " + yLook);
										//System.out.println("xOff: " + xOff + " xSpan: " + xSpan + " x: " + x + " xLook: " + xLook);
										if (map.get(z+zLook-zOff-1).get(y+yLook-yOff-1).get(x+xLook-xOff-1) == '#') neighbors++;
									}									
								}
							}
						}
						
						if (newMap.get(z-zOff).get(y-yOff).get(x-xOff) == '.') {
							if (neighbors == 3) {
								newMap.get(z-zOff).get(y-yOff).set(x-xOff, '#');
								total++;
							}
						} else {
							if (!(neighbors == 3 || neighbors == 2)) {
								newMap.get(z-zOff).get(y-yOff).set(x-xOff, '.');
							} else {
								total++;
							}
						}
						
					}
				}
			}
			
			map = myclone(newMap);
			
			System.out.println(total);
		}
	}
	
	private static ArrayList<ArrayList<ArrayList<Character>>> myclone(ArrayList<ArrayList<ArrayList<Character>>> map) {
		ArrayList<ArrayList<ArrayList<Character>>> newMap = new ArrayList<ArrayList<ArrayList<Character>>>();
		for (int z = 0; z < map.size(); z++) {
			newMap.add(new ArrayList<ArrayList<Character>>());
			for (int y = 0; y < map.get(z).size(); y++) {
				newMap.get(z).add((ArrayList<Character>) map.get(z).get(y).clone());
			}
		}
		return newMap;
	}
	
	private static ArrayList<ArrayList<ArrayList<ArrayList<Character>>>> myclone2(ArrayList<ArrayList<ArrayList<ArrayList<Character>>>> map) {
		ArrayList<ArrayList<ArrayList<ArrayList<Character>>>> newMap = new ArrayList<ArrayList<ArrayList<ArrayList<Character>>>>();
		for (int w = 0; w < map.size(); w++) {
			newMap.add(new ArrayList<ArrayList<ArrayList<Character>>>());
			for (int z = 0; z < map.get(w).size(); z++) {
				newMap.get(w).add(new ArrayList<ArrayList<Character>>());
				for (int y = 0; y < map.get(w).get(z).size(); y++) {
					newMap.get(w).get(z).add((ArrayList<Character>) map.get(w).get(z).get(y).clone());
				}
			}
		}
		
		return newMap;
	}

	public static void part2(Scanner console) {
		int wOff = 0;
		int zOff = 0;
		int xOff = 0;
		int yOff = 0;
		int wSpan = 1;
		int zSpan = 1;
		int xSpan = 8;
		int ySpan = 8;
		int steps = 6;
		
		ArrayList<ArrayList<ArrayList<ArrayList<Character>>>> map = new ArrayList<ArrayList<ArrayList<ArrayList<Character>>>>();
		
		int w = wOff;
		int z = zOff;
		int y = yOff;
		map.add(new ArrayList<ArrayList<ArrayList<Character>>>());
		map.get(w-wOff).add(new ArrayList<ArrayList<Character>>());
		while (console.hasNextLine()) {
			map.get(w-wOff).get(z-zOff).add(new ArrayList<Character>());
			String line = console.nextLine();
			for (int x = xOff; x < line.length() + xOff; x++) {
				map.get(w-wOff).get(z-zOff).get(y-yOff).add(line.charAt(x-xOff));
			}
			y++;
		}
		
		ArrayList<ArrayList<ArrayList<ArrayList<Character>>>> newMap = myclone2(map);
		
		int total = 0;
		
		for (int step = 0; step < steps; step++) {
			System.out.println ("new Step");
			total = 0;
			wOff--; zOff--; xOff--; yOff--;
			wSpan += 2; zSpan += 2; xSpan += 2; ySpan += 2;
			
			
			newMap.add(0, new ArrayList<ArrayList<ArrayList<Character>>>());
			newMap.add(new ArrayList<ArrayList<ArrayList<Character>>>());
			// fill new w-planes
			for (z = zOff; z < zOff + zSpan; z++) {
				newMap.get(0).add(new ArrayList<ArrayList<Character>>());
				newMap.get(newMap.size()-1).add(new ArrayList<ArrayList<Character>>());
				for (y = yOff; y < yOff + ySpan; y++) {
					newMap.get(0).get(z-zOff).add(new ArrayList<Character>());
					newMap.get(newMap.size()-1).get(z-zOff).add(new ArrayList<Character>());
					for (int x = xOff; x < xOff + xSpan; x++) {
						newMap.get(0).get(z-zOff).get(y-yOff).add('.');
						newMap.get(newMap.size()-1).get(z-zOff).get(y-yOff).add('.');
					}
				}
			}
			// enlargen old w-planes
			for (w = wOff + 1; w < wOff + wSpan - 1; w++) {
				newMap.get(w-wOff).add(0, new ArrayList<ArrayList<Character>>());
				newMap.get(w-wOff).add(new ArrayList<ArrayList<Character>>());
				// fill new z-Planes
				for (y = yOff; y < yOff + ySpan; y++) {
					newMap.get(w-wOff).get(0).add(new ArrayList<Character>());
					newMap.get(w-wOff).get(newMap.get(w-wOff).size()-1).add(new ArrayList<Character>());
					for (int x = xOff; x < xOff + xSpan; x++) {
						newMap.get(w-wOff).get(0).get(y-yOff).add('.');
						newMap.get(w-wOff).get(newMap.get(w-wOff).size()-1).get(y-yOff).add('.');
					}
				}
				// enlarge old z-Planes
				for (z = zOff + 1; z < zOff + zSpan - 1; z++) {
					// new ys
					newMap.get(w-wOff).get(z-zOff).add(0, new ArrayList<Character>());
					newMap.get(w-wOff).get(z-zOff).add(new ArrayList<Character>());
					for (y = yOff; y < yOff + ySpan; y++) {
						for (int x = xOff; x < xOff + xSpan; x++) {
							if (y - yOff == 0 || y - yOff == ySpan - 1) newMap.get(w-wOff).get(z-zOff).get(y-yOff).add('.');
							else if (x - xOff == 0) newMap.get(w-wOff).get(z-zOff).get(y-yOff).add(0, '.');
							else if (x - xOff == xSpan -1) newMap.get(w-wOff).get(z-zOff).get(y-yOff).add('.');
						}
					}
				}
			}
			
			System.out.println("wSpan: " + wSpan + " nM.s " + newMap.size() + " zSpan " + zSpan + " nM.1.s " + newMap.get(1).size() + " ySpan " + ySpan + " nM.0.1.s " + newMap.get(0).get(1).size() + " xSpan " + xSpan + " nM.0.1.1.s " + newMap.get(0).get(1).get(1).size());
			System.out.println("wSpan: " + wSpan + " nM.s " + newMap.size() + " zSpan " + zSpan + " nM.0.s " + newMap.get(0).size() + " ySpan " + ySpan + " nM.1.1.s " + newMap.get(1).get(1).size() + " xSpan " + xSpan + " nM.1.1.1.s " + newMap.get(1).get(1).get(1).size());
			
			for (w = wOff; w < wOff + wSpan; w++) {
				for (z = zOff; z < zOff + zSpan; z++) {
					//if (z == zOff || z == zOff + zSpan - 1) newMap.add(z - zOff, new ArrayList<ArrayList<Character>>());
					for (y = yOff; y < yOff + ySpan; y++) {
						//if (z == zOff || z == zOff + zSpan - 1 ||
						//	y == yOff || y == yOff + ySpan - 1) newMap.get(z-zOff).add(y-yOff, new ArrayList<Character>());
						for (int x = xOff; x < xOff + xSpan; x++) {
							//if (z == zOff || z == zOff + zSpan - 1 ||
							//	y == yOff || y == yOff + ySpan - 1 ||
							//	x == xOff || x == xOff + xSpan - 1) newMap.get(z-zOff).get(y-yOff).add(z-zOff, '.');
							//System.out.println ("at new Tile");
							
							int neighbors = 0;
							for (int wLook = -1; wLook <= 1; wLook++) {
								if (w + wLook <= wOff || w + wLook >= wOff + wSpan -1) continue;
								for (int zLook = -1; zLook <= 1; zLook++) {
									if (z + zLook <= zOff || z + zLook >= zOff + zSpan -1) continue;
									for (int yLook = -1; yLook <= 1; yLook++) {
										if (y + yLook <= yOff || y + yLook >= yOff + ySpan -1) continue;
										for (int xLook = -1; xLook <= 1; xLook++) {
											if (x + xLook <= xOff || x + xLook >= xOff + xSpan -1) continue;
											else if (wLook == 0 && zLook == 0 && yLook == 0 && xLook == 0) continue;
											else {
												//System.out.println("zOff: " + zOff + " zSpan: " + zSpan + " z: " + z + " zLook: " + zLook);
												//System.out.println("yOff: " + yOff + " ySpan: " + ySpan + " y: " + y + " yLook: " + yLook);
												//System.out.println("xOff: " + xOff + " xSpan: " + xSpan + " x: " + x + " xLook: " + xLook);
												if (map.get(w+wLook-wOff-1).get(z+zLook-zOff-1).get(y+yLook-yOff-1).get(x+xLook-xOff-1) == '#') neighbors++;
											}									
										}
									}
								}
							}
							
							if (newMap.get(w-wOff).get(z-zOff).get(y-yOff).get(x-xOff) == '.') {
								if (neighbors == 3) {
									newMap.get(w-wOff).get(z-zOff).get(y-yOff).set(x-xOff, '#');
									total++;
								}
							} else {
								if (!(neighbors == 3 || neighbors == 2)) {
									newMap.get(w-wOff).get(z-zOff).get(y-yOff).set(x-xOff, '.');
								} else {
									total++;
								}
							}
							
						}
					}
				}
			}
			
			map = myclone2(newMap);
			
//			for (int wi = 0; wi < wSpan; wi++) {
//				for (int zi = 0; zi < zSpan; zi++) {
//					System.out.println("z=" + (zi + yOff) + ", w=" + (wi + wOff));
//					for (int yi = 0; yi < ySpan; yi++) {
//						for (int xi = 0; xi < xSpan; xi++) {
//							System.out.print(map.get(wi).get(zi).get(yi).get(xi));
//						}
//						System.out.println();
//					}
//				}
//			}
			
			System.out.println(total);
		}
	}

}
