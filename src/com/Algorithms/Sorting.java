package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        Arrays.sort(costs, (c1, c2) ->
                Math.abs(c2[0] - c2[1]) - Math.abs(c1[0] - c1[1])
        );

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
        Arrays.sort(people, (p1, p2) -> {
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

    /**
     * Given an array of even length, check if it is possible to reorder elements such that
     * the element and its double are next to each other.
     * arr = [4,-2,2,-4]
     * Output: true. Can be reordered as [-2,-4,2,4]
     *
     * @param arr
     * @return
     */
    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        Integer[] array = Arrays.stream(arr).boxed().toArray( Integer[]::new ); //To use comparator
        Arrays.sort(array, Comparator.comparingInt(Math::abs)); //To tackle negative numbers

        for (int num : array) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : array) {
            int doubled = num * 2;

            //No need to check whether map containsKey.
            if (map.get(num) == 0)  //This num may be that double we already found. So will continue.
                continue;
            //Instead of containsKey
            if (map.getOrDefault(doubled, 0) <= 0)  //The double is not found. So we can return here.
                return false;

            map.put(doubled, map.get(doubled) - 1);  //Towards the end, we should have a hashmap with empty values.
            map.put(num, map.get(num) - 1);

        }
        return true;
    }

    /**
     * An array original is transformed into a doubled array changed by appending the double of every element,
     * and then randomly shuffling the resulting array.
     * Return the original array.
     * changed = [1,3,4,2,6,8]
     * Output: [1,3,4]
     *
     * @param changed
     * @return
     */
    public int[] findOriginalArray(int[] changed) {
        if (changed.length % 2 != 0)
            return new int[0];

        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        Integer[] array = Arrays.stream(changed).boxed().toArray( Integer[]::new );
        Arrays.sort(array, Comparator.comparingInt(Math::abs)); //To tackle negative numbers

        for (int num : array) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : array) {
            int doubled = num * 2;

            if (map.get(num) == 0)  //This num may be that double we already found. So will continue.
                continue;
            if (map.getOrDefault(doubled, 0) <= 0)  //The double is not found. So we can return here.
                return new int[0];  //Negative case

            map.put(doubled, map.get(doubled) - 1);
            map.put(num, map.get(num) - 1);
            list.add(num);
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            char[] strChar = str.toCharArray();
            Arrays.sort(strChar);

            String anagram = String.valueOf(strChar);

            map.putIfAbsent(anagram, new ArrayList<>());
            map.get(anagram).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
