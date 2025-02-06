package com.Algorithms;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

/**
 * Time Based Key Value Store
 * Design a time-based key-value data structure that can store multiple values for the same key
 * at different time stamps and retrieve the key's value at a certain timestamp.

 * TimeMap timeMap = new TimeMap();
 * timeMap.put("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
 * timeMap.get("foo", 1);         // return "bar"
 * timeMap.get("foo", 3);         // return "bar",
 *                                since there is no value corresponding to foo at timestamp 3 and timestamp 2,
 *                                then the only value is at timestamp 1 is "bar".
 * timeMap.put("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
 * timeMap.get("foo", 4);         // return "bar2"
 * timeMap.get("foo", 5);         // return "bar2"
 */
public class TimeBasedKeyValueStore {
    Map<String, TreeMap<Integer, String>> store;  //TreeMap has the timestamp as key and the string as the value
    //The question asks to find a previous timestamp for this value.
    // TreeMap has a function that helps achieve this.

    public TimeBasedKeyValueStore() {
        store = new HashMap<>();
    }

    // runs in O(log n) because adding a new key in a TreeMap takes logarithmic time.
    public void put(String key, String value, int timestamp) {
        store.putIfAbsent(key, new TreeMap<>());
        store.get(key).put(timestamp, value);
    }

    // runs in O(log n) due to the floorKey() lookup, which again takes logarithmic time.
    public String get(String key, int timestamp) {
        if (store.containsKey(key)) {
            TreeMap<Integer, String> treeMap = store.get(key);

            Integer floorKey = treeMap.floorKey(timestamp);

            if (floorKey != null) {  //floorKey returns the largest key <= the key passed
                return treeMap.get(floorKey);
            }
        }

        return "";
    }
}
