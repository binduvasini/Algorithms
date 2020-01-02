package com.Algorithms;

import java.util.HashSet;
import java.util.List;

/**
 * The solution uses bidirectional BFS.
 */
class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        if (!wordList.contains(endWord))
            return 0;

        HashSet<String> sourceQueue = new HashSet<>();  //For this problem, it is better to use HashSet rather than LinkedList
        HashSet<String> targetQueue = new HashSet<>();

        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);
        sourceQueue.add(beginWord);
        targetQueue.add(endWord);

        for (int trans = 2; !sourceQueue.isEmpty(); trans++) {

            HashSet<String> transformedWords = new HashSet<>();  //

            for (String word : sourceQueue) {
                char[] wordChar = word.toCharArray();

                for (int i = 0; i < wordChar.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char temp = wordChar[i];
                        wordChar[i] = c;
                        String newWord = String.valueOf(wordChar);

                        if (targetQueue.contains(newWord)) {  //The meeting point from both the ends. Use this condition rather than checking if newWord.equals(endWord).
                            return trans;
                        } else if (wordList.contains(newWord) && !visited.contains(newWord)) {
                            transformedWords.add(newWord);
                            visited.add(newWord);
                        }

                        wordChar[i] = temp;
                    }
                }
            }
            // always traverse the smaller one.
            // IN EVERY ITERATION WE ARE TRYING TO ALTERNATE BETWEEN SOURCE QUEUE & TARGET QUEUE
            sourceQueue = (transformedWords.size() < targetQueue.size())
                    ? transformedWords
                    : targetQueue;

            targetQueue = (sourceQueue == transformedWords)
                    ? targetQueue
                    : transformedWords;

        }
        return 0;
    }
}