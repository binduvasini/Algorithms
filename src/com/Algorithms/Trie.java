package com.Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isWord;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isWord = false;
    }
}

/**
 * If the characters are lowercase letters, we can simply use an array of size 26.
 */
public class Trie {
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
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
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
     *
     * @param word
     * @return whether the trie contains this word.
     */
    public boolean contains(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!current.children.containsKey(ch))
                return false;
            current = current.children.get(ch);
        }
        return current.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++){
            char ch = prefix.charAt(i);
            if (!current.children.containsKey(ch))
                return false;
            current = current.children.get(ch);
        }
        return true;
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
        if (word.length() == i) {
            if (!current.isWord) {
                return false;
            }
            current.isWord = false;
            return current.children.isEmpty();
        }
        char ch = word.charAt(i);
        TrieNode child = current.children.get(ch);
        if (child == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(child, word, i + 1) && !child.isWord;
        if (shouldDeleteCurrentNode) {
            current.children.remove(ch);
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
        trie.insert("apple");
        System.out.println(trie.contains("apple"));   // returns true
        System.out.println(trie.contains("app"));     // returns false
        System.out.println(trie.startsWith("app")); // returns true
        trie.insert("app");
        trie.insert("append");
        trie.insert("appreciate");
        System.out.println(trie.contains("app"));     // returns true
        trie.allWordsStartingWith("app");
    }
}