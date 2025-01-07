package com.Algorithms;

import java.util.*;

/**
 * The file system is implemented using a tree structure.
 * Each node in the tree represents either a file or a directory.
 * Each directory node maintains a TreeMap of its children to ensure lexicographic order.
 * A file node has its isFile flag set to true and contains a StringBuilder to store its content.
 *
 * The Runtimes of mkdir, addContent, readContent, and ls are
 * O(k•log n) where k is the number of components in the input path,
 * n is the number of children in the tree. TreeMap takes O(log n) time for lookups.
 */
public class FileSystem {  // Similar to Trie
    static class Node {  // Similar to TrieNode
        Map<String, Node> children;  // <name, node>
        boolean isFile;  // Indicates whether the node is a file
        StringBuilder content;  // Stores content if the node is a file

        public Node() {
            this.content = new StringBuilder();
            this.children = new TreeMap<>();  // TreeMap stores the nodes in a sorted order.
            this.isFile = false;
        }
    }

    Node root;

    public FileSystem() {
        // Initialize the root in this constructor.
        root = new Node();
    }

    /**
     * Create a directory at the specified path.
     * If the intermediate directories do not exist, they are created automatically.
     *
     * @param path
     */
    public void mkdir(String path) {
        String[] components = path.split("/");
        Node current = root;
        for (String component : components) {
            current.children.putIfAbsent(component, new Node());  // Create node if absent
            current = current.children.get(component);  // Navigate to the child
        }
    }

    /**
     * Append content to a file. If the file does not exist, it is created first.
     *
     * @param filePath
     * @param content
     */
    public void addContent(String filePath, String content) {
        String[] components = filePath.split("/");
        Node current = root;
        // Navigate to the node for the given path
        for (String component : components) {
            current.children.putIfAbsent(component, new Node());
            current = current.children.get(component);
        }
        current.content.append(content);  // Append the content to the file
        current.isFile = true;  // Mark the node as a file
    }

    /**
     * Read the content of a file.
     *
     * @param filePath
     * @return
     */
    public String readContent(String filePath) {
        String[] components = filePath.split("/");
        Node current = root;
        // Navigate to the node for the given path
        for (String component : components) {
            current.children.putIfAbsent(component, new Node());
            current = current.children.get(component);
        }
        if (!current.isFile) {
            throw new IllegalArgumentException("Path is not a file: " + filePath);
        }
        return current.content.toString();
    }

    /**
     * List all files and directories in a given path, sorted lexicographically.
     *
     * @param path
     * @return
     */
    public List<String> ls(String path) {
        String[] components = path.split("/");
        Node current = root;
        // Navigate to the node for the given path
        for (String component : components) {
            current = current.children.get(component);
        }

        if (current.isFile) {
            // If it's a file, return its name in a single-item list
            return List.of(components[components.length - 1]);
        }

        // If it's a directory, return the sorted list of its children
        return new ArrayList<>(current.children.keySet());
    }
}
