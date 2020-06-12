package com.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomizedSet {
    HashMap<Integer, Integer> map;
    ArrayList<Integer> list;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(map.containsKey(val))
            return false;
        int index = list.size();
        map.put(val, index);
        list.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(!map.containsKey(val))
            return false;
        int listIndex = map.get(val);
        int lastIndex = list.size()-1;
        if(lastIndex != listIndex){
            int temp = list.get(lastIndex);
            list.set(listIndex, temp);
            list.set(lastIndex, val);
            map.put(temp, listIndex);
        }
        list.remove(lastIndex);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        int k = random.nextInt(list.size());
        return list.get(k);
    }
}
