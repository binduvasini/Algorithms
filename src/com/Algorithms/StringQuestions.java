package com.Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
     * Given a string s, find the length of the longest substring without repeating characters.
     * Input: “abbcdb”. Output: 3. “bcd” is the longest substring without repeating characters.
     *
     * @param s
     * @return
     */
    public int longestSubstringWithoutRepeatingChars(String s) {
        int start = 0, end = 0, longest = 0;
        Set<Character> set = new HashSet<>();
        while (end < s.length()) {
            if (!set.contains(s.charAt(end))) {
                set.add(s.charAt(end));  //If we add to the set without checking, it will replace the character \
                // as set contains only unique characters.
                end += 1;
            }
            else {
                set.remove(s.charAt(start));
                start += 1;
            }
            longest = Math.max(longest, (end - start));
        }
        return longest;
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

        int start = 0, end = 0;
        int minWindStart = 0, minWindLen = Integer.MAX_VALUE;
        int tCount = 0;  //window size

        Map<Character, Integer> tMap = new HashMap<>();
        for (char tChar : t.toCharArray()) {  //Count the characters of t and store it in the map
            tMap.put(tChar, tMap.getOrDefault(tChar, 0) + 1);
        }

        while (end < s.length()) {
            char endChar = s.charAt(end);
            if (tMap.containsKey(endChar)) {
                if (tMap.get(endChar) > 0) {  //Is this character present in t? Increment the window counter.
                    tCount += 1;
                }
                tMap.put(endChar, tMap.get(endChar) - 1);  //We finished visiting this character.
                // Reduce the count in the map.
            }
            end += 1;  //Move the end pointer until you find all the characters of t.

            while (tCount == t.length()) {  //We formed the window.
                char startChar = s.charAt(start);
                if (tMap.containsKey(startChar)) {
                    tMap.put(startChar, tMap.get(startChar) + 1);  //Now we are visiting this character.
                    // Increase the count.
                    if (tMap.get(startChar) > 0) {
                        tCount -= 1;  //make this substring window invalid
                    }
                }

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
     * Given two strings s and t, find all the start indices of t's anagrams in s.
     * s: "abab" t: "ab"
     * Output: [0, 1, 2]
     *
     * @param s
     * @param t
     * @return
     */
    public List<Integer> findAnagrams(String s, String t) {
        int start = 0, end = 0, tCount = 0;
        List<Integer> anagramsList = new LinkedList<>();

        Map<Character, Integer> tMap = new HashMap<>();
        for (char tc : t.toCharArray()) {
            tMap.put(tc, tMap.getOrDefault(tc, 0) + 1);
        }

        while (end < s.length()) {
            char endChar = s.charAt(end);
            if (tMap.containsKey(endChar)) {
                if (tMap.get(endChar) > 0) {  //Is this character present in t? Increment the window counter.
                    tCount += 1;
                }
                tMap.put(endChar, tMap.get(endChar) - 1);  //We finished visiting this character.
                // Reduce the count in the map.
            }
            end += 1;

            while (tCount == t.length()) {
                char startChar = s.charAt(start);
                if (tMap.containsKey(startChar)) {
                    tMap.put(startChar, tMap.get(startChar) + 1);  //Now we are visiting this character.
                    // Increase the count.
                    if (tMap.get(startChar) > 0) {
                        tCount -= 1;  //make this substring window invalid
                    }
                }

                if (end - start == t.length()) {
                    anagramsList.add(start);
                }
                start += 1;
            }
        }
        return anagramsList;
    }

    /**
     * Given a non-negative integer num represented as a string,
     * remove k digits from the number so that the new number is the smallest possible.
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
    public boolean checkIfStringSContainsPermutationOfT(String s, String t) {
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
     * Two lists A and B are written on two separate horizontal lines.
     * If we draw a connecting line from A to B, the two numbers must be equal A[i] == B[j].
     * The connecting lines must not intersect.
     * Return the maximum number of connecting lines we can draw this way.
     * A = [2,5,1,2,5]
     * B = [10,5,2,1,5,2]
     * Output: 3
     *
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
     * You have the following 3 operations permitted on a word:
     * 1) Insert a character
     * 2) Delete a character
     * 3) Replace a character
     * Input: word1 = "intention", word2 = "execution"
     * Output: 5
     * intention -> inention (remove 't')
     * inention -> enention (replace 'i' with 'e')
     * enention -> exention (replace 'n' with 'x')
     * exention -> exection (replace 'n' with 'c')
     * exection -> execution (insert 'u')
     *
     * @param word1
     * @param word2
     * @return
     */
    public int editMinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {  //Initialize the first column
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {  //Initialize the first row
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * Given strings s and t, check if t is a subsequence of s.
     * A subsequence of a string is a new string
     * which is formed from the original string by deleting some (can be none) of the characters
     * without disturbing the relative positions of the remaining characters.
     * (ie, "ace" is a subsequence of "abcde" while "aec" is not).
     * s = "ahbgdc"
     * t = "acb"
     * return false.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (t.isEmpty())
            return true;
        else if (s.isEmpty())
            return false;
        int sPointer = 0, tPointer = 0, count = t.length();
        while (sPointer < s.length()) {
            if (s.charAt(sPointer) == t.charAt(tPointer)) {
                count -= 1;
                tPointer += 1;
            }
            if (count == 0)
                return true;
            sPointer += 1;
        }
        return false;
    }

    /**
     * Given a string s that consists of only uppercase English letters,
     * you can perform at most k operations on that string.

     * In one operation, you can choose any character of the string and
     * change it to any other uppercase English character.

     * Find the length of the longest sub-string containing all repeating letters you can get
     * after performing the above operations.
     * Input:
     * s = "AABABBA", k = 1
     * Output:
     * 4
     *
     * @param s
     * @param k
     * @return
     */
    public int LongestSubstringLengthReplacingAtMostKChars(String s, int k) {
        int start = 0, end = 0;
        int[] charFreq = new int[26];
        int maxFreq = 0, longestLength = 0;

        while (end < s.length()) {
            int endChar = s.charAt(end) - 'A';
            charFreq[endChar] += 1;
            maxFreq = Math.max(maxFreq, charFreq[endChar]);
            end += 1;

            int windowSize = end - start - maxFreq;
            if (windowSize > k) {
                int startChar = s.charAt(start) - 'A';
                charFreq[startChar] -= 1;
                start += 1;
            }

            longestLength = Math.max(longestLength, end - start);
        }
        return longestLength;
    }

    /**
     * Longest substring with at most k distinct characters.
     * s = "AABBCC", k = 2.
     * Output: 4. (Either "AABB" or "BBCC")
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstringWithAtMostKDistinctChars(String s, int k) {
        int start = 0, end = 0;
        int[] charFreq = new int[26];
        int longestLength = 0;
        HashSet<Integer> uniqueCharSet = new HashSet<>();
        while (end < s.length()) {
            int c = s.charAt(end) - 'A';
            charFreq[c] += 1;
            uniqueCharSet.add(c);
            if (uniqueCharSet.size() > k) {
                int cStart = s.charAt(start) - 'A';
                int charToRemove = cStart;
                while (cStart == charToRemove && charFreq[cStart] > 0) {
                    //While loop because we need to decrement the char count that we are getting rid of.
                    cStart = s.charAt(start) - 'A';
                    charFreq[cStart] -= 1;
                    start += 1;
                }
                uniqueCharSet.remove(charToRemove);
            }
            longestLength = Math.max(longestLength, end - start + 1);
            end += 1;
        }
        return longestLength;
    }

    /**
     * Given two strings a and b of the same length, choose an index and split both strings at the same index,
     * splitting a into two strings: a = aprefix + asuffix, and splitting b into two strings: b = bprefix + bsuffix.
     * Check if aprefix + bsuffix or bprefix + asuffix forms a palindrome.
     *
     * @param a
     * @param b
     * @return
     */
    public boolean checkPalindromeFormation(String a, String b) {
        return check(a, b) || check(b, a);
    }

    private boolean check(String s1, String s2) {
        int start = 0, end = s2.length() - 1;

        while (start < end && s1.charAt(start) == s2.charAt(end)) {
            System.out.println("over here ");
            start += 1;
            end -= 1;
        }

        //This is what I initially tried. But this is not required.
        // Checking the middle part of either of the strings would do.

//        String s1s2 = s1.substring(0, start) + s2.substring(start);
//        String s2s1 = s2.substring(0, start) + s1.substring(start);
//        return isPalindrome(s1s2) || isPalindrome(s2s1);

        String s1Middle = s1.substring(start, end + 1);
        String s2Middle = s2.substring(start, end + 1);
        return isPalindrome(s1Middle) || isPalindrome(s2Middle);
    }

    private boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder(s);
        return (sb.reverse().toString().equals(s));
    }

}

/**
 * TimeMap timeMap = new TimeMap();
 * timeMap.put("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
 * timeMap.get("foo", 1);         // return "bar"
 * timeMap.get("foo", 3);         // return "bar",
 *                                since there is no value corresponding to foo at timestamp 3 and timestamp 2,
 *                                then the only value is at timestamp 1 is "bar".
 * timeMap.put("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
 * timeMap.get("foo", 4);         // return "bar2"
 * timeMap.get("foo", 5);         // return "bar2"
 */
class TimeMap {
    Map<String, TreeMap<Integer, String>> map;
    //The question asks to find a previous timestamp for this value.
    // TreeMap has a function that helps achieve this.

    public TimeMap() {
        map = new HashMap<>();
    }

    public void put(String key, String value, int timestamp) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> treeMap;
        if (map.containsKey(key)) {
            treeMap = map.get(key);

            if (treeMap.floorKey(timestamp) != null) {  //Essence of the solution.
                int tmKey = treeMap.floorKey(timestamp);
                return treeMap.get(tmKey);
            }
        }

        return "";
    }

    int count = 0;

    /**
     * Given a string s, return the number of palindromic substrings in it.
     * A string is a palindrome when it reads the same backward as forward.
     * A substring is a contiguous sequence of characters within the string.
     * Input: "racecar"
     * Output: 9  [r, a, c, e, c, a, cec, aceca, racecar]
     *
     * @param s
     * @return
     */
    public int findAllPalindromeSubstrings(String s) {
        for(int i = 0; i < s.length() - 1; i++) {
            findPalindromesInSubString(s, i, i);     //Odd length palindromic sub-string.
            // Imagine having both pointers pointing at the same character.
            // Expanding one character at a time on each direction will be a string with odd length.
            findPalindromesInSubString(s, i, i + 1);   //Even length palindromic sub-string
        }

        return count;
    }

    private void findPalindromesInSubString(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            System.out.println(s.substring(left, right + 1));
            count += 1;    //palindromic substring is found
            left -= 1;    //Expand to the left
            right +=1;    //Expand to the right
        }
    }
}
