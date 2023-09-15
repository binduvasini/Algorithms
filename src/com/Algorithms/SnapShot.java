package com.Algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
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

public class SnapShot {  //Add snapshot array question here
}
