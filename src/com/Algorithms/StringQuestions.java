package com.Algorithms;

import java.util.HashMap;
import java.util.PriorityQueue;

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

    boolean isWildcardMatching(String s, String p) {
        int n = s.length(), m = p.length();

        //the first row and column will be dedicated to empty string and pattern respectively.
        boolean[][] dp = new boolean[n + 1][m + 1];

        //both the pattern and string are empty
        dp[0][0] = true;

        //string is empty. the pattern is * so we look at the prev column value.
        for (int j = 1; j <= m; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[n][m];
    }

    boolean isRegexMatching(String s, String p) {
        int n = s.length(), m = p.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        //both the pattern and string are empty
        dp[0][0] = true;

        //string is empty. the pattern is * so we look at the prev column value.
        for (int j = 1; j <= m; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (p.charAt(j - 1) == '.' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }
        return dp[n][m];
    }

    boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> hm = new HashMap<>();
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        for (int i = 0; i < sChar.length; i++) {
            if (hm.containsKey(sChar[i])) {
                if (hm.get(sChar[i]) != tChar[i])
                    return false;
            } else {
                if (hm.containsValue(tChar[i]))
                    return false;
                hm.put(sChar[i], tChar[i]);
            }
        }
        return true;
    }

    /**
     * Given a string S, rearrange it such that the characters adjacent to each other are not the same.
     *
     * @param S
     * @return
     */
    String reorganizeString(String S) {
        StringBuilder sb = new StringBuilder();
        HashMap<Character, Integer> map = new HashMap<>();
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        char[] sChar = S.toCharArray();
        for (char c : sChar) {
            int count = map.getOrDefault(c, 0) + 1;
            map.put(c, count);
            if (count > (S.length() + 1) / 2)
                return "";
        }

        maxHeap.addAll(map.keySet());

        char prev = '#';
        while (!maxHeap.isEmpty()) {
            char mostOccurChar = maxHeap.poll();
            sb.append(mostOccurChar);
            map.put(mostOccurChar, map.getOrDefault(mostOccurChar, 1) - 1);

            if (map.containsKey(prev) && map.get(prev) >= 1)
                maxHeap.add(prev);
            prev = mostOccurChar;
        }

        return sb.toString();
    }

    /**
     * Given two strings S and T, return if they are equal when both are typed into empty text editors.
     * # means a backspace character.
     * S = "ab#c", T = "ad#c" : true
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        return getActualString(S).equals(getActualString(T));
    }

    private String getActualString(String str) {
        StringBuilder strBuild = new StringBuilder();
        int hashCount = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '#') {
                hashCount += 1;
            } else {
                if (hashCount == 0)
                    strBuild.append(str.charAt(i));
                else
                    hashCount -= 1;
            }
        }
        return strBuild.toString();
    }

    /**
     * Longest substring with at Least k repeating Characters
     * Input: s = "ababbc", k = 2
     * Output: 5
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        return longest(s.toCharArray(), 0, s.length() - 1, k);
    }

    private int longest(char[] sChar, int start, int end, int k) {
        int[] count = new int[26];
        for (int i = start; i <= end; i++) {
            char c = sChar[i];
            count[c - 'a'] += 1;
        }

        for (int i = start; i <= end; i++) {
            char c = sChar[i];
            if (count[c - 'a'] > 0 && count[c - 'a'] < k) {  //Splitting criteria
                return Math.max(longest(sChar, start, i - 1, k),
                        longest(sChar, i + 1, end, k));
            }
        }
        return end - start + 1;
    }

    /**
     * Given two strings s and t, find the minimum window of t in s.
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     *
     * @param s
     * @param t
     * @return
     */
    public String minimumWindowSubstring(String s, String t) {
        if (s == null || t == null || s.length() < t.length())
            return "";

        int start = 0, end = 0, minWindStart = 0, minWindLen = Integer.MAX_VALUE, tCount = t.length();

        HashMap<Character, Integer> tMap = new HashMap<>();
        for (char tc : t.toCharArray()) {
            tMap.put(tc, tMap.getOrDefault(tc, 0) + 1);
        }

        while (end < s.length()) {
            char sCharEnd = s.charAt(end);
            if (tMap.containsKey(sCharEnd) && tMap.get(sCharEnd) > 0)
                tCount -= 1;
            tMap.put(sCharEnd, tMap.getOrDefault(sCharEnd, 0) - 1);
            end += 1;  //Move the end pointer until you find all the characters of t.
            while (tCount == 0) {  //Found all the characters of t, now move the start pointer until we find the required shortest size.
                char sCharStart = s.charAt(start);
                tMap.put(sCharStart, tMap.getOrDefault(sCharStart, 0) + 1);
                if (tMap.containsKey(sCharStart) && tMap.get(sCharStart) > 0)  //At this point, the value of character at sCharStart will be negative if it doesn't appear in t.
                    tCount += 1;
                if (minWindLen > end - start) {  //Update the minWindLen and the starting position of the substring.
                    minWindLen = end - start;
                    minWindStart = start;
                }
                start += 1;
            }
        }
        return minWindLen == Integer.MAX_VALUE ? "" : s.substring(minWindStart, minWindStart + minWindLen);
    }
}
