package com.Algorithms;

import java.util.*;

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
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.
     *
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     *
     * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
     *
     * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 courses to take.
     *              To take course 1 you should have finished course 0, and to take course 0 you should
     *              also have finished course 1. So it is impossible.
     */

    HashMap<Integer, List<Integer>> graph = new HashMap<>();
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        for (int i = 0; i < prerequisites.length; i++) {
            graph.putIfAbsent(prerequisites[i][1], new ArrayList<>());
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        for(Integer n : graph.keySet()){
            if(!visited.contains(n) && !dfsDetectCycle(n, visited, stack)){
                return false;
            }
        }
        return true;
    }

    private boolean dfsDetectCycle(Integer node, HashSet<Integer> visited, ArrayDeque<Integer> stack){
        visited.add(node);
        stack.add(node);
        if(graph.containsKey(node)){
            for(Integer n : graph.get(node)){
                if(!visited.contains(n) && !dfsDetectCycle(n, visited, stack)){
                    return false;
                }
                else if(stack.contains(n)){
                    return false;
                }
            }
        }
        stack.remove(node);
        return true;
    }

    /**
     * There are a total of n courses you have to take, labeled from 0 to n-1.
     *
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     *
     * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
     *
     * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
     * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,1,2,3] or [0,2,1,3]
     * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
     *              courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
     *              So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3]
     */
    HashMap<Integer, List<Integer>> graph = new HashMap<>();
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        for(int i=0; i<numCourses; i++){
            List<Integer> l = new ArrayList<>();
            graph.put(i, l);
        }

        for (int i = 0; i < prerequisites.length; i++) {
            if (graph.containsKey(prerequisites[i][1])) {
                graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
            } else {
                List<Integer> l = new ArrayList<>();
                l.add(prerequisites[i][0]);
                graph.put(prerequisites[i][1], l);
            }
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> beingVisited = new HashSet<>();
        for(Integer n : graph.keySet()){
            if(!visited.contains(n) && !topologicalSortUtil(n, visited, stack, beingVisited)){
                return new int[0];
            }
        }

        int i = 0;
        int[] order = new int[numCourses];
        while(!stack.isEmpty()){
            order[i++] = stack.pop();
        }

        return order;
    }

    private boolean topologicalSortUtil(Integer node, HashSet<Integer> visited, ArrayDeque<Integer> stack, HashSet<Integer> beingVisited){
        visited.add(node);
        beingVisited.add(node);
        if(graph.containsKey(node)){
            for(Integer n : graph.get(node)){
                if(!visited.contains(n) && !topologicalSortUtil(n, visited, stack, beingVisited)){
                    return false;
                }
                else if(beingVisited.contains(n)){
                    return false;
                }
            }
        }
        beingVisited.remove(node);
        stack.push(node);

        return true;
    }

    /**
     * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
     *
     * Each person may dislike some other people, and they should not go into the same group.
     *
     * Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
     *
     * Return true if and only if it is possible to split everyone into two groups in this way.
     *
     * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
     * Output: false
     * @param N
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition(int N, int[][] dislikes) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < dislikes.length; i++) {
            graph.putIfAbsent(dislikes[i][0], new ArrayList<>());
            graph.get(dislikes[i][0]).add(dislikes[i][1]);
            graph.putIfAbsent(dislikes[i][1], new ArrayList<>());
            graph.get(dislikes[i][1]).add(dislikes[i][0]);
        }
        LinkedList<Integer> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        boolean[] color = new boolean[N + 1];
        for (int i = 1; i <= N; i++){
            if (visited.contains(i))
                continue;
            queue.add(i);
            visited.add(i);
            color[i] = true;
            while (!queue.isEmpty()) {
                int node = queue.remove();
                if (graph.containsKey(node)){
                    for (int neighbor : graph.get(node)) {
                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                            color[neighbor] = !color[node];
                        }
                        if (color[neighbor] == color[node]){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
