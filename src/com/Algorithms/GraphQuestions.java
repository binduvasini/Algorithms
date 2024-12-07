package com.Algorithms;

import java.util.*;

public class GraphQuestions {

    Map<Integer, List<Integer>> graph = new HashMap<>();

    /**
     * Find the shortest path from source to destination using BFS.
     *
     * @param source
     * @param destination
     * @return
     */
    public int bfsShortestPath(Integer source, Integer destination) {
        // Initialize a queue for BFS and add the starting node (source)
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        // Maintain a Set to keep track of visited nodes to avoid cycles
        Set<Integer> visited = new HashSet<>();
        visited.add(source);

        int distance = 0;

        while (!queue.isEmpty()) {
            // distance represents the shortest path length,
            // incrementing by 1 at each level to measure how far we've traversed from the source.
            distance += 1;

            // Number of nodes at the current level
            int nodesAtCurrentLevel = queue.size();
            // processes all nodes at the same distance level.
            for (int i = 0; i < nodesAtCurrentLevel; i++) {
                Integer node = queue.remove();

                if (graph.containsKey(node)) {
                    // Iterate through each neighbor of the current node
                    for (Integer neighbor : graph.get(node)) {
                        if (neighbor.equals(destination)) {
                            return distance;
                        }
                        // If the neighbor hasn't been visited, add it to the queue and mark as visited
                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * There are a total of numCourses courses you have to take. Some courses may have prerequisites,
     * for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
     * Given the total number of courses and a list of prerequisite pairs,
     * is it possible for you to finish all courses?
     * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * There are a total of 2 courses to take.
     * To take course 1 you should have finished course 0,
     * and to take course 0 you should also have finished course 1. So it is impossible.
     */
//    graph = new HashMap<>();
    public boolean canFinishCourses(int[][] courses) {
        // Runtime: O(V+E), where V is the number of courses and E is the number of prerequisites.
        // We go through each course and its prerequisites once.
        for (int[] course : courses) {
            graph.putIfAbsent(course[1], new ArrayList<>());
            // [1, 0] means that course 1 depends on course 0.
            // This is represented as an edge in the graph where 0 -> 1.
            // We want the prerequisite to point to dependent courses.
            // prerequisites = [[1, 0], [2, 1], [3, 2]] means 0 → 1 → 2 → 3
            graph.get(course[1]).add(course[0]);
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        for (Integer course : graph.keySet()) {
            if (!visited.contains(course) && hasCycle(course, visited, visiting)) {
                return false;
            }
        }
        return true;
    }

    //No need to do topological sort (DFS) for this question. We just need to detect a cycle

    // A node has 3 possible states:
    // visited - node has been added to the output. We never have to visit this node again.
    // visiting - node has not been added to the output. We are visiting and moving forward to the next node.
    //              This helps us determine if there is a cycle.
    // unvisited - node has not been added to the output, and we don't know if there is a cycle.
    private boolean hasCycle(Integer course, Set<Integer> visited, Set<Integer> visiting) {
        visited.add(course);
        visiting.add(course);
        if (graph.containsKey(course)) {
            for (Integer prereq : graph.get(course)) {
                if (!visited.contains(prereq) && hasCycle(prereq, visited, visiting)) {
                    return true;
                } else if (visiting.contains(prereq)) {
                    return true;
                }
            }
        }
        // We need to clear this node from the visiting set.
        // It clears the current recursive path once the node is fully processed.
        // It ensures that the visiting set only reflects the active path in the current recursion.
        visiting.remove(course);
        return false;
    }

    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses-1.
     * Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
     * which is expressed as a pair: [0,1]
     * Given the total number of courses and a list of prerequisite pairs,
     * return the ordering of courses you should take to finish all courses.
     * There may be multiple correct orders, you just need to return one of them.
     * If it is impossible to finish all courses, return an empty array.
     * Input: numCourses = 4, [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,1,2,3] or [0,2,1,3]
     * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
     * Both courses 1 and 2 should be taken after you finished course 0.
     * So one correct course order is [0,1,2,3]. Another correct order is [0,2,1,3]
     */
//    graph = new HashMap<>();
    public int[] findOrderOfCourses(int numCourses, int[][] courses) {
        Deque<Integer> courseOrder = new ArrayDeque<>(); // Stack

        for (int[] course : courses) {
            graph.putIfAbsent(course[1], new ArrayList<>());
            // [1, 0] means that course 1 depends on course 0.
            // This is represented as an edge in the graph where 0 -> 1.
            // We want the prerequisite to point to dependent courses.
            // prerequisites = [[1, 0], [2, 1], [3, 2]] means 0 → 1 → 2 → 3
            graph.get(course[1]).add(course[0]);
        }

        //Use topological sort.
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();
        for (Integer course : graph.keySet()) {
            if (!visited.contains(course) && dfsCycleCheck(course, visited, visiting, courseOrder)) {
                return new int[0];  //return an empty array because there is a cycle.
            }
        }

        return courseOrder.stream().mapToInt(i -> i).toArray();
    }

    private boolean dfsCycleCheck(
            Integer course, Set<Integer> visited, Set<Integer> visiting, Deque<Integer> courseOrder
    ) {
        visited.add(course);
        visiting.add(course); //To detect a cycle
        if (graph.containsKey(course)) {
            //We need to check this cuz we come back here during recursion with the actual course of prerequisites
            for (Integer prereq : graph.get(course)) {
                if (!visited.contains(prereq) && dfsCycleCheck(prereq, visited, visiting, courseOrder)) {
                    return true;
                } else if (visiting.contains(prereq)) {
                    return true;
                }
            }
        }
        visiting.remove(course);
        courseOrder.push(course); //Store the sorted elements
        return false;
    }


    /**
     * Change the color of a starting pixel and all connected pixels (up, down, left, right)
     * that have the same initial color as the starting pixel to a new color.
     * Input:
     * [
     *   [1,1,1],
     *   [1,1,0],
     *   [1,0,1]
     * ]
     * sr = 1, sc = 1, newColor = 2
     * Output:
     * [
     *   [2,2,2],
     *   [2,2,0],
     *   [2,0,1]
     * ]
     *
     * @param image
     * @param startRow
     * @param startCol
     * @param newColor
     * @return
     */
    public int[][] floodFill(int[][] image, int startRow, int startCol, int newColor) {
        // Store the original color of the starting pixel
        int origColor = image[startRow][startCol];

        if (origColor != newColor) {
            // Start the DFS to fill the region
            dfs(image, startRow, startCol, origColor, newColor);
        }

        return image;
    }

    private void dfs(int[][] image, int r, int c, int origColor, int newColor) {
        // Check if the current pixel is out of bounds
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) {
            return;
        }

        // If the current pixel's color is not the original color, stop recursion
        if (image[r][c] != origColor) {
            return;
        }

        // Change the current pixel's color to the new color
        image[r][c] = newColor;

        // Recursively apply the flood fill to the surrounding pixels
        dfs(image, r + 1, c, origColor, newColor);
        dfs(image, r - 1, c, origColor, newColor);
        dfs(image, r, c + 1, origColor, newColor);
        dfs(image, r, c - 1, origColor, newColor);
    }

    /**
     * Given a 2D grid of integers where:
     * -1 represents a wall or obstacle.
     * 0 represents a gate.
     * INF represents an empty room.
     * Fill each empty room with the distance to its nearest gate.
     * If it is impossible to reach a gate, leave the cell as INF.
     *
     * @param rooms
     */
    private static final int INF = Integer.MAX_VALUE;
    public void wallsAndGates(int[][] rooms) {
        // Run BFS from the gates.
        int rows = rooms.length;
        int cols = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();

        // Step 1: Add all gates to the queue
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (rooms[row][col] == 0) {
                    queue.add(new int[]{row, col});
                }
            }
        }

        // Step 2: BFS from each gate
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int row = point[0];
            int col = point[1];

            // Explore neighbors
            for (int[] direction : directions) {
                int r = row + direction[0];
                int c = col + direction[1];

                // Skip out-of-bounds
                if (r < 0 || r >= rows || c < 0 || c >= cols) {
                    continue;
                }

                // A room is considered "visited" if its value has been updated to something other than INF.
                if (rooms[r][c] != INF) {
                    continue;
                }

                // Update distance and add neighbor room to the queue
                rooms[r][c] = rooms[row][col] + 1;
                queue.add(new int[]{r, c});
            }
        }
    }

    /**
     * Given a set of N people (numbered 1, 2, ..., N), we would like to split everyone into two groups of any size.
     * Each person may dislike some other people, and they should not go into the same group.
     * Formally, if dislikes[i] = [a, b],
     * it means it is not allowed to put the people numbered a and b into the same group.
     * Return true if and only if it is possible to split everyone into two groups in this way.
     * Input: N = 3, dislikes = [[1,2],[1,3],[2,3]]
     * Output: false
     *
     * @param N
     * @param dislikes
     * @return
     */
    public boolean possibleBiPartition(int N, int[][] dislikes) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] dislike : dislikes) {
            graph.putIfAbsent(dislike[0], new ArrayList<>());
            graph.get(dislike[0]).add(dislike[1]);
            graph.putIfAbsent(dislike[1], new ArrayList<>());
            graph.get(dislike[1]).add(dislike[0]);
        }
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
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
     * There are N network nodes, labelled 1 to N.
     * Given a list of travel times as directed edges times[i] = (u, v, w),
     * where u is the source node, v is the target node,
     * and w is the time it takes for a signal to travel from source to target.
     * We send a signal from a certain node source.
     * How long will it take for all nodes to receive the signal? If it is impossible, return -1.
     * times = [
     *          [1,2,1],
     *          [2,3,2],
     *          [1,3,4]
     *         ]
     * N = 3
     * source = 1
     * output: 3.
     *
     * @param times
     * @param N
     * @param source
     * @return
     */
    public int networkDelayTime(int[][] times, int N, int source) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] time : times) {
            graph.putIfAbsent(time[0], new HashMap<>());
            graph.get(time[0]).put(time[1], time[2]);
        }
        //We don't need the distance array separately as in BFSShortestPath.
        // We can make use of the minHeap to store the distance.
        Queue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        boolean[] visited = new boolean[N + 1];
        minHeap.add(new int[]{source, 0});
        int totalDistance = 0;

        while (!minHeap.isEmpty()) {
            int[] nodeDist = minHeap.remove();
            int node = nodeDist[0], distance = nodeDist[1];
            //Setting visited = true for this node before the while loop and
            // having a !visited condition for every neighbor doesn't work.

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
     * Given all the cities and flights,
     * together with starting city source and the destination dest,
     * your task is to find the cheapest price from source to dest with up to k stops.
     * If there is no such route, return -1.
     * N = 3, flights = [
     *                   [0,1,100],
     *                   [1,2,100],
     *                   [0,2,500]
     *                  ]
     * source = 0, dest = 2, K = 0
     * Output: 500
     *
     * @param N
     * @param flights
     * @param source
     * @param dest
     * @param K
     * @return
     */
    public int findCheapestPrice(int N, int[][] flights, int source, int dest, int K) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.putIfAbsent(flight[0], new HashMap<>());
            graph.get(flight[0]).put(flight[1], flight[2]);
        }

        Queue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        minHeap.add(new int[]{source, 0, K + 1});

        while (!minHeap.isEmpty()) {
            int[] nodeDist = minHeap.remove();
            int node = nodeDist[0], price = nodeDist[1], stops = nodeDist[2];
            // It fails when we include the visited logic. This solution doesn't need a visited array.

            if (node == dest)  //Found the destination.
                return price;

            if (stops > 0) {  //Checking if (stops < 0) and breaking the loop doesn't work.
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
     * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to],
     * reconstruct the itinerary in order. All the tickets belong to a man who departs from JFK.
     * Thus, the itinerary must begin with JFK.
     * Input: [
     *         ["JFK","SFO"],
     *         ["JFK","ATL"],
     *         ["SFO","ATL"],
     *         ["ATL","JFK"],
     *         ["ATL","SFO"]
     *        ]
     * Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
     *
     * @param tickets
     * @return
     */
    Map<String, Queue<String>> itineraryGraph = new HashMap<>();
    LinkedList<String> orderedItinerary = new LinkedList<>();
    public List<String> orderItinerary(List<List<String>> tickets) {
        for (List<String> ticket : tickets) {
            itineraryGraph.putIfAbsent(ticket.get(0), new PriorityQueue<>());
            itineraryGraph.get(ticket.get(0)).add(ticket.get(1));
        }

        dfsUtil("JFK");
        return orderedItinerary;
    }

    private void dfsUtil(String flight) {
        if (itineraryGraph.containsKey(flight)) {
            Queue<String> connectingFlights = itineraryGraph.get(flight);
            while (!connectingFlights.isEmpty()) {
                dfsUtil(connectingFlights.remove());
            }
        }
        orderedItinerary.addFirst(flight);
    }

    /**
     * In a given grid, each cell can have one of three values:

     * the value 0 representing an empty cell;
     * the value 1 representing a fresh orange;
     * the value 2 representing a rotten orange.
     * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.

     * Return the minimum number of minutes that must elapse until no cell has a fresh orange.
     * If this is impossible, return -1 instead.

     * Input:
     * [
     *   [2,1,1],
     *   [1,1,0],
     *   [0,1,1]
     * ]
     * Output: 4
     *
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        LinkedList<int[]> queue = new LinkedList<>();  //cell position
//      int minutes = 0;
// Keeping minutes 0, isUpdated boolean and having an if condition at the end of for loop didn't work.
//      boolean isUpdated = false;
        int freshOranges = 0;
        int rows = grid.length, cols = grid[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.add(new int[]{r, c});
                }
                else if (grid[r][c] == 1) {
                    freshOranges += 1;
                }
            }
        }

        if (freshOranges == 0)
            return 0;  //Required for a test case.

        int minutes = -1;
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        while (!queue.isEmpty()) {
            minutes += 1;
            for (int i = 0; i < queue.size(); i++) {
                int[] cellPosition = queue.remove();

                for (int[] dir : directions) {
                    int r = cellPosition[0] + dir[0];
                    int c = cellPosition[1] + dir[1];
                    int[] newCellPosition = new int[]{r, c};

                    if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == 2 || grid[r][c] == 0)
                        continue;

                    grid[r][c] = 2;
                    freshOranges -= 1;
//                    isUpdated = true;  //nope
                    queue.add(newCellPosition);
                }
//                if (isUpdated) {
// didn't work. Fails at test case [[1,2,1,1,2,1,1]] outputting 3 while the expected output is 2.
//                    minutes += 1;
//                    isUpdated = false;
//                }
            }
        }
        return freshOranges > 0 ? -1 : minutes;
    }

    public void alienDictionary() {
        String[] dictionary =  {"wrt", "wrp", "er", "ett", "rmtt"};
        Map<Character, List<Character>> graph = new HashMap<>();
        for (String word : dictionary) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new ArrayList<>());  //Create the graph and don't populate it yet.
            }
        }

        for (int i = 1; i < dictionary.length; i++) {
            String first = dictionary[i - 1];
            String second = dictionary[i];
            int length = Math.min(first.length(), second.length());

            for (int j = 0; j < length; j++) {
                char parent = first.charAt(j);
                char child = second.charAt(j);
                if (parent != child) {
                    if (!graph.get(parent).contains(child)) {
                        graph.get(parent).add(child);
                    }
                    break;
                }
            }
        }
    }
}
