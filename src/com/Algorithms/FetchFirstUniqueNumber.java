package com.Algorithms;

import java.util.HashSet;
import java.util.LinkedHashSet;

class FirstUniqueNumber {
    LinkedHashSet<Integer> queue;
    HashSet<Integer> set;

    public FirstUniqueNumber(int[] nums) {
        queue = new LinkedHashSet<>();
        set = new HashSet<>();
        for (int n : nums) {
            if (!set.contains(n) && !queue.contains(n)) {
                set.add(n);
                queue.add(n);
            } else {
                queue.add(n);
                set.remove(n);
            }
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
        if (!set.contains(value) && !queue.contains(value)) {
            set.add(value);
            queue.add(value);
        } else {
            queue.add(value);
            set.remove(value);
        }
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