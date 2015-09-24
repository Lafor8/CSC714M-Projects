package minimumEditDistanceCalculator.system;

public class MinEditDistCalc {
	
	public int calculateMinEditDist(String str1, String str2) {
		int M, N;
		M = str1.length() + 1;
		N = str2.length() + 1;
		int arr[][] = new int[M][N];

		// INITIALIZATION
		for (int i = 0; i < M; ++i)
			arr[i][0] = i;

		for (int j = 0; j < N; ++j)
			arr[0][j] = j;

		// FILLING IN THE TABLE
		for (int i = 1, left, up, diag; i < M; ++i)
			for (int j = 1; j < N; ++j) {
				left = arr[i][j - 1] + this.getCostLeft();
				up = arr[i - 1][j] + this.getCostTop();
				diag = arr[i - 1][j - 1];
				if (str1.charAt(i - 1) != str2.charAt(j - 1))
					diag += this.getCostDiagonal();
				arr[i][j] = Math.min(Math.min(left, up), diag);
			}

		// PRINTING OUT THE TABLE
		System.out.print("\t\t");
		for (int j = 1; j < N; ++j)
			System.out.print(str2.charAt(j - 1) + "\t");
		System.out.println();

		for (int i = 0; i < M; ++i) {
			if (i != 0)
				System.out.print(str1.charAt(i - 1) + "\t");
			else
				System.out.print("\t");
			for (int j = 0; j < N; ++j)
				System.out.print(arr[i][j] + "\t");
			System.out.println();
		}

		System.out.println();
		System.out.println(arr[str1.length()][str2.length()]);

		// BACKTRACE

		StringBuilder backtrace = new StringBuilder();

		{
			int i, j, left, up, diag, min;
			i = str1.length();
			j = str2.length();
			while (i != 0 || j != 0) {
				if (i == 0) {
					backtrace.append("A");
					--j;
				} else if (j == 0) {
					backtrace.append("D");
					--i;
				} else {
					left = arr[i][j - 1];
					up = arr[i - 1][j];
					diag = arr[i - 1][j - 1];
					min = Math.min(Math.min(left, up), diag);

					if (up == min) {
						backtrace.append("D");
						--i;
					} else if (left == min) {
						backtrace.append("A");
						--j;

					} else if (diag == min) {
						if (arr[i][j] == diag) {
							backtrace.append("|");
						} else {

							backtrace.append("S");
						}
						--i;
						--j;
					}
				}
			}
		}

		// PRINT TRACE

		System.out.println();
		String trace = backtrace.reverse().toString();
		for (int i = 0, j = 0; i < trace.length(); ++i)
			if (trace.charAt(i) != 'A')
				System.out.print(str1.charAt(j++) + "\t");
			else
				System.out.print("*\t");

		System.out.println();
		System.out.println();
		for (int i = 0; i < trace.length(); ++i)
			System.out.print(trace.charAt(i) + "\t");

		System.out.println();
		System.out.println();
		for (int i = 0, j = 0; i < trace.length(); ++i)
			if (trace.charAt(i) != 'D')
				System.out.print(str2.charAt(j++) + "\t");
			else
				System.out.print("*\t");

		System.out.println();

		return arr[str1.length()][str2.length()];
	}

	protected int getCostLeft() {
		return 1;
	}

	protected int getCostTop() {
		return 1;
	}

	protected int getCostDiagonal() {
		return 2;
	}
}
