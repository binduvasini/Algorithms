package com.Algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
 */
class WordLadder {  //The solution uses bidirectional BFS.
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        if (!wordList.contains(endWord))  //edge case
            return 0;

        Set<String> sourceQueue = new HashSet<>();
        //For this problem, it is better to use HashSet rather than LinkedList
        Set<String> targetQueue = new HashSet<>();

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        sourceQueue.add(beginWord);
        targetQueue.add(endWord);

        //The shortest transformation sequence is the sum of levels of the meet point node from both the ends.
        // Thus, for every visited node we save its level as value in the visited dictionary.
        int trans = 2;
        while (!sourceQueue.isEmpty()) {

            Set<String> transformedWords = new HashSet<>();
            //At this level, the words are transformed, so save them here.

            for (String word : sourceQueue) {
                char[] wordChar = word.toCharArray();

                for (int i = 0; i < wordChar.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char temp = wordChar[i];
                        wordChar[i] = c;
                        String newWord = String.valueOf(wordChar);

                        if (targetQueue.contains(newWord)) {
                            //The meeting point from both the ends.
                            // Use this condition rather than checking if newWord.equals(endWord).
                            return trans;
                        } else if (wordList.contains(newWord) && !visited.contains(newWord)) {
                            transformedWords.add(newWord);
                            visited.add(newWord);
                        }

                        wordChar[i] = temp;
                    }
                }
            }
            // update the sourceQueue and targetQueue at every iteration.
            // always traverse the smaller one.
            // IN EVERY ITERATION WE ARE TRYING TO ALTERNATE BETWEEN SOURCE QUEUE & TARGET QUEUE
            sourceQueue = (transformedWords.size() < targetQueue.size())
                    //From this transformed word, we need to transform further to get the endWord.
                    ? transformedWords
                    : targetQueue;

            targetQueue = (sourceQueue == transformedWords)
                    ? targetQueue
                    : transformedWords;

            trans += 1;
        }

        return 0;
    }
}
