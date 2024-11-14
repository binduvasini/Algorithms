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
    public int longestSubstringLengthWithoutRepeatingChars(String s) {  //Runtime: O(n)
        int start = 0, end = 0, longest = 0;
//        int longestStart = 0;  // If we need the actual substring
        Set<Character> set = new HashSet<>();
        while (end < s.length()) {
            if (!set.contains(s.charAt(end))) {
                //Check that this character is not a duplicate within the current window.
                set.add(s.charAt(end));

                // We are updating the longest here because this is the window that has unique characters.
                if (end - start + 1 > longest) {
                    longest = end - start + 1;
//                    longestStart = start;
                }
            } else {  //Shrink the window.
                set.remove(s.charAt(start));
                start += 1;
            }
            end += 1;  // Increment the end pointer on each loop iteration as the window expands regularly.
        }
        return longest;
//        return s.substring(longestStart, longestStart + longest);  //To return the actual substring.
    }

    /**
     * Given a string s that consists of only uppercase English letters,
     * you can perform at most k operations on that string.
     * In one operation, you can choose any character of the string and
     * change it to any other uppercase English character.
     * Find the length of the longest sub-string containing all repeating letters you can get
     * after performing the above operations.
     * s = "AABABBA", k = 1
     * Output: 4
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstringLengthReplacingAtMostKChars(String s, int k) {
        int start = 0, end = 0;

        // We need to find the most occurring character so that we will replace its neighboring k characters.
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int maxFreq = 0;
        int longest = 0;

        while (end < s.length()) {
            // Expand the window.
            char endChar = s.charAt(end);
            frequencyMap.put(endChar, frequencyMap.getOrDefault(endChar, 0) + 1);

            // Within the window, keep track of the frequency of the most frequently occurring character.
            maxFreq = Math.max(maxFreq, frequencyMap.get(endChar));
            end += 1;

            // How many characters in the current window are different from the most frequent character.
            int charsToReplace = end - start - maxFreq;

            if (charsToReplace > k) {
                // If the number of characters that need to be replaced exceeds k,
                // we cannot make all characters in the window the same with just k replacements.
                // So shrink the window.
                char startChar = s.charAt(start);
                frequencyMap.put(startChar, frequencyMap.get(startChar) - 1);
                start += 1;
            }

            longest = Math.max(longest, end - start);
        }
        return longest;
    }

    /**
     * Find the length of the longest substring in a given string s that contains at most k distinct characters.
     * s = "AABBCC", k = 2.
     * Output: 4. (Either "AABB" or "BBCC")
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstringWithAtMostKDistinctChars(String s, int k) {
        int start = 0, end = 0;
        Map<Character, Integer> frequencyMap = new HashMap<>();

        int longest = 0;

        while (end < s.length()) {
            char endChar = s.charAt(end);
            frequencyMap.put(endChar, frequencyMap.getOrDefault(endChar, 0) + 1);
            end += 1;

            // The size of the map represents the number of distinct characters in the current window.
            // Has it exceeded k? Shrink the window.
            if (frequencyMap.size() > k) {
                char startChar = s.charAt(start);
                frequencyMap.put(startChar, frequencyMap.get(startChar) - 1);
                if (frequencyMap.get(startChar) == 0) {
                    frequencyMap.remove(startChar);
                }
                start += 1;
            }
            longest = Math.max(longest, end - start);
        }

        return longest;
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
    public List<Integer> findAnagramIndices(String s, String t) {
        int start = 0, end = 0;
        List<Integer> result = new LinkedList<>();

        Map<Character, Integer> tFreqMap = new HashMap<>();
        for (char tc : t.toCharArray()) {
            tFreqMap.put(tc, tFreqMap.getOrDefault(tc, 0) + 1);
        }

        int currentMatch = 0;
        int requiredMatch = tFreqMap.size();

        while (end < s.length()) {
            // Expand the window.
            char endChar = s.charAt(end);
            if (tFreqMap.containsKey(endChar)) {
                tFreqMap.put(endChar, tFreqMap.get(endChar) - 1);  //We finished visiting this character.
                // Reduce the count in the map.

                // Check if this character's count has reached zero; if so, it's fully matched
                if (tFreqMap.get(endChar) == 0) {
                    currentMatch += 1;
                }
            }
            end += 1;

            // When the window size matches the length of t, we evaluate it
            if (end - start == t.length()) {
                // If `currentMatches` equals `requiredMatches`, we found an anagram
                if (currentMatch == requiredMatch) {
                    result.add(start);
                }

                // As the window slides one character forward,
                // update the character counts by adding the new character at the end of the window and
                // removing the character at the beginning.
                char startChar = s.charAt(start);
                if (tFreqMap.containsKey(startChar)) {
                    if (tFreqMap.get(startChar) == 0) {
                        // If the character was fully matched (count was zero), decrement currentMatch
                        currentMatch -= 1;
                    }

                    tFreqMap.put(startChar, tFreqMap.get(startChar) + 1);  //Now we are visiting this character.
                    // Increase the count.
                }
                start += 1;
            }
        }
        return result;
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
    public boolean isAnagram(String s, String t) {  // Runtime: O(n log n) due to the sorting operation.
        // Convert strings to character arrays and sort them
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        Arrays.sort(sArray);
        Arrays.sort(tArray);

        // Compare the sorted arrays
        return Arrays.equals(sArray, tArray);
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
     * Given two strings s and t, find the minimum window of t in s.
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     *
     * @param s
     * @param t
     * @return
     */
    public String minimumWindowSubstring(String s, String t) {  // Runtime: O(s + t)
        int start = 0, end = 0;

        Map<Character, Integer> tFreqMap = new HashMap<>();
        for (char tc : t.toCharArray()) {
            tFreqMap.put(tc, tFreqMap.getOrDefault(tc, 0) + 1);
        }

        int currentMatch = 0;
        int requiredMatch = tFreqMap.size();

        int minWindLen = Integer.MAX_VALUE;  //This is crucial. Initialize to the maximum number possible.
        int minWindStart = 0;

        while (end < s.length()) {
            char endChar = s.charAt(end);
            if (tFreqMap.containsKey(endChar)) {
                tFreqMap.put(endChar, tFreqMap.get(endChar) - 1);
                if (tFreqMap.get(endChar) == 0) {
                    currentMatch += 1;
                }
            }
            end += 1;

            while (currentMatch == requiredMatch) {
                // While only works. Not an if. The while condition here checks if we have the required match.
                // Using end - start == t.length() would restrict the solution to windows of a fixed length,
                // which does not work for this problem.
                // Instead, we need to maintain a flexible window size and
                // focus on ensuring that all required characters are present in each candidate window.
                if (end - start < minWindLen) {
                    minWindLen = end - start;
                    minWindStart = start;
                }

                char startChar = s.charAt(start);
                if (tFreqMap.containsKey(startChar)) {
                    if (tFreqMap.get(startChar) == 0) {
                        currentMatch -= 1;
                    }

                    tFreqMap.put(startChar, tFreqMap.get(startChar) + 1);
                }
                start += 1;
            }
        }
        return minWindLen == Integer.MAX_VALUE ? "" : s.substring(minWindStart, minWindStart + minWindLen);
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
            right += 1;   //Expand to the right
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
     * Given a string s. You can convert s to a palindrome by adding characters in front of it.
     * Return the shortest palindrome you can find by performing this transformation.
     * s = "abcd"
     * Output: "dcbabcd"
     *
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) { // Runtime: O(n^2) due to substring comparisons.
        // Kind of a two pointer approach.
        // Reverse the string.
        StringBuilder rev = new StringBuilder(s).reverse();

        int n = s.length();

        for (int i = 0; i < n; i++) {
            // Compare each prefix of s with the corresponding suffix in rev.
            String prefixOfS = s.substring(0, n - i);
            String suffixOfRev = rev.substring(i);

            // If the prefix of s matches the suffix of rev, it means the middle portion is a palindrome
            if (prefixOfS.equals(suffixOfRev)) {
                String unmatched = rev.substring(0, i);
                // Add the missing portion (the first part of rev) to the front of s to make it a palindrome.
                return unmatched + s;
            }
        }

        return "";
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
            } else {
                set.add(c);
            }
        }
        if (!set.isEmpty()) {
            len += 1;
        }
        return len;
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
     * Longest substring with at Least k repeating Characters.
     * Return the length of the longest substring where every character appears at least k times.
     * Input: s = "ababbc", k = 2
     * Output: 5
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     *
     * @param str
     * @param k
     * @return
     */
    public int longestSubstringAtLeastKRepeatingChars(String str, int k) {
        // This solution uses an entirely different approach.
        // Divide and conquer.
        return longest(str, 0, str.length() - 1, k);
    }

    private int longest(String str, int start, int end, int k) {  //Recursion
        // Traverse the string and count the frequency of each character.
        // Split the string around characters that don't appear at least k times.
        // These characters can never be part of a valid substring.
        // For every character that doesn't meet the frequency requirement, split the string into smaller substrings.

        if (end - start < k) {  // Base case: Substring too short to have a valid substring
            return 0;
        }

        // Frequency map to count occurrences of each character in the current substring
        Map<Character, Integer> count = new HashMap<>();
        for (int i = start; i <= end; i++) {
            count.put(str.charAt(i), count.getOrDefault(str.charAt(i), 0) + 1);
        }

        for (int i = start; i <= end; i++) {
            char c = str.charAt(i);
            // Now, find a character that appears less than k times
            if (count.get(c) > 0 && count.get(c) < k) {  //Splitting criteria
                return Math.max(
                        longest(str, start, i - 1, k),
                        longest(str, i + 1, end, k)
                );
            }
        }
        // If all characters meet the condition, return the length of this substring
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
            } else {
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
        for (String word : words) {
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
