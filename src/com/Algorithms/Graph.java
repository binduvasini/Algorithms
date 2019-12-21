package com.Algorithms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        List<Edge> l;
        if (adjListofEdges.get(source) != null)
            l = adjListofEdges.get(source);
        else
            l = new LinkedList<>();
        l.add(newEdge);
        adjListofEdges.put(sourceNode, l);
        adjListofNodes.computeIfAbsent(sourceNode, k -> new LinkedList<>()).add(destNode);
    }

    void DFS(int nodeData) {
        GraphNode node = getNode(nodeData);
        if (node == null)
            return;
        node.visited = true;
        System.out.println(node.data);
        if (adjListofNodes.containsKey(node)) {
            adjListofNodes.forEach((graphNode, graphNodes) -> {
                if (!graphNode.visited){
                    DFS(graphNode.data);
                }
            });
        }
    }

    boolean hasCycle(int nodeData, ArrayDeque<Integer> visiting) {
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
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        HashSet<Integer> beingVisited = new HashSet<>();
        for (GraphNode graphNode : nodes) {
            if (!graphNode.visited && !hasNoCycleUtil(graphNode, stack, beingVisited))
                return new Integer[0];
        }
        return (Integer[]) stack.toArray();
    }

    private boolean hasNoCycleUtil(GraphNode node, ArrayDeque<Integer> stack, HashSet<Integer> beingVisited) {
        node.visited = true;
        beingVisited.add(node.data);
        if (adjListofNodes.containsKey(node)) {
            for(GraphNode graphNode : adjListofNodes.get(node)){
                if (!graphNode.visited && !hasNoCycleUtil(graphNode, stack, beingVisited)) {
                    return false;
                }
                else if (beingVisited.contains(graphNode.data)){
                    return false;
                }
            }
        }
        beingVisited.remove(node.data);
        stack.push(node.data);
        return true;
    }

    void BFS(int nodeData) {
        GraphNode node = getNode(nodeData);
        LinkedList<GraphNode> queue = new LinkedList<>();
        boolean[] visited = new boolean[nodes.size()];
        queue.add(node);
        visited[node.data] = true;
        while (!queue.isEmpty()) {
            GraphNode gn = queue.remove();
            System.out.println(gn.data);
            if (adjListofNodes.containsKey(gn)) {
                adjListofNodes.forEach((graphNode, graphNodes) -> {
                    if (!visited[graphNode.data]){
                        queue.add(graphNode);
                        visited[graphNode.data] = true;
                    }
                });
            }
        }
    }

    boolean Bipartite(int nodeData) {
        GraphNode node = getNode(nodeData);
        LinkedList<GraphNode> queue = new LinkedList<>();
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
}
