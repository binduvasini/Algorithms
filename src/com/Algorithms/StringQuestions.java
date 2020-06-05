package com.Algorithms;

import java.util.*;

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
                return "";  //If the given string contains a character that occurs more than half of its length, we cannot rearrange it.
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
     * Given a string, sort it in decreasing order based on the frequency of characters.
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        StringBuilder builder = new StringBuilder();
        HashMap<Character, Integer> map = new HashMap<>();
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        char[] sChar = s.toCharArray();
        for (char c : sChar) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        maxHeap.addAll(map.keySet());
        while (!maxHeap.isEmpty()) {
            char mostOccurChar = maxHeap.poll();
            while(map.get(mostOccurChar) > 0){
                builder.append(mostOccurChar);
                map.put(mostOccurChar, map.getOrDefault(mostOccurChar, 1) - 1);
            }
        }
        return builder.toString();
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

    /**
     * Given a string s and a non-empty string t, find all the start indices of t's anagrams in s.
     * s: "abab" t: "ab"
     * Output: [0, 1, 2]
     *
     * @param s
     * @param t
     * @return
     */
    public List<Integer> findAnagrams(String s, String t) {
        int start = 0, end = 0, tCount = t.length();
        List<Integer> anagramsList = new LinkedList<>();

        HashMap<Character, Integer> tMap = new HashMap<>();
        for (char tc : t.toCharArray()) {
            tMap.put(tc, tMap.getOrDefault(tc, 0) + 1);
        }

        while (end < s.length()) {
            char sCharEnd = s.charAt(end);
            if (tMap.containsKey(sCharEnd) && tMap.get(sCharEnd) > 0) {
                tCount -= 1;
            }
            tMap.put(sCharEnd, tMap.getOrDefault(sCharEnd, 0) - 1);
            end += 1;

            while (tCount == 0) {
                char sCharStart = s.charAt(start);
                tMap.put(sCharStart, tMap.getOrDefault(sCharStart, 0) + 1);
                if (tMap.containsKey(sCharStart) && tMap.get(sCharStart) > 0)
                    tCount += 1;

                if (t.length() == end - start) {
                    anagramsList.add(start);
                }
                start += 1;
            }
        }
        return anagramsList;
    }

    /**
     * Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.
     * Input: num = "1432219", k = 3
     * Output: "1219"
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        StringBuilder builder = new StringBuilder(num);

        while (k > 0) {
            int i = 0;
            while (i < builder.length() - 1 && builder.charAt(i) <= builder.charAt(i + 1)) {
                i++;
            }
            builder.deleteCharAt(i);
            k -= 1;
        }

        for (int i = 0; i < builder.length(); ) {
            if (builder.charAt(i) == '0')
                builder.deleteCharAt(i);
            else
                break;
        }
        return builder.toString().isBlank() ? "0" : builder.toString();
    }

    /**
     * Given two strings t and s, write a function to check if s contains the permutation of t.
     * Input: t = "ab" s = "eidboaoo"
     * Output: False
     *
     * @param t
     * @param s
     * @return
     */
    public boolean checkPermutationOfAString(String t, String s) {
        int[] tChars = new int[26];
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tChars[c - 'a'] += 1;
        }

        int[] sChars = new int[26];
        int start = 0, end = t.length();
        while (end <= s.length()) {
            for (int i = start; i < end; i++) {
                char c = s.charAt(i);
                sChars[c - 'a'] += 1;
            }
            if (Arrays.equals(tChars, sChars))
                return true;
            sChars = new int[26];
            start += 1;
            end += 1;
        }
        return false;
    }

    /**
     * We write the integers of A and B (in the order they are given) on two separate horizontal lines.
     * Now, we may draw connecting lines: a straight line connecting two numbers A[i] and B[j] such that:
     * A[i] == B[j];
     * The line we draw does not intersect any other connecting (non-horizontal) line.
     * Note that a connecting lines cannot intersect even at the endpoints: each number can only belong to one connecting line.
     * Return the maximum number of connecting lines we can draw in this way.
     * A = [2,5,1,2,5]
     * B = [10,5,2,1,5,2]
     * Output: 3
     * @param A
     * @param B
     * @return
     */
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length + 1][B.length + 1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1])
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[A.length][B.length];
    }

    /**
     * Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
     *
     * You have the following 3 operations permitted on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * Explanation:
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for(int i=0; i <= word1.length(); i++){
            dp[i][0] = i;
        }

        for(int j=0; j <= word2.length(); j++){
            dp[0][j] = j;
        }

        for(int i=1; i <= word1.length(); i++){
            for(int j=1; j <= word2.length(); j++){
                if(word1.charAt(i-1) == word2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1];
                else
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks. Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
     *
     * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
     *
     * You need to return the least number of intervals the CPU will take to finish all the given tasks.
     *
     *
     *
     * Example:
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        HashMap<Character, Integer> map = new HashMap<>();
        PriorityQueue<Character> maxHeap = new PriorityQueue<>((o1, o2) -> map.get(o2) - map.get(o1));

        for (char task : tasks) {
            map.put(task, map.getOrDefault(task, 0) + 1);
        }

        maxHeap.addAll(map.keySet());
        int count = 0;
        while (!maxHeap.isEmpty()) {
            List<Character> list = new ArrayList<>();  //Store the removed characters and add it back after the completion of the task interval.
            for(int i=0; i<=n; i++){
                if(!maxHeap.isEmpty()){
                    char mostOccurChar = maxHeap.poll();
                    map.put(mostOccurChar, map.getOrDefault(mostOccurChar, 1) - 1);
                    if(map.get(mostOccurChar) >= 1)
                        list.add(mostOccurChar);  //Add to the list only when this character's occurrence is at least 1.
                }
                count += 1;
                if(maxHeap.isEmpty() && list.isEmpty())
                    break;
            }
            maxHeap.addAll(list);  //Add the removed characters back. We checked for the occurrences already inside the for loop.
        }
        return count;
    }
}
