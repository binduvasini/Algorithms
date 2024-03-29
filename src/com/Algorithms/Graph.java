package com.Algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {

    static class GraphNode {
        int data;
        boolean visited;

        public GraphNode(int data) {
            this.data = data;
        }
    }

    static class Edge {
        GraphNode source;
        GraphNode destination;
        int weight;

        public Edge(GraphNode source, GraphNode destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    Set<GraphNode> nodes;
    Set<Edge> edges;
    Map<GraphNode, List<Edge>> adjListofEdges;
    Map<GraphNode, List<GraphNode>> adjListofNodes;

    public Graph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
        adjListofEdges = new HashMap<>();
        adjListofNodes = new HashMap<>();
    }

    void addNode(int newdata) {
        GraphNode newNode = new GraphNode(newdata);
        nodes.add(newNode);
    }

    void removeNode(int removedata) {
        GraphNode removeNode = getNode(removedata);
        nodes.remove(removeNode);
    }

    GraphNode getNode(int nodeData) {
        for (GraphNode node : nodes) {
            if (node.data == nodeData)
                return node;
        }
        return null;
    }

    void addEdge(int source, int destination, int weight) {
        GraphNode sourceNode = getNode(source);
        GraphNode destNode = getNode(destination);
        Edge newEdge = new Edge(sourceNode, destNode, weight);
        edges.add(newEdge);
        List<Edge> edgeList;
        if (adjListofEdges.get(sourceNode) != null)
            edgeList = adjListofEdges.get(sourceNode);
        else
            edgeList = new LinkedList<>();
        edgeList.add(newEdge);
        adjListofEdges.put(sourceNode, edgeList);
        adjListofNodes.computeIfAbsent(sourceNode, k -> new LinkedList<>()).add(destNode);
    }

    void DFS(int nodeData) {
        GraphNode node = getNode(nodeData);
        if (node == null)
            return;
        node.visited = true;
        System.out.println(node.data);
        if (adjListofNodes.containsKey(node)) {
            adjListofNodes.forEach((graphNode, graphNodes) -> {  //For each neighbor,
                if (!graphNode.visited) {
                    DFS(graphNode.data);
                }
            });
        }
    }

    boolean hasCycle(int nodeData, HashSet<Integer> visiting) {
        GraphNode node = getNode(nodeData);
        node.visited = true;
        System.out.println(node.data);
        visiting.add(node.data);
        if (adjListofNodes.containsKey(node)) {
            for (GraphNode n : adjListofNodes.get(node)) {
                if (!n.visited && hasCycle(n.data, visiting)) {
                    return true;
                } else if (visiting.contains(n.data))
                    return true;
            }
        }
        visiting.remove(node.data);
        return false;
    }

    Integer[] topologicalSort(int nodeData) {
        GraphNode node = getNode(nodeData);
        if (node == null)
            return new Integer[0];
        ArrayDeque<Integer> order = new ArrayDeque<>();
        //To store the sorted elements.
        for (GraphNode graphNode : nodes) {
            if (!graphNode.visited && hasCycle(graphNode, order, new HashSet<>()))
                return new Integer[0];  //return an empty array because there is a cycle.
        }
        return (Integer[]) order.toArray();
    }

    //This is detecting a cycle as well as sorting topologically
    private boolean hasCycle(GraphNode node, ArrayDeque<Integer> order, HashSet<Integer> visiting) {
        node.visited = true;
        visiting.add(node.data);  //to detect a cycle
        if (adjListofNodes.containsKey(node)) {
            for (GraphNode graphNode : adjListofNodes.get(node)) {  //For each neighbor
                if (!graphNode.visited && hasCycle(graphNode, order, visiting)) {
                    return true;
                } else if (visiting.contains(graphNode.data)) {
                    return true;
                }
            }
        }
        visiting.remove(node.data);
        order.push(node.data);
        return false;
    }

    int connectedComponents() {
        int count = 0;
        for (GraphNode graphNode : nodes) {
            if (!graphNode.visited) {
                DFS(graphNode.data);
                count += 1;
            }
        }
        return count;
    }

    void BFS(int nodeData) {
        GraphNode node = getNode(nodeData);
        Queue<GraphNode> queue = new LinkedList<>();
        boolean[] visited = new boolean[nodes.size()];
        queue.add(node);
        visited[node.data] = true;
        while (!queue.isEmpty()) {
            GraphNode gn = queue.remove();
            System.out.println(gn.data);
            if (adjListofNodes.containsKey(gn)) {
                adjListofNodes.forEach((graphNode, graphNodes) -> {
                    if (!visited[graphNode.data]) {
                        queue.add(graphNode);
                        visited[graphNode.data] = true;
                    }
                });
            }
        }
    }

    boolean Bipartite(int nodeData) {
        GraphNode node = getNode(nodeData);
        Queue<GraphNode> queue = new LinkedList<>();
        boolean[] color = new boolean[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        queue.add(node);
        color[node.data] = true;
        while (!queue.isEmpty()) {
            GraphNode gn = queue.remove();
            visited[gn.data] = true;
            if (adjListofNodes.containsKey(gn)) {
                for (GraphNode neighbor : adjListofNodes.get(gn)) {
                    if (!visited[neighbor.data]) {
                        queue.add(neighbor);
                        visited[neighbor.data] = true;
                        color[neighbor.data] = !color[gn.data];
                    }
                    if (color[neighbor.data] == color[gn.data]) return false;
                }
            }
        }
        return true;
    }

    void weightedGraph() {
        String[][] array = {{"JFK", "SFO"}, {"JFK", "ATL"}};
        int[] weights = {5, 9};
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            graph.putIfAbsent(array[i][0], new HashMap<>());
            graph.get(array[i][0]).put(array[i][1], weights[i]);
        }
    }

    double dfsUtilToEvaluateEquation(
            String source,
            String dest,
            HashMap<String, Map<String, Double>> graph,
            HashSet<String> set,
            double val
    ) {

        if (set.contains(source) || !graph.containsKey(source))
            return -1.0;

        if (source.equals(dest))
            return val;

        set.add(source);

        Map<String, Double> neighbors = graph.get(source);
        for (String neighbor : neighbors.keySet()) {
            double result = dfsUtilToEvaluateEquation(neighbor, dest, graph, set, val * neighbors.get(neighbor));

            if (result != -1.0)
                return result;
        }
        set.remove(source);
        return -1.0;
    }
}
