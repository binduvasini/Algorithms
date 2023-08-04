package com.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedSet {
    Map<Integer, Integer> map;  //Use a HashMap to accommodate the insert, remove, and search operations in O(1) time.
    List<Integer> list;  //Use an ArrayList to accommodate the random operation.

    /** Initialize your data structure here. */
    public RandomizedSet() {
        map = new HashMap<>();  //The element is the key and its ArrayList index is the value.
        list = new ArrayList<>();  //To store all the elements.
    }

    /** Inserts a value to the set. */
    public boolean insert(int num) {
        if (map.containsKey(num))
            return false;

        int index = list.size();
        map.put(num, index);
        list.add(num);  //Add the element to the end of the list.

        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int num) {
        if (!map.containsKey(num))
            return false;

        int listIndex = map.get(num);
        int lastIndex = list.size() - 1;

        if (lastIndex != listIndex) {  //We need to swap the element position in the list.
            int temp = list.get(lastIndex);
            list.add(listIndex, temp);
            list.add(lastIndex, num);

            map.put(temp, listIndex);  //Update the new index position of the swapped element in the map.
        }

        list.remove(lastIndex);
        map.remove(num);

        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        int i = random.nextInt(list.size());

        return list.get(i);
    }
}
