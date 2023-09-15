package com.Algorithms;

import java.util.*;

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
class TimeMap {
    Map<String, TreeMap<Integer, String>> map;  //Treemap has the timestamp as key and the string as the value
    //The question asks to find a previous timestamp for this value.
    // TreeMap has a function that helps achieve this.

    public TimeMap() {
        map = new HashMap<>();
    }

    public void put(String key, String value, int timestamp) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (map.containsKey(key)) {
            TreeMap<Integer, String> treeMap = map.get(key);

            if (treeMap.floorKey(timestamp) != null) {  //floorKey returns the largest key <= the key passed
                int tmKey = treeMap.floorKey(timestamp);
                return treeMap.get(tmKey);
            }
        }

        return "";
    }
}

/**
 * Snapshot Array
 * Implement a SnapshotArray that supports the following interface:
 * SnapshotArray(int length) initializes an array-like data structure with the given length.
 * void set(index, val) sets the element at the given index to be equal to val.
 * int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
 * int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 */
public class SnapShot {  //A treemap is the best data structure for this.
    List<TreeMap<Integer, Integer>> snapShot;
    int snapCount;

    public SnapShot(int length) {
        snapShot = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            snapShot.add(new TreeMap<>());
        }
    }

    public void set(int index, int val) {
        snapShot.get(index).put(snapCount, val);
    }

    public int snap() {
        return snapCount;
    }

    public int get(int index, int snap_id) {
        Integer key = snapShot.get(index).floorKey(snap_id);
        return key == null ? 0 : snapShot.get(index).get(key);
    }
}
