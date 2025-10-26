package org.example;

import com.google.gson.*;
import com.google.gson.ToNumberPolicy;
import org.example.algorithms.*;
import org.example.model.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // ✅ 1. Чтение входных данных
            Gson gson = new Gson();
            FileReader reader = new FileReader("data/input.json"); // или "data/input.json" если в папке data
            InputData inputData = gson.fromJson(reader, InputData.class);
            reader.close();

            List<ResultData> results = new ArrayList<>();

            // ✅ 2. Обработка каждого графа
            for (GraphData graph : inputData.graphs) {
                ResultData result = new ResultData();
                result.graph_id = graph.id;
                result.input_stats = new InputStats(graph.nodes.size(), graph.edges.size());

                result.prim = PrimMST.run(graph);
                result.kruskal = KruskalMST.run(graph);

                results.add(result);
            }

            // ✅ 3. Подготовка выходного объекта
            Map<String, Object> output = new HashMap<>();
            output.put("results", results);

            // ✅ 4. Настройка Gson — чтобы числа были как 3.0, 16.0, а не 3, 16
            Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeSpecialFloatingPointValues()
                    .setObjectToNumberStrategy(ToNumberPolicy.DOUBLE)
                    .create();

            // ✅ 5. Запись в файл
            FileWriter writer = new FileWriter("data/output.json");
            prettyGson.toJson(output, writer);
            writer.close();

            System.out.println("✅ Results saved to output.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



