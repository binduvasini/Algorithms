package com.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NestedList {
    public static void main(String[] args) {
        List<Object> nestedList = lst(1, lst(2, lst(3, 4)), lst(5, 6, 7), 8, lst(lst(9, 10)), 9);
        List<Integer> flatList = flatten(nestedList);
        System.out.println(nestedList);
        System.out.println(flatList);
    }

    public static List<Integer> flatten(List<?> list) {
        List<Integer> ret = new LinkedList<>();
        flattenHelper(list, ret);
        return ret;
    }

    public static void flattenHelper(List<?> nestedList, List<Integer> flatList) {
        for (Object item : nestedList) {
            if (item instanceof List<?>) {
                flattenHelper((List<?>) item, flatList);
            } else {
                flatList.add((Integer) item);
            }
        }
    }

    private static List<Object> lst(Object... objs) {
        return Arrays.asList(objs);
    }
}
