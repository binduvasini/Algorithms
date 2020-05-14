package com.Algorithms;

import java.util.HashMap;

/**
 * If we want to make use of the tree structure, we can use this class.
 * We need a root in the Trie class. In each method, we keep a copy of the root and try to insert / find the prefix.
 *
 * public void insert(String word) {
 *    TrieNode node = root;
 *    for(int i = 0; i < word.length(); i++){
 *       char c = word.charAt(i);
 *       if(!node.children.containsKey(c)){
 *          node.children.put(c, new TrieNode());
 *       }
 *       node = node.children.get(c);
 *    }
 *    node.isCompleteWord = true;
 * }
 *
 * public boolean search(String word) {
 *    TrieNode node = root;
 *    for(int i = 0; i < word.length(); i++){
 *       char c = word.charAt(i);
 *       if(!node.children.containsKey(c))
 *         return false;
 *       node = node.children.get(c);
 *    }
 *    return node.isCompleteWord;
 * }
 *
 */
class TrieNode {
    HashMap<Character, TrieNode> children;
    boolean isCompleteWord;

    public TrieNode() {
        this.children = new HashMap<>();
        this.isCompleteWord = false;
    }
}

/**
 * If the characters are lowercase letters, we can simply use an array of size 26.
 */
public class Trie {
    Trie[] children;
    HashMap<String, Boolean> isCompleteWord;  //We can have a boolean and iterate through the word or use a HashMap that offers fast lookups.
    int size = 0;

    public Trie() {
        children = new Trie[26];
        isCompleteWord = new HashMap<>();
    }

    public void insert(String word) {
        insert(word, 0);
    }

    private void insert(String word, int i) {  //We use recursion because we need to associate the children to the appropriate node.
        isCompleteWord.put(word, true);
        size += 1;
        if (word.length() == i) {
            return;
        }
        int charCode = word.charAt(i) - 'a';
        Trie child = children[charCode];  //Check if the children array contains this char. If so, insert the word to this node.
        if (child == null) {
            child = new Trie();
            children[charCode] = child;
        }
        child.insert(word, i + 1);  //Association happens
    }

    public boolean search(String word) {
        if (isCompleteWord.containsKey(word))
            return isCompleteWord.get(word);
        return false;
    }

    public boolean startsWith(String prefix) {
        return startsWith(prefix, 0);
    }

    private boolean startsWith(String pref, int i) {
        if (pref.length() == i)
            return true;
        int charCode = pref.charAt(i) - 'a';
        Trie child = children[charCode];
        if (child == null)
            return false;
        return child.startsWith(pref, i + 1);
    }

    public int findWordCountForAPrefix(String pref) {
        return findCount(pref, 0);
    }

    private int findCount(String pref, int i) {
        if (pref.length() == i)
            return size;
        int charCode = pref.charAt(i) - 'a';
        Trie child = children[charCode];
        if (child == null)
            return 0;
        return child.findCount(pref, i + 1);
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        System.out.println(trie.search("apple"));   // returns true
        System.out.println(trie.search("app"));     // returns false
        System.out.println(trie.startsWith("app")); // returns true
        trie.insert("app");
        System.out.println(trie.search("app"));     // returns true
        System.out.println(trie.findWordCountForAPrefix("ap"));
    }
}