package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Sorting {
    /**
     * Suppose you have a random list of people standing in a queue.
     * Each person is described by a pair of integers (h, k),
     * where h is the height of the person and k is the number of people in front of this person
     * who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.
     * Input:
     * [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
     * Output:
     * [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        //We sort and keep the tallest people first because
        // 1) their index position for the first few people will match k value.
        // 2) later when we insert everyone in the right position,
        //    we will not encounter ArrayIndexOutOfBounds exception.
        Arrays.sort(people, (p1, p2) ->{
            if (p1[0] == p2[0])
                return p1[1] - p2[1];
            else
                return p2[0] - p1[0];
        });

        List<int[]> queue = new LinkedList<>();
        for(int[] person : people){
            //insert the element in the right index using List.add(int index, E element);
            queue.add(person[1], person);
        }
        return queue.toArray(new int[queue.size()][]);
    }
}
