package com.Algorithms;

import java.util.*;

public class FileSystem {  //Similar to Trie
    static class File {  //Similar to TrieNode
        String content;
        Map<String, File> children;  //<directory-name, file>
        boolean isFile;

        public File() {
            this.content = "";
            this.children = new HashMap<>();
            this.isFile = false;
        }
    }

    File root;

    public FileSystem() {
        root = new File();
    }

    public void addContent(String path, String content) {
        File current = root;
        String[] files = path.split("/");
        for (String file : files) {
            current.children.putIfAbsent(file, new File());  //file name is the key. new File() is the value.
            current = current.children.get(file);
        }
        current.isFile = true;
        current.content = current.content + content;
    }

    public String readContent(String path) {
        File current = root;
        String[] files = path.split("/");
        for (String file : files) {
            current.children.putIfAbsent(file, new File());
            current = current.children.get(file);
        }
        return current.content;
    }

    public void mkdir(String path) {
        File current = root;
        String[] files = path.split("/");
        for (String file : files) {
            current.children.putIfAbsent(file, new File());
            current = current.children.get(file);
        }
    }

    public List<String> ls(String path) {
        File current = root;
        List<String> res = new LinkedList<>();
        if (!path.equals("/")) {
            String[] files = path.split("/");
            for (String file : files) {
                current = current.children.get(file);
            }
            if (current.isFile) {
                res.add(files[files.length - 1]);
                return res;  //Revisit this logic.
            }
        }
        List<String> res_files = new LinkedList<>(current.children.keySet());
        Collections.sort(res_files);
        return res_files;
    }
}
