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
        if (node == null) {
            System.out.println("here--");
            return;
        }
        node.visited = true;
        System.out.println(node.data);
        for (GraphNode n : node.adjacentNodes) {
            if (n.visited == false) {
                DFS(n);
            }
        }
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
                if (visited[neighbor.data] == false) {
                    queue.add(neighbor);
                    visited[neighbor.data] = true;
                }
            }
        }
    }
}
