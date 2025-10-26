package org.example.algorithms;

import org.example.model.*;
import java.util.*;

public class KruskalMST {

    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();

        void makeSet(Collection<String> nodes) {
            for (String node : nodes) parent.put(node, node);
        }

        String find(String node) {
            if (!parent.get(node).equals(node))
                parent.put(node, find(parent.get(node)));
            return parent.get(node);
        }

        void union(String a, String b) {
            parent.put(find(a), find(b));
        }
    }

    public static AlgorithmResult run(GraphData graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<EdgeData> edges = new ArrayList<>(graph.edges);
        edges.sort(Comparator.comparingDouble(e -> e.weight));
        operations += edges.size();

        UnionFind uf = new UnionFind();
        uf.makeSet(graph.nodes);

        List<EdgeData> mstEdges = new ArrayList<>();
        double totalCost = 0;

        for (EdgeData e : edges) {
            operations++;
            if (!uf.find(e.from).equals(uf.find(e.to))) {
                uf.union(e.from, e.to);
                mstEdges.add(e);
                totalCost += e.weight;
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
