package org.example.util;

import com.google.gson.GsonBuilder;
import org.example.model.EdgeData;
import org.example.model.GraphData;
import org.example.model.InputData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {

    public static void main(String[] args) {
        Random rand = new Random();
        List<GraphData> graphs = new ArrayList<>();

        // Generate graphs by category
        generateGraphs(graphs, 5, 5, 30, rand);       // small
        generateGraphs(graphs, 10, 30, 300, rand);    // medium
        generateGraphs(graphs, 10, 300, 1000, rand);  // large
        generateGraphs(graphs, 3, 1000, 2000, rand);  // extra large

        InputData inputData = new InputData();
        inputData.graphs = graphs;

        try (FileWriter writer = new FileWriter("data/input.json")) {
            new GsonBuilder().setPrettyPrinting().create().toJson(inputData, writer);
            System.out.println("✅ Input file generated successfully!");
            System.out.println("📊 Total graphs: " + graphs.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateGraphs(List<GraphData> graphs, int count, int minV, int maxV, Random rand) {
        for (int i = 0; i < count; i++) {
            GraphData g = new GraphData();
            g.id = graphs.size() + 1;

            int vertexCount = rand.nextInt(maxV - minV + 1) + minV;
            g.nodes = generateLetterNodes(vertexCount);

            // Формируем список рёбер
            Set<String> uniqueEdges = new HashSet<>();
            g.edges = new ArrayList<>();

            // Количество рёбер ограничиваем (до ~2x вершин)
            int edgeCount = Math.min(vertexCount * 2, 1000 + vertexCount / 2);

            while (g.edges.size() < edgeCount) {
                String from = g.nodes.get(rand.nextInt(vertexCount));
                String to = g.nodes.get(rand.nextInt(vertexCount));

                if (!from.equals(to)) {
                    String edgeKey = from + "-" + to;
                    String reverseKey = to + "-" + from;

                    if (!uniqueEdges.contains(edgeKey) && !uniqueEdges.contains(reverseKey)) {
                        uniqueEdges.add(edgeKey);
                        double weight = 1 + rand.nextInt(100);
                        g.edges.add(new EdgeData(from, to, weight));
                    }
                }
            }

            g.edges_count = g.edges.size(); // ✅ добавляем поле, как в твоём старом JSON
            graphs.add(g);
        }
    }

    // Генератор буквенных имён узлов (A, B, C ... Z, AA, AB ...)
    private static List<String> generateLetterNodes(int count) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            nodes.add(numberToLetters(i));
        }
        return nodes;
    }

    private static String numberToLetters(int num) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, (char) ('A' + (num % 26)));
            num = num / 26 - 1;
        } while (num >= 0);
        return sb.toString();
    }
}




