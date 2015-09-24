package minimumEditDistanceCalculator;

import minimumEditDistanceCalculator.system.MinEditDistCalc;

public class Driver {

	public static void main(String args[]) {
		MinEditDistCalc calc = new MinEditDistCalc();

		int dist;
		// Should be 8
		// dist = calc.calculateMinEditDist("INTENTION", "EXECUTION");

		// Should be 8
		// dist = calc.calculateMinEditDist("INDUSTRY", "INTEREST");

		// Should be 26
		dist = calc.calculateMinEditDist("PEOPLE SOILED OUR GREEN", "SOYLENT GREEN IS PEOPLE");

		System.out.println();
		System.out.println(dist);
	}
}