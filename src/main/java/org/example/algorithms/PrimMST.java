package org.example.algorithms;

import org.example.graph.Graph;
import org.example.graph.Edge;
import org.example.model.*;
import java.util.*;

public class PrimMST {


    public static AlgorithmResult run(GraphData graph) {
        long start = System.nanoTime();
        int operations = 0;

        if (graph.nodes == null || graph.nodes.isEmpty()) {
            long end = System.nanoTime();
            return new AlgorithmResult(new ArrayList<>(), 0, 0, (end - start) / 1_000_000.0);
        }

        Map<String, List<EdgeData>> adj = new HashMap<>();

        for (EdgeData e : graph.edges) {
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(
                    new EdgeData(e.to, e.from, e.weight)
            );
        }

        Set<String> visited = new HashSet<>();
        List<EdgeData> mstEdges = new ArrayList<>();
        PriorityQueue<EdgeData> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));

        double totalCost = 0;

        for (String node : graph.nodes) {
            if (visited.contains(node)) continue;

            visited.add(node);
            List<EdgeData> edgesFromNode = adj.get(node);
            if (edgesFromNode != null) {
                pq.addAll(edgesFromNode);
            }

            while (!pq.isEmpty() && mstEdges.size() < graph.nodes.size() - 1) {
                EdgeData edge = pq.poll();
                operations++;

                if (visited.contains(edge.to)) continue;

                visited.add(edge.to);
                mstEdges.add(edge);
                totalCost += edge.weight;

                List<EdgeData> edgesFromNewNode = adj.get(edge.to);
                if (edgesFromNewNode != null) {
                    for (EdgeData next : edgesFromNewNode) {
                        if (!visited.contains(next.to)) {
                            pq.add(next);
                        }
                    }
                }
            }
        }

        long end = System.nanoTime();

        AlgorithmResult result = new AlgorithmResult();
        result.mst_edges = mstEdges;
        result.total_cost = totalCost;
        result.operations_count = operations;
        result.execution_time_ms = (end - start) / 1_000_000.0;
        return result;
    }

    public static MSTResult findMST(Graph graph) {
        int operations = 0;
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        int vertices = graph.getVerticesCount();
        if (vertices == 0) {
            return new MSTResult(0, 0, mstEdges);
        }

        boolean[] visited = new boolean[vertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));

        visited[0] = true;
        for (Edge edge : graph.getAdjacentEdges(0)) {
            pq.add(edge);
        }

        while (!pq.isEmpty() && mstEdges.size() < vertices - 1) {
            Edge edge = pq.poll();
            operations++;

            int u = edge.getSource();
            int v = edge.getDestination();

            if (visited[u] && visited[v]) {
                continue;
            }

            mstEdges.add(edge);
            totalCost += edge.getWeight();

            int newVertex = visited[u] ? v : u;
            visited[newVertex] = true;

            for (Edge nextEdge : graph.getAdjacentEdges(newVertex)) {
                int nextV = nextEdge.getDestination();
                if (!visited[nextV]) {
                    pq.add(nextEdge);
                }
            }
        }

        return new MSTResult(totalCost, operations, mstEdges);
    }
}
