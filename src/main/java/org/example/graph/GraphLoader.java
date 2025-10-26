package org.example.graph;

import java.util.*;

public class GraphLoader {

    public static Graph createGraph1() {
        List<String> nodes = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R"
        );

        Graph graph = new Graph(nodes.size(), 1, nodes);

        graph.addEdge("B", "D", 30.0);
        graph.addEdge("L", "G", 17.0);
        graph.addEdge("Q", "O", 55.0);
        graph.addEdge("I", "D", 76.0);
        graph.addEdge("J", "G", 18.0);
        graph.addEdge("R", "P", 54.0);
        graph.addEdge("C", "O", 58.0);
        graph.addEdge("O", "K", 16.0);
        graph.addEdge("O", "E", 73.0);
        graph.addEdge("H", "F", 72.0);
        graph.addEdge("I", "P", 68.0);
        graph.addEdge("M", "R", 50.0);
        graph.addEdge("H", "K", 71.0);
        graph.addEdge("C", "I", 3.0);
        graph.addEdge("I", "Q", 97.0);
        graph.addEdge("L", "P", 82.0);
        graph.addEdge("D", "G", 46.0);
        graph.addEdge("F", "A", 68.0);
        graph.addEdge("L", "Q", 38.0);
        graph.addEdge("H", "J", 28.0);
        graph.addEdge("N", "B", 74.0);
        graph.addEdge("G", "R", 33.0);
        graph.addEdge("E", "C", 53.0);
        graph.addEdge("Q", "J", 77.0);
        graph.addEdge("P", "B", 14.0);
        graph.addEdge("C", "B", 94.0);
        graph.addEdge("B", "O", 86.0);
        graph.addEdge("N", "L", 100.0);
        graph.addEdge("H", "Q", 42.0);
        graph.addEdge("D", "R", 14.0);
        graph.addEdge("G", "Q", 2.0);
        graph.addEdge("F", "K", 82.0);
        graph.addEdge("D", "J", 61.0);
        graph.addEdge("C", "L", 35.0);
        graph.addEdge("M", "D", 100.0);
        graph.addEdge("C", "N", 78.0);

        return graph;
    }

    public static Graph createGraph2() {
        List<String> nodes = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I"
        );

        Graph graph = new Graph(nodes.size(), 2, nodes);

        graph.addEdge("A", "F", 25.0);
        graph.addEdge("G", "B", 44.0);
        graph.addEdge("B", "F", 78.0);
        graph.addEdge("H", "G", 77.0);
        graph.addEdge("A", "C", 100.0);
        graph.addEdge("B", "H", 42.0);
        graph.addEdge("A", "B", 89.0);
        graph.addEdge("G", "A", 61.0);
        graph.addEdge("F", "C", 97.0);
        graph.addEdge("A", "I", 45.0);
        graph.addEdge("A", "D", 84.0);
        graph.addEdge("B", "C", 56.0);
        graph.addEdge("G", "E", 73.0);
        graph.addEdge("H", "C", 40.0);
        graph.addEdge("F", "H", 29.0);
        graph.addEdge("G", "F", 29.0);
        graph.addEdge("H", "E", 9.0);
        graph.addEdge("D", "C", 60.0);

        return graph;
    }

    public static Graph createGraph3() {
        List<String> nodes = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G"
        );

        Graph graph = new Graph(nodes.size(), 3, nodes);

        graph.addEdge("D", "B", 7.0);
        graph.addEdge("D", "C", 82.0);
        graph.addEdge("E", "B", 60.0);
        graph.addEdge("G", "C", 60.0);
        graph.addEdge("F", "D", 12.0);
        graph.addEdge("F", "B", 2.0);
        graph.addEdge("F", "C", 100.0);
        graph.addEdge("D", "E", 99.0);
        graph.addEdge("A", "F", 28.0);
        graph.addEdge("B", "C", 13.0);
        graph.addEdge("E", "A", 5.0);
        graph.addEdge("G", "B", 55.0);
        graph.addEdge("G", "F", 33.0);
        graph.addEdge("E", "G", 82.0);

        return graph;
    }

    public static Graph createGraph4() {
        List<String> nodes = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"
        );

        Graph graph = new Graph(nodes.size(), 4, nodes);

        graph.addEdge("J", "E", 27.0);
        graph.addEdge("J", "F", 18.0);
        graph.addEdge("G", "M", 91.0);
        graph.addEdge("K", "F", 96.0);
        graph.addEdge("H", "D", 46.0);
        graph.addEdge("K", "G", 3.0);
        graph.addEdge("M", "D", 50.0);
        graph.addEdge("L", "K", 21.0);
        graph.addEdge("I", "L", 56.0);
        graph.addEdge("H", "F", 96.0);
        graph.addEdge("I", "K", 47.0);
        graph.addEdge("D", "F", 31.0);
        graph.addEdge("E", "I", 7.0);
        graph.addEdge("M", "E", 44.0);
        graph.addEdge("K", "D", 51.0);
        graph.addEdge("L", "J", 76.0);
        graph.addEdge("L", "B", 87.0);
        graph.addEdge("A", "J", 78.0);
        graph.addEdge("B", "I", 73.0);
        graph.addEdge("F", "I", 34.0);
        graph.addEdge("M", "C", 73.0);
        graph.addEdge("C", "I", 97.0);
        graph.addEdge("L", "F", 26.0);
        graph.addEdge("D", "J", 48.0);
        graph.addEdge("K", "H", 69.0);
        graph.addEdge("C", "H", 67.0);

        return graph;
    }

    public static Graph createGraph5() {
        List<String> nodes = Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "AA", "AB", "AC"
        );

        Graph graph = new Graph(nodes.size(), 5, nodes);

        graph.addEdge("T", "R", 95.0);
        graph.addEdge("Y", "J", 63.0);
        graph.addEdge("E", "J", 3.0);
        graph.addEdge("Z", "A", 89.0);
        graph.addEdge("A", "F", 38.0);
        graph.addEdge("J", "AB", 85.0);
        graph.addEdge("AC", "N", 49.0);
        graph.addEdge("S", "N", 27.0);
        graph.addEdge("AA", "P", 87.0);
        graph.addEdge("N", "H", 69.0);
        graph.addEdge("AC", "K", 74.0);
        graph.addEdge("Q", "AC", 51.0);
        graph.addEdge("A", "AB", 86.0);
        graph.addEdge("M", "B", 100.0);
        graph.addEdge("T", "L", 83.0);
        graph.addEdge("P", "X", 59.0);
        graph.addEdge("P", "F", 61.0);
        graph.addEdge("Z", "AA", 94.0);
        graph.addEdge("T", "B", 18.0);
        graph.addEdge("X", "B", 96.0);

        return graph;
    }

    public static List<Graph> getAllExperimentalGraphs() {
        List<Graph> graphs = new ArrayList<>();
        graphs.add(createGraph1());
        graphs.add(createGraph2());
        graphs.add(createGraph3());
        graphs.add(createGraph4());
        graphs.add(createGraph5());
        return graphs;
    }
}

