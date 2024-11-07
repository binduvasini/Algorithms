package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StringQuestions {

    /**
     * Given a string s, find the length of the longest substring without repeating characters.
     * Input: “abbcdb”. Output: 3. “bcd” is the longest substring without repeating characters.
     *
     * @param s
     * @return
     */
    public int longestSubstringLengthWithoutRepeatingChars(String s) {
        int start = 0, end = 0, longest = 0;
        Set<Character> set = new HashSet<>();
        while (end < s.length()) {
            if (!set.contains(s.charAt(end))) {
                set.add(s.charAt(end));  //If we add to the set without checking, it will replace the character
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
     * Given two strings s and t, determine if they are isomorphic.
     * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
     * All occurrences of a character must be replaced with another character while preserving the order of characters.
     * No two characters may map to the same character, but a character may map to itself.
     * Input: s = "foo", t = "bar"
     * Output: false
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        for (int i = 0; i < sChar.length; i++) {
            if (map.containsKey(sChar[i])) {  //If the HashMap contains a character in s as key,
                // the value should be the character in t.
                if (map.get(sChar[i]) != tChar[i])
                    return false;
            } else {  //If the HashMap contains a character in t as value already,
                // the key is not the character in s, so it is false.
                if (map.containsValue(tChar[i]))
                    return false;
                map.put(sChar[i], tChar[i]);
            }
        }
        return true;
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
                    // Increase the count in the map.
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
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
     * typically using all the original letters exactly once.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();

        for (char c : s.toCharArray())
            sMap.put(c, sMap.getOrDefault(c, 0) + 1);

        for (char c : t.toCharArray())
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);

        return sMap.equals(tMap);
    }

    /**
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] strChars = str.toCharArray();
            Arrays.sort(strChars);
            String sortedString = String.valueOf(strChars);

            map.putIfAbsent(sortedString, new ArrayList<>());
            map.get(sortedString).add(str);
        }

        return new ArrayList<>(map.values());
    }

    /**
     * Determine if the string is a palindrome
     *
     * @param str
     * @return
     */
    public boolean isPalindrome(String str) {
        str = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        if (str.trim().equals(""))
            return true;

        return isPalindrome(str, 0, str.length() - 1);
    }

    private boolean isPalindrome(String str, int start, int end) {
        if (start >= end)
            return true;
        if (str.charAt(start) != str.charAt(end))
            return false;
        return isPalindrome(str, start + 1, end - 1);
    }

    /**
     * Given two strings a and b of the same length, choose an index and split both strings at the same index,
     * splitting a into two strings: a = aprefix + asuffix, and splitting b into two strings: b = bprefix + bsuffix.
     * Check if aprefix + bsuffix or bprefix + asuffix forms a palindrome.
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkPalindromeFormation(String s1, String s2) {
        return check(s1, s2) || check(s2, s1);
    }

    private boolean check(String s1, String s2) {
        int start = 0, end = s2.length() - 1;

        while (start < end && s1.charAt(start) == s2.charAt(end)) {
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

//    private boolean isPalindrome(String s) {
//        StringBuilder sb = new StringBuilder(s);
//        return (sb.reverse().toString().equals(s));
//    }

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
    int count = 0;
    public int findPalindromicSubstrings(String s) {
        for (int i = 0; i < s.length(); i++) {
            palindromes(s, i, i);   //Odd length palindromic substring.
            // Imagine having both pointers pointing at the same character.
            // Expanding one character at a time on each direction will be a string with odd length.
            palindromes(s, i, i + 1);   //Even length palindromic substring
        }

        return count;
    }

    private void palindromes(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            System.out.println(s.substring(left, right + 1));  //In case we need the substring
            count += 1;  //palindromic substring is found
            left -= 1;   //Expand to the left
            right +=1;   //Expand to the right
        }
    }

    /**
     * Find the longest palindromic substring in s.
     *
     * @param s
     * @return
     */
    int longestLen = Integer.MIN_VALUE;  //We need a variable to maintain the longest length at a given time.
    int start = 0;  //We will maintain another variable to hold the beginning position of the palindrome so that
    // we will update this start pointer when we update the longest length.
    // Because this position is going to be the starting point of the longest palindrome.
    public String longestPalindromicSubstring(String s) {
        for (int i = 0; i < s.length(); i++) {
            palindromeLength(s, i, i);
            palindromeLength(s, i, i + 1);
        }
        return s.substring(start, start + longestLen);
    }

    private void palindromeLength(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left -= 1;
            right += 1;
        }
        if (right - left - 1 > longestLen) {  // Update the longest length when a palindrome is found.
            // This condition must be outside the while loop.
            longestLen = right - left - 1;  //The reason for doing - 1 is that when the while loop exits,
            // the pointers would have already crossed the boundary.
            start = left + 1;  //Same reason as above.
        }
    }

    /**
     * Given a string s which consists of lowercase or uppercase letters,
     * return the length of the longest palindrome that can be built with those letters.
     * Not a substring. Something like a subsequence.
     * Letters are case-sensitive, for example, "Aa" is not considered a palindrome.
     *
     * @param s
     * @return
     */
    public int longestPalindromeFromASetOfCharacters(String s) {
        int len = 0;
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) {
                len += 2;
                set.remove(c);
            }
            else {
                set.add(c);
            }
        }
        if (!set.isEmpty()) {
            len += 1;
        }
        return len;
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
        Set<Integer> uniqueCharSet = new HashSet<>();

        while (end < s.length()) {
            int endChar = s.charAt(end) - 'A';
            charFreq[endChar] += 1;
            uniqueCharSet.add(endChar);

            if (uniqueCharSet.size() > k) {
                int startChar = s.charAt(start) - 'A';
                int charToRemove = startChar;
                while (startChar == charToRemove && charFreq[startChar] > 0) {
                    //While loop because we need to decrement the char count that we are getting rid of.
                    startChar = s.charAt(start) - 'A';
                    charFreq[startChar] -= 1;
                    start += 1;
                }
                uniqueCharSet.remove(charToRemove);
            }
            end += 1;
            longestLength = Math.max(longestLength, end - start);
        }

        return longestLength;
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
     * Longest substring with at Least k repeating Characters
     * Input: s = "ababbc", k = 2
     * Output: 5
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstringAtLeastKRepeatingChars(String s, int k) {
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
     * Given two strings S and T, return if they are equal when both are typed into empty text editors.
     * # means a backspace character.
     * S = "ab#c", T = "ad#c"
     * Output: true
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        return getActualString(S).equals(getActualString(T));
    }

    private String getActualString(String str) {
        StringBuilder builder = new StringBuilder();
        int hashCount = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '#') {
                hashCount += 1;
            } else {
                if (hashCount == 0)
                    builder.append(str.charAt(i));
                else
                    hashCount -= 1;
            }
        }
        return builder.toString();
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
    public String removeKdigits(String num, int k) {  //There is a stack solution for this question.
        StringBuilder builder = new StringBuilder(num);

        while (k > 0) {
            int i = 1;
            while (builder.charAt(i - 1) <= builder.charAt(i) && i < builder.length()) {
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
     * Given two strings s and t, write a function to check if s contains the permutation of t.
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

    public static String findMostCommonSubstring(String s, int k) {
        if (s == null || s.length() < k) {
            return "";
        }

        Map<String, Integer> substringMap = new HashMap<>();

        for (int i = 0; i <= s.length() - k; i++) {
            String sub = s.substring(i, i + k);
            substringMap.put(sub, substringMap.getOrDefault(sub, 0) + 1);
        }

        String mostCommonSubstring = "";
        int maxCount = 0;

        for (String key : substringMap.keySet()) {
            if (substringMap.get(key) > maxCount) {
                mostCommonSubstring = key;
            }
        }

        return mostCommonSubstring;
    }

    /**
     * Find the longest common prefix string amongst an array of strings.
     * Input: strs = ["flower","flow","flight"]
     * Output: "fl"
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        Arrays.sort(strs);
        String first = strs[0];
        String last = strs[strs.length - 1];

        int i = 0;

        while (i < first.length()) {
            if (first.charAt(i) == last.charAt(i)) {
                i += 1;
            }
            else {
                break;
            }
        }

        return first.substring(0, i);
    }

    /**
     * Given a string s and an array of integers cost where cost[i] is the cost of deleting the character i in s.
     * Return the minimum cost of deletions such that there are no two identical letters next to each other.
     * s = "aaaaabaa", cost = [11,5,3,4,6,9,1,15]
     * Output: 19
     *
     * @param s
     * @param cost
     * @return
     */
    public int minDeletionCost(String s, int[] cost) {
        int result = 0;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                result += Math.min(cost[i], cost[i - 1]);
                cost[i] = Math.max(cost[i], cost[i - 1]);
            }
        }

        return result;
    }

    /**
     * Encode a list of strings to a string.
     * The encoded string is then sent over the network and is decoded back to the original list of strings.
     * Implement encode and decode
     * Input: [“algo”,“to”,“do”,“this”] - Encode it.
     * Output: [“algo”,“to”,“do”,“this”] - Decoded.
     *
     * @param words
     * @return
     */
    public String encode(List<String> words) {
        StringBuilder encoded = new StringBuilder();
        for (String word: words) {
            // For each string, calculate its length and create an encoding in the format length#string
            encoded.append(word.length()).append('#').append(word);
        }
        return encoded.toString();
    }

    public List<String> decode(String str) {
        List<String> decoded = new ArrayList<>();
        int i = 0;

        while (i < str.length()) {  // Iterate over the string.
            // There is an integer followed by the delimiter.
            // Find the position of the next '#' character.
            int delimiter = str.indexOf('#', i);  //the first delimiter after index i
            int wordLength = Integer.parseInt(str.substring(i, delimiter));

            i = delimiter + wordLength + 1;  //move the index to the position of the next word

            decoded.add(str.substring(delimiter + 1, i));  //add the word to the output
        }

        return decoded;
    }

    public String countAndSay(String num) {
        int count;
        StringBuilder result = new StringBuilder();

        int i = 0;

        while (i < num.length()) {
            count = 1; //To store how many times a digit occurred

            //Inner while loop compares current digit and the next digit
            while (i + 1 < num.length() && num.charAt(i) == num.charAt(i + 1)) {
                i += 1;
                count += 1;
            }

            result.append(count).append(num.charAt(i));
            i += 1;
        }
        return result.toString();
    }
}
