package com.Algorithms;

import java.util.*;

class GraphNode {
    int data;
    boolean visited;
    LinkedList<GraphNode> adjacentNodes = new LinkedList<>();

    public GraphNode(int data) {
        this.data = data;
    }
}

class Edge {
    GraphNode source;
    GraphNode destination;
    int weight;

    public Edge(GraphNode source, GraphNode destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class Graph {
    Set<GraphNode> nodes;
    Set<Edge> edges;
    Map<GraphNode, List<Edge>> adjList;

    public Graph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
        adjList = new HashMap<>();
    }

    void addNode(int newdata) {
        GraphNode newNode = new GraphNode(newdata);
        nodes.add(newNode);
    }

    void removeNode(int removedata) {
        GraphNode removeNode = new GraphNode(removedata);
        nodes.remove(removeNode);
    }

    void addEdge(int source, int destination, int weight) {
        Edge newEdge = new Edge(new GraphNode(source), new GraphNode(destination), weight);
        edges.add(newEdge);
        List<Edge> l = adjList.get(source);
        l.add(newEdge);
        adjList.put(new GraphNode(source), l);
    }

    void DFS(GraphNode node) {
        if (node == null)
            return;
        node.visited = true;
        System.out.println(node.data);
        for (GraphNode n : node.adjacentNodes) {
            if (!n.visited) {
                DFS(n);
            }
        }
    }

    boolean DFScycle(GraphNode node, boolean[] recStack) {
        node.visited = true;
        System.out.println(node.data);
        recStack[node.data] = true;
        for (GraphNode n : node.adjacentNodes) {
            if (!n.visited) {
                DFScycle(n, recStack);
            } else if (n.visited && recStack[n.data])
                return true;
        }
        recStack[node.data] = false;
        return false;
    }

    void BFS(GraphNode node) {
        LinkedList<GraphNode> queue = new LinkedList<>();
        boolean[] visited = new boolean[nodes.size()];
        queue.add(node);
        visited[node.data] = true;
        while (!queue.isEmpty()) {
            GraphNode gn = queue.remove();
            System.out.println(gn.data);
            for (GraphNode neighbor : gn.adjacentNodes) {
                if (!visited[neighbor.data]) {
                    queue.add(neighbor);
                    visited[neighbor.data] = true;
                }
            }
        }
    }

    boolean Bipartite(GraphNode node) {
        LinkedList<GraphNode> queue = new LinkedList<>();
        boolean[] color = new boolean[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        queue.add(node);
        color[node.data] = true;
        while (!queue.isEmpty()) {
            GraphNode gn = queue.remove();
            visited[gn.data] = true;
            for (GraphNode neighbor : gn.adjacentNodes) {
                if (!visited[neighbor.data]) {
                    queue.add(neighbor);
                    visited[neighbor.data] = true;
                    color[neighbor.data] = !color[gn.data];
                }
                if (color[neighbor.data] == color[gn.data]) return false;
            }
        }
        return true;
    }
}
