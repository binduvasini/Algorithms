package com.Algorithms;

public class StringQuestions {

    public String longestPalindromeSubstring(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        String longestPalSubstring = "";
        int longestPalLength = 0;

        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            longestPalLength = 1;
            longestPalSubstring = s.substring(i, i + 1);
        }

        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                longestPalLength = 2;
                longestPalSubstring = s.substring(i, i + 2);
            }
        }

        int j;
        for (int k = 2; k < n; k++) {
            for (int i = 0; i < n; i++) {
                j = i + k;
                if (j < n && s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;

                    if (j - i + 1 > longestPalLength) {
                        longestPalLength = j - i + 1;
                        longestPalSubstring = s.substring(i, j + 1);
                    }
                }
            }
        }
        return longestPalSubstring;
    }
}
