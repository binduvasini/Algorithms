package com.Algorithms;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * You have a queue of integers, you need to retrieve the first unique integer in the queue.
 *
 * Implement the FirstUnique class:
 *
 * FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
 * int showFirstUnique() returns the value of the first unique integer of the queue,
 * and returns -1 if there is no such integer.
 * void add(int value) insert value to the queue.
 */
class FirstUniqueNumber {
    LinkedHashSet<Integer> queue;
    HashSet<Integer> set;

    public FirstUniqueNumber(int[] nums) {
        queue = new LinkedHashSet<>();
        set = new HashSet<>();
        for (int n : nums) {
            if (!set.contains(n) && !queue.contains(n))
                set.add(n);
            else
                set.remove(n);
            queue.add(n);
        }
    }

    public int showFirstUnique() {
        for (int k : queue) {
            if (set.contains(k))
                return k;
            else
                queue.add(k);
        }
        return -1;
    }

    public void add(int value) {
        if (!set.contains(value) && !queue.contains(value))
            set.add(value);
        else
            set.remove(value);
        queue.add(value);
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