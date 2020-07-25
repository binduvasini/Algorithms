package com.Algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphQuestions {

    HashMap<Integer, List<Integer>> graph = new HashMap<>();

    int BFSshortestpath(Integer source, Integer destination) {
        int[] distance = new int[50];  //We don't need visited array cuz we can track everything in distance array
        Arrays.fill(distance, -1);  //When we don't find a node, return -1
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        distance[source] = 0;  //distance to get to the source is 0
        while (!queue.isEmpty()) {
            Integer gn = queue.remove();
            if (gn.equals(destination))
                break;
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


    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     * Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
     * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * There are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
     */

//    graph = new HashMap<>();
    public boolean canFinishCourses(int numCourses, int[][] prerequisites) {
        for (int[] prerequisite : prerequisites) {
            graph.putIfAbsent(prerequisite[1], new ArrayList<>());
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        for (Integer prereq : graph.keySet()) {
            if (!visited.contains(prereq) && !hasNoCycleUtil(prereq, visited, stack)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasNoCycleUtil(Integer prereq, HashSet<Integer> visited, ArrayDeque<Integer> stack) {
        visited.add(prereq);
        stack.add(prereq);
        if (graph.containsKey(prereq)) {  //We need to check this cuz we come back here during recursion with the actual course of prerequisites
            for (Integer course : graph.get(prereq)) {
                if (!visited.contains(course) && !hasNoCycleUtil(course, visited, stack)) {
                    return false;
                } else if (stack.contains(course)) {
                    return false;
                }
            }
        }
        stack.remove(prereq);
        return true;
    }

    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
     * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
     * Input: numCourses = 4, [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,1,2,3] or [0,2,1,3]
     * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
     * Both courses 1 and 2 should be taken after you finished course 0.
     * So one correct course order is [0,1,2,3]. Another correct order is [0,2,1,3]
     */
//    graph = new HashMap<>();
    public int[] findOrderOfCourses(int numCourses, int[][] prerequisites) {
        for (int i = 0; i < numCourses; i++) {  //This is to cover test case: numCourses = 2, []. Output: [1,0]
            graph.putIfAbsent(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            graph.putIfAbsent(prerequisite[1], new ArrayList<>());
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }

        //Use topological sort.
        ArrayDeque<Integer> courseOrder = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> beingVisited = new HashSet<>();
        for (Integer prereq : graph.keySet()) {
            if (!visited.contains(prereq) && !hasNoCycleUtil(prereq, visited, courseOrder, new HashSet<>())) {
                return new int[0];
            }
        }

        int i = 0;
        int[] order = new int[numCourses];
        while (!courseOrder.isEmpty()) {
            order[i++] = courseOrder.pop();
        }

        return order;
    }

    private boolean hasNoCycleUtil(Integer prereq, HashSet<Integer> visited, ArrayDeque<Integer> courseOrder, HashSet<Integer> indegreeSet) {
        visited.add(prereq);
        indegreeSet.add(prereq);
        if (graph.containsKey(prereq)) {  //We need to check this cuz we come back here during recursion with the actual course of prerequisites
            for (Integer course : graph.get(prereq)) {
                if (!visited.contains(course) && !hasNoCycleUtil(course, visited, courseOrder, indegreeSet)) {
                    return false;
                } else if (indegreeSet.contains(course)) {
                    return false;
                }
            }
        }
        indegreeSet.remove(prereq);
        courseOrder.push(prereq);
        return true;
    }

    /**
     * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
     * Each person may dislike some other people, and they should not go into the same group.
     * Formally, if dislikes[i] = [a, b], it means it is not allowed to put the people numbered a and b into the same group.
     * Return true if and only if it is possible to split everyone into two groups in this way.
     * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
     * Output: false
     *
     * @param N
     * @param dislikes
     * @return
     */
    public boolean possibleBipartition(int N, int[][] dislikes) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] dislike : dislikes) {
            graph.putIfAbsent(dislike[0], new ArrayList<>());
            graph.get(dislike[0]).add(dislike[1]);
            graph.putIfAbsent(dislike[1], new ArrayList<>());
            graph.get(dislike[1]).add(dislike[0]);
        }
        LinkedList<Integer> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        boolean[] color = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            if (visited.contains(i))
                continue;
            queue.add(i);
            visited.add(i);
            color[i] = true;
            while (!queue.isEmpty()) {
                int node = queue.remove();
                if (graph.containsKey(node)) {
                    for (int neighbor : graph.get(node)) {
                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                            color[neighbor] = !color[node];
                        }
                        if (color[neighbor] == color[node]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * There are N network nodes, labelled 1 to N. Given a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target. We send a signal from a certain node source. How long will it take for all nodes to receive the signal? If it is impossible, return -1.
     * times = [[1,2,1],[2,3,2],[1,3,4]]
     * N = 3
     * source = 1
     * output: 3.
     * @param times
     * @param N
     * @param source
     * @return
     */
    public int networkDelayTime(int[][] times, int N, int source) {
        HashMap<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.putIfAbsent(time[0], new HashMap<>());
            graph.get(time[0]).put(time[1], time[2]);
        }
        //We don't need the distance array separately as in BFSShortestPath.
        // We can make use of the minHeap to store the distance.
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        boolean[] visited = new boolean[N+1];
        minHeap.add(new int[]{source, 0});
        int totalDistance = 0;
        while (!minHeap.isEmpty()) {
            int[] nodeDist = minHeap.remove();
            int node = nodeDist[0], distance = nodeDist[1];
            //Setting visited = true for this node before the while loop and
            // having a !visited condition for every neighbor
            // doesn't work.
            if (visited[node])
                continue;
            visited[node] = true;
            totalDistance = distance;
            N -= 1;
            if (graph.containsKey(node)) {
                Map<Integer, Integer> neighbors = graph.get(node);
                for (Integer neighbor : neighbors.keySet()) {
                    int neighborDist = distance + neighbors.get(neighbor);
                    minHeap.add(new int[]{neighbor, neighborDist});
                }
            }
        }
        return N == 0 ? totalDistance : -1;
    }

    /**
     * There are N cities connected by M flights. Each flight starts from city u and arrives at v with a price w.
     * Given all the cities and flights, together with starting city source and the destination dest, your task is to find the cheapest price from source to dest with up to k stops. If there is no such route, return -1.
     * N = 3, flights = [[0,1,100],[1,2,100],[0,2,500]]
     * source = 0, dest = 2, K = 0
     * Output: 500
     * @param N
     * @param flights
     * @param source
     * @param dest
     * @param K
     * @return
     */
    public int findCheapestPrice(int N, int[][] flights, int source, int dest, int K) {
        HashMap<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.putIfAbsent(flight[0], new HashMap<>());
            graph.get(flight[0]).put(flight[1], flight[2]);
        }

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        minHeap.add(new int[]{source, 0, K + 1});
        while (!minHeap.isEmpty()) {
            int[] nodeDist = minHeap.remove();
            int node = nodeDist[0], price = nodeDist[1], stops = nodeDist[2];
            // It fails when we include the visited logic. This solution doesn't need a visited array.
            if(node == dest)  //Found the destination.
                return price;
            if(stops > 0){  //Checking if (stops < 0) and breaking the loop doesn't work.
                if (graph.containsKey(node)) {
                    Map<Integer, Integer> neighbors = graph.get(node);
                    for (Integer neighbor : neighbors.keySet()) {
                        int neighborDist = price + neighbors.get(neighbor);
                        minHeap.add(new int[]{neighbor, neighborDist, stops - 1});
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
     * Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
     * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     * @param tickets
     * @return
     */
    private HashMap<String, PriorityQueue<String>> iteneraryGraph = new HashMap<>();
    private LinkedList<String> orderedItenerary = new LinkedList<>();
    public List<String> orderItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            iteneraryGraph.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            iteneraryGraph.get(ticket.get(0)).add(ticket.get(1));
        }
        dfsUtil("JFK");
        return orderedItenerary;
    }

    private void dfsUtil(String flight) {
        if (iteneraryGraph.containsKey(flight)) {
            PriorityQueue<String> connectingFlights = iteneraryGraph.get(flight);
            while (!connectingFlights.isEmpty()) {
                dfsUtil(connectingFlights.remove());
            }
        }
        orderedItenerary.addFirst(flight);
    }
}
