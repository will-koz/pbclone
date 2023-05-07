package pb;

import java.util.Arrays;

public class Distance {

// Algorithm for finding the Levenshtein distance of two strings
public static int calculate (String x, String y) {
	int[][] arr = new int[x.length() + 1][y.length() + 1];

	for (int i = 0; i <= x.length(); i++) {
		for (int j = 0; j <= y.length(); j++) {
			if (i == 0) arr[i][j] = j;
			else if (j == 0) arr[i][j] = i;
			else
				arr[i][j] = min(arr[i - 1][j - 1] + cost_of_substitution(x.charAt(i - 1),
					y.charAt(j - 1)), arr[i - 1][j] + 1, arr[i][j - 1] + 1);
		}
	}

	return arr[x.length()][y.length()];
}

private static int cost_of_substitution (char a, char b) { return a == b ? 0 : 1; }

public static boolean grade (String x, String y) {
	// Calculate the distance between two Strings
	int dist = calculate(x.trim(), y.trim());

	// if it is less than one-third of the length of the second one (the correct one)
	//    return true
	if (dist <= y.length() / 3) return true;
	else return false;
}

// Given several numbers, return the minimum
private static int min (int... numbers) {
	return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
}

}
