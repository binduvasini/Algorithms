package com.Algorithms;

import java.util.*;

/**
 * You have a queue of integers, you need to retrieve the first unique integer in the queue.
 * Implement the FirstUnique class:
 * FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
 * int showFirstUnique() returns the value of the first unique integer of the queue,
 * and returns -1 if there is no such integer.
 * void add(int value) insert value to the queue.
 */
class FirstUniqueNumber {
    // LinkedHashMap keeps insertion order and allows efficient lookups.
    // The key is the number, and the value is a Boolean indicating whether the number is unique.
    LinkedHashMap<Integer, Boolean> map;

    public FirstUniqueNumber(int[] nums) {
        map = new LinkedHashMap<>();
        for (int num : nums) {
            add(num); // Process each number in the initial list
        }
    }

    public int showFirstUnique() {
        // Iterate through the LinkedHashMap in insertion order
        for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) { // Check if the number is marked as unique
                return entry.getKey(); // Return the first unique number
            }
        }
        return -1; // No unique number found
    }

    // Method to add a number to the stream
    public void add(int num) {
        if (map.containsKey(num)) {
            // If the number already exists, mark it as non-unique
            map.put(num, false);
        } else {
            // If the number is new, add it to the map and mark it as unique
            map.put(num, true);
        }
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 5};
        FirstUniqueNumber firstUnique = new FirstUniqueNumber(nums);
        System.out.println(firstUnique.showFirstUnique()); // Output: 2
        firstUnique.add(5);
        System.out.println(firstUnique.showFirstUnique()); // Output: 2
        firstUnique.add(2);
        System.out.println(firstUnique.showFirstUnique()); // Output: 3
        firstUnique.add(3);
        System.out.println(firstUnique.showFirstUnique()); // Output: -1
    }
}
