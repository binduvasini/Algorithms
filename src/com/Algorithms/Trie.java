package com.Algorithms;

import java.util.HashMap;

class TrieNode {
    HashMap<Character, TrieNode> children;
    boolean isCompleteWord;

    public TrieNode(HashMap<Character, TrieNode> children) {
        this.children = children;
        this.isCompleteWord = false;
    }
}

public class Trie {
}
