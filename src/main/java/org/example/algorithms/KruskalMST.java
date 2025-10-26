package org.example.algorithms;

import org.example.graph.Graph;
import org.example.graph.Edge;
import org.example.model.*;
import java.util.*;

public class KruskalMST {

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }

    public static AlgorithmResult run(GraphData graph) {
        long start = System.nanoTime();
        int operations = 0;

        List<EdgeData> edges = new ArrayList<>(graph.edges);
        edges.sort(Comparator.comparingDouble(e -> e.weight));
        operations += edges.size();

        UnionFindOld uf = new UnionFindOld();
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

    public static MSTResult findMST(Graph graph) {
        int operations = 0;
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        edges.sort(Comparator.comparingDouble(Edge::getWeight));
        operations += edges.size();

        UnionFind uf = new UnionFind(graph.getVerticesCount());

        for (Edge edge : edges) {
            operations++;
            int rootFrom = uf.find(edge.getSource());
            int rootTo = uf.find(edge.getDestination());

            if (rootFrom != rootTo) {
                uf.union(edge.getSource(), edge.getDestination());
                mstEdges.add(edge);
                totalCost += edge.getWeight();

                if (mstEdges.size() == graph.getVerticesCount() - 1) {
                    break;
                }
            }
        }

        return new MSTResult(totalCost, operations, mstEdges);
    }

    static class UnionFindOld {
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
}
