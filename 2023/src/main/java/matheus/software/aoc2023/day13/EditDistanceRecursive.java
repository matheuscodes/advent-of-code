package matheus.software.aoc2023.day13;

import java.util.Arrays;

// Too lazy to implement own Levenshtein Distance.
// https://www.baeldung.com
// /java-levenshtein-distance#dynamic-programming-approach
public final class EditDistanceRecursive {

    private EditDistanceRecursive() {
        throw new UnsupportedOperationException("Satisfying checkstyle.");
    }

    static int calculate(final String x, final String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(x.charAt(i - 1),
                                    y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        // Altered to absolute distance.
        return Math.abs(dp[x.length()][y.length()]);
    }

    public static int costOfSubstitution(final char a, final char b) {
        return a == b ? 0 : 1;
    }

    public static int min(final int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
