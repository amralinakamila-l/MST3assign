package org.example.algorithms;

import org.example.model.*;
import java.util.*;

public class PrimMST {

    public static AlgorithmResult run(GraphData graph) {
        long start = System.nanoTime();
        int operations = 0;

        Map<String, List<EdgeData>> adj = new HashMap<>();
        for (EdgeData e : graph.edges) {
            adj.computeIfAbsent(e.from, k -> new ArrayList<>()).add(e);
            adj.computeIfAbsent(e.to, k -> new ArrayList<>()).add(new EdgeData(e.to, e.from, e.weight));
        }

        Set<String> visited = new HashSet<>();
        List<EdgeData> mstEdges = new ArrayList<>();
        PriorityQueue<EdgeData> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));

        String startNode = graph.nodes.get(0);
        visited.add(startNode);
        pq.addAll(adj.getOrDefault(startNode, new ArrayList<>()));

        double totalCost = 0;

        while (!pq.isEmpty() && mstEdges.size() < graph.nodes.size() - 1) {
            EdgeData edge = pq.poll();
            operations++;
            if (visited.contains(edge.to)) continue;

            visited.add(edge.to);
            mstEdges.add(edge);
            totalCost += edge.weight;

            for (EdgeData next : adj.getOrDefault(edge.to, new ArrayList<>())) {
                if (!visited.contains(next.to)) pq.add(next);
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
}
