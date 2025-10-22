package org.example.model;

import java.util.List;

public class Graph {
    public int id;
    public List<String> nodes;
    public List<Edge> edges;

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }
}
