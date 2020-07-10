package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NestedList {
    public static List<Integer> flatten(List<?> nestedList) {
        return flattenHelper(nestedList, new LinkedList<>());
    }

    public static List<Integer> flattenHelper(List<?> nestedList, List<Integer> flatList) {
        for (Object item : nestedList) {
            if (item instanceof List<?>) {
                flattenHelper((List<?>) item, flatList);
            } else {
                flatList.add((Integer) item);
            }
        }
        return flatList;
    }

    public static void main(String[] args) {
        List<Object> nestedList = lst(1, lst(2, lst(3, 4)), lst(5, 6, 7), 8, lst(lst(9, 10)), 11);
        List<Integer> flatList = flatten(nestedList);
        System.out.println("Nested list: "+nestedList);
        System.out.println("Flattened list: "+flatList);
    }

    private static List<Object> lst(Object... objs) {
        return Arrays.asList(objs);
    }
}
