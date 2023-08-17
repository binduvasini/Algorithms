package com.Algorithms;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * You have a queue of integers, you need to retrieve the first unique integer in the queue.

 * Implement the FirstUnique class:

 * FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
 * int showFirstUnique() returns the value of the first unique integer of the queue,
 * and returns -1 if there is no such integer.
 * void add(int value) insert value to the queue.
 */
class FirstUniqueNumber {
    Set<Integer> queue;
    Set<Integer> set;

    public FirstUniqueNumber(int[] nums) {
        queue = new LinkedHashSet<>();
        set = new HashSet<>();
        for (int num : nums) {
            if (!set.contains(num) && !queue.contains(num))
                set.add(num);
            else
                set.remove(num);
            queue.add(num);
        }
    }

    public int showFirstUnique() {
        for (int num : queue) {
            if (set.contains(num))
                return num;
        }
        return -1;
    }

    public void add(int num) {
        if (!set.contains(num) && !queue.contains(num))
            set.add(num);
        else
            set.remove(num);
        queue.add(num);
    }
}

public class FetchFirstUniqueNumber {
    public static void main(String[] args) {
        int[] nums = {2, 3, 5};
        FirstUniqueNumber firstUnique = new FirstUniqueNumber(nums);
        System.out.println(firstUnique.showFirstUnique());
        firstUnique.add(5);
        System.out.println(firstUnique.showFirstUnique());
        firstUnique.add(2);
        System.out.println(firstUnique.showFirstUnique());
        firstUnique.add(3);
        System.out.println(firstUnique.showFirstUnique());
    }
}