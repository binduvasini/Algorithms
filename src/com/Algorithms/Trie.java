package com.Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * If the characters are lowercase letters, we can simply use an array of size 26.
 */
public class Trie {
    static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isWord = false;
        }
    }

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Start from the root. It is the current node.
     * Iterate through each character of the given word.
     * If the current node has the current character (through one of the elements in the “children” field), move on.
     * Otherwise, create a new node for this character.
     * Repeat until the entire word is traversed.
     *
     * @param word
     */
    public void add(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isWord = true;
    }

    /**
     * Start from the root. It is the current node.
     * Iterate through each character of the word.
     * If the current node has the current character (through one of the elements in the “children” field),
     * move on to its sub-trie.
     * Otherwise, return false.
     * Repeat until the entire word is traversed.
     * In the end, return the current node's isWord boolean.

     * This works only for the exact character match. To handle wildcard matches, incorporate recursion / backtracking.
     *
     * @param word
     * @return whether the trie contains this word.
     */
    public boolean contains(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return true;
    }

    /**
     * Search a word in the Trie with support for '.' wildcard
     *
     * @param word
     * @return
     */
    public boolean search(String word) {
        return searchInNode(word, 0, root);
    }

    private boolean searchInNode(String word, int index, TrieNode node) {
        if (index == word.length()) {
            return node.isWord;
        }

        TrieNode current = node;

        for (int i = index; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c == '.') {
                // Try each child node
                for (Character key : node.children.keySet()) {
                    if (searchInNode(word, i + 1, node.children.get(key))) {
                        return true;
                    }
                }
                return false; // No match found with any child node
                // Suppose the Trie has the words cat, bat, and rat, and searching for the word c.tz will return false.
            } else {
                // Exact character match
                if (!current.children.containsKey(c)) {
                    return false; // Character not found
                }
                current = current.children.get(c);
            }
        }

        // Check if the last node is the end of a word
        return current.isWord;
    }

    /**
     * Recursively delete each character in the word from the trie.
     *
     * @param word
     */
    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(TrieNode current, String word, int i) {
        if (i == word.length()) {
            if (!current.isWord) {
                return false;
            }
            current.isWord = false;
            return current.children.isEmpty();
        }
        char c = word.charAt(i);
        TrieNode child = current.children.get(c);
        if (child == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(child, word, i + 1) && !child.isWord;
        if (shouldDeleteCurrentNode) {
            current.children.remove(c);
            return current.children.isEmpty();
        }
        return false;
    }

    public void allWordsStartingWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (current.children.containsKey(ch))
                current = current.children.get(ch);
        }
        List<String> resultList = new LinkedList<>();
        allWordsStartingWith(resultList, current, prefix);
        System.out.println(Arrays.toString(resultList.toArray()));
    }

    private void allWordsStartingWith(List<String> resultList, TrieNode current, String word) {
        if (current.isWord) {
            resultList.add(word);
        }
        for (char ch : current.children.keySet()) {
            TrieNode child = current.children.get(ch);
            allWordsStartingWith(resultList, child, word + ch);
        }
    }

    public int findWordCountForAPrefix(String prefix) {
        return findCount(root, prefix, 0, 0);
    }

    private int findCount(TrieNode current, String prefix, int i, int count) {
        if (prefix.length() == i)
            return count;
        char ch = prefix.charAt(i);
        TrieNode child = current.children.get(ch);
        if (child == null)
            return 0;
        return findCount(child, prefix, i + 1, count + 1);
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("apple");
        System.out.println(trie.contains("apple"));   // returns true
        System.out.println(trie.contains("app"));     // returns false
        System.out.println(trie.startsWith("app")); // returns true
        trie.add("app");
        trie.add("append");
        trie.add("appreciate");
        System.out.println(trie.contains("app"));     // returns true
        trie.allWordsStartingWith("app");
    }
}
