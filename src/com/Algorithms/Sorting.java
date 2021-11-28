package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Sorting {
    /**
     * There are 2N people a company is planning to interview.
     * The cost of flying the i-th person to city A is costs[i][0],
     * and the cost of flying the i-th person to city B is costs[i][1].
     * Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.
     * Input: [[10,20],[30,200],[400,50],[30,20]]
     * Output: 110
     * The first person goes to city A for a cost of 10.
     * The second person goes to city A for a cost of 30.
     * The third person goes to city B for a cost of 50.
     * The fourth person goes to city B for a cost of 20.
     * The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.
     *
     * @param costs
     * @return
     */
    public int twoCityScheduling(int[][] costs) {
        int N = costs.length / 2, total = 0;
        int A = 0, B = 0;
        //We need to sort the array in decreasing order because we need the minimum cost.
        // So rule out the maximum costs first.
        //If we sort it in increasing order instead, we will get the maximum cost.
        Arrays.sort(costs, (c1, c2) -> Math.abs(c2[0] - c2[1]) - Math.abs(c1[0] - c1[1]));

        for (int[] cost : costs) {
            if ((cost[0] <= cost[1] && A < N) || B == N) {  //making sure we have N candidates in each city.
                A += 1;
                total += cost[0];
            } else {
                B += 1;
                total += cost[1];
            }
        }
        return total;
    }

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
