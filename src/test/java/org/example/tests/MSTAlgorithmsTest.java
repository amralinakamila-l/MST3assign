package org.example.tests;

import org.example.algorithms.KruskalMST;
import org.example.algorithms.PrimMST;
import org.example.model.AlgorithmResult;
import org.example.model.GraphData;
import org.example.model.EdgeData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MSTAlgorithmsTest {


    private GraphData createBasicConnectedGraph() {
        List<EdgeData> edges = Arrays.asList(
                new EdgeData("0", "1", 4),
                new EdgeData("0", "2", 3),
                new EdgeData("1", "2", 1),
                new EdgeData("1", "3", 2),
                new EdgeData("2", "3", 5),
                new EdgeData("2", "4", 6),
                new EdgeData("3", "4", 7)
        );

        GraphData graph = new GraphData();
        graph.id = 1;
        graph.nodes = Arrays.asList("0", "1", "2", "3", "4");
        graph.edges_count = edges.size();
        graph.edges = edges;

        return graph;
    }

    private GraphData createDisconnectedGraph() {
        List<EdgeData> edges = Arrays.asList(
                new EdgeData("0", "1", 1),
                new EdgeData("2", "3", 2)
        );

        GraphData graph = new GraphData();
        graph.id = 2;
        graph.nodes = Arrays.asList("0", "1", "2", "3");
        graph.edges_count = edges.size();
        graph.edges = edges;

        return graph;
    }

    private GraphData createSingleVertexGraph() {
        GraphData graph = new GraphData();
        graph.id = 3;
        graph.nodes = Arrays.asList("0");
        graph.edges_count = 0;
        graph.edges = Arrays.asList();

        return graph;
    }

    private GraphData createCycleGraph() {
        List<EdgeData> edges = Arrays.asList(
                new EdgeData("0", "1", 1),
                new EdgeData("1", "2", 2),
                new EdgeData("2", "0", 3)
        );

        GraphData graph = new GraphData();
        graph.id = 4;
        graph.nodes = Arrays.asList("0", "1", "2");
        graph.edges_count = edges.size();
        graph.edges = edges;

        return graph;
    }

    private GraphData createComplexGraph() {
        List<EdgeData> edges = Arrays.asList(
                new EdgeData("0", "1", 2),
                new EdgeData("0", "3", 6),
                new EdgeData("1", "2", 3),
                new EdgeData("1", "3", 8),
                new EdgeData("1", "4", 5),
                new EdgeData("2", "4", 7),
                new EdgeData("3", "4", 9)
        );

        GraphData graph = new GraphData();
        graph.id = 5;
        graph.nodes = Arrays.asList("0", "1", "2", "3", "4");
        graph.edges_count = edges.size();
        graph.edges = edges;

        return graph;
    }


    @Test
    void testMSTTotalCostIdentical() {
        GraphData graph = createBasicConnectedGraph();


        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertEquals(kruskalResult.total_cost, primResult.total_cost,
                "Total MST cost should be identical for both algorithms");
    }

    @Test
    void testMSTEdgeCount() {
        GraphData graph = createBasicConnectedGraph();
        int expectedEdgeCount = graph.nodes.size() - 1;

        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertEquals(expectedEdgeCount, kruskalResult.mst_edges.size(),
                "Kruskal MST should have V-1 edges");
        assertEquals(expectedEdgeCount, primResult.mst_edges.size(),
                "Prim MST should have V-1 edges");
    }

    @Test
    void testMSTAcyclic() {
        GraphData graph = createBasicConnectedGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertFalse(hasCycles(kruskalResult, graph.nodes),
                "Kruskal MST should be acyclic");
        assertFalse(hasCycles(primResult, graph.nodes),
                "Prim MST should be acyclic");
    }

    @Test
    void testMSTConnectsAllVertices() {
        GraphData graph = createBasicConnectedGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertTrue(connectsAllVertices(kruskalResult, graph.nodes),
                "Kruskal MST should connect all vertices");
        assertTrue(connectsAllVertices(primResult, graph.nodes),
                "Prim MST should connect all vertices");
    }

    @Test
    void testExecutionTimeNonNegative() {
        GraphData graph = createBasicConnectedGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertTrue(kruskalResult.execution_time_ms >= 0,
                "Kruskal execution time should be non-negative");
        assertTrue(primResult.execution_time_ms >= 0,
                "Prim execution time should be non-negative");
    }

    @Test
    void testOperationsCountNonNegative() {
        GraphData graph = createBasicConnectedGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(graph);
        AlgorithmResult primResult = PrimMST.run(graph);

        assertTrue(kruskalResult.operations_count >= 0,
                "Kruskal operations count should be non-negative");
        assertTrue(primResult.operations_count >= 0,
                "Prim operations count should be non-negative");
    }

    @Test
    void testDisconnectedGraphHandling() {
        GraphData disconnectedGraph = createDisconnectedGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(disconnectedGraph);
        AlgorithmResult primResult = PrimMST.run(disconnectedGraph);

        assertTrue(kruskalResult.mst_edges.size() < disconnectedGraph.nodes.size() - 1,
                "Kruskal should handle disconnected graphs gracefully");
        assertTrue(primResult.mst_edges.size() < disconnectedGraph.nodes.size() - 1,
                "Prim should handle disconnected graphs gracefully");
    }

    @Test
    void testSingleVertexGraph() {
        GraphData singleVertexGraph = createSingleVertexGraph();

        AlgorithmResult kruskalResult = KruskalMST.run(singleVertexGraph);
        AlgorithmResult primResult = PrimMST.run(singleVertexGraph);

        assertEquals(0, kruskalResult.mst_edges.size());
        assertEquals(0.0, kruskalResult.total_cost, 0.001);
        assertEquals(0, primResult.mst_edges.size());
        assertEquals(0.0, primResult.total_cost, 0.001);
    }



    private boolean hasCycles(AlgorithmResult result, List<String> nodes) {
        if (result == null || result.mst_edges.isEmpty())
            return false;

        Map<String, String> parent = new HashMap<>();
        for (String node : nodes) {
            parent.put(node, node);
        }

        for (EdgeData edge : result.mst_edges) {
            String rootU = find(parent, edge.from);
            String rootV = find(parent, edge.to);

            if (rootU.equals(rootV))
                return true;

            parent.put(rootU, rootV);
        }

        return false;
    }

    private boolean connectsAllVertices(AlgorithmResult result, List<String> nodes) {
        if (result == null)
            return nodes.isEmpty();
        if (result.mst_edges.isEmpty())
            return nodes.size() <= 1;

        Map<String, String> parent = new HashMap<>();
        for (String node : nodes) {
            parent.put(node, node);
        }

        for (EdgeData edge : result.mst_edges) {
            String rootU = find(parent, edge.from);
            String rootV = find(parent, edge.to);
            if (!rootU.equals(rootV)) {
                parent.put(rootU, rootV);
            }
        }

        String firstRoot = find(parent, nodes.get(0));
        for (int i = 1; i < nodes.size(); i++) {
            if (!find(parent, nodes.get(i)).equals(firstRoot))
                return false;
        }

        return true;
    }

    private String find(Map<String, String> parent, String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent, parent.get(x)));
        }
        return parent.get(x);
    }
}