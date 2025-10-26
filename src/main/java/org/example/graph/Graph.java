package org.example.graph;

import java.util.*;

public class Graph {
    private final int verticesCount;
    private final List<Edge> edges;
    private final List<List<Edge>> adjacencyList;
    private final int graphId;
    private final Map<String, Integer> nodeToIndex; // Соответствие имён вершин индексам

    public Graph(int verticesCount, int graphId, List<String> nodeNames) {
        this.verticesCount = verticesCount;
        this.graphId = graphId;
        this.edges = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
        this.nodeToIndex = new HashMap<>();

        for (int i = 0; i < nodeNames.size(); i++) {
            nodeToIndex.put(nodeNames.get(i), i);
        }

        for (int i = 0; i < verticesCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(String from, String to, double weight) {
        Integer fromIndex = nodeToIndex.get(from);
        Integer toIndex = nodeToIndex.get(to);

        if (fromIndex == null || toIndex == null) {
            throw new IllegalArgumentException("Unknown node name: " + from + " or " + to);
        }

        addEdge(fromIndex, toIndex, weight);
    }


    public void addEdge(int source, int destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);

        adjacencyList.get(source).add(edge);
        adjacencyList.get(destination).add(new Edge(destination, source, weight));
    }


    public int getVerticesCount() {
        return verticesCount;
    }

    public int getGraphId() {
        return graphId;
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public String getNodeName(int index) {
        for (Map.Entry<String, Integer> entry : nodeToIndex.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return "Node_" + index;
    }

    public int getNodeIndex(String name) {
        return nodeToIndex.getOrDefault(name, -1);
    }

    public List<Edge> getAdjacentEdges(int vertex) {
        return new ArrayList<>(adjacencyList.get(vertex));
    }


    public List<Edge> getSortedEdges() {
        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges);
        return sortedEdges;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph ").append(graphId).append(": ")
                .append(verticesCount).append(" vertices, ")
                .append(edges.size()).append(" edges\n");

        for (Edge edge : edges) {
            String fromName = getNodeName(edge.getSource());
            String toName = getNodeName(edge.getDestination());
            sb.append("  ").append(fromName).append(" - ").append(toName)
                    .append(" (").append(edge.getWeight()).append(")\n");
        }

        return sb.toString();
    }
}
