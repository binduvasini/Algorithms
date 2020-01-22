package com.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GraphQuestions {
//    static HashMap<String, List<String>> graph = new HashMap<>();

    static HashMap<Integer, List<Integer>> graph = new HashMap<>();

    int BFSshortestpath(Integer source, Integer destination) {
        int[] distance = new int[50];  //We don't need visited array cuz we can track everything in distance array
        Arrays.fill(distance, -1);  //When we don't find a node, return -1
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        distance[source] = 0;  //distance to get to the source is 0
        while (!queue.isEmpty()) {
            Integer gn = queue.remove();
            if (gn.equals(destination)) break;
            if (graph.containsKey(gn)) {
                for (Integer neighbor : graph.get(gn)) {
                    if (distance[neighbor] == -1) {
                        queue.add(neighbor);
                        distance[neighbor] = distance[gn] + 1;
                    }
                }
            }
        }
        return distance[destination];
    }

    public static void main(String[] args) {
//        String[][] array = {{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"ATL", "JFK"}, {"ATL", "SFO"}};
        int[][] array = {{4, 1}, {7, 9}, {10, 2}, {16, 19}};
        for (int[] arr : array) {
            graph.putIfAbsent(arr[0], new ArrayList<>());
            graph.get(arr[0]).add(arr[1]);
        }
    }
}
