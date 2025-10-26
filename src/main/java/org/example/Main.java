package org.example;

import com.google.gson.*;
import com.google.gson.ToNumberPolicy;
import org.example.algorithms.*;
import org.example.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("data/input.json");
            InputData inputData = gson.fromJson(reader, InputData.class);
            reader.close();

            List<ResultData> results = new ArrayList<>();

            for (GraphData graph : inputData.graphs) {
                ResultData result = new ResultData();
                result.graph_id = graph.id;
                result.input_stats = new InputStats(graph.nodes.size(), graph.edges.size());

                result.prim = PrimMST.run(graph);
                result.kruskal = KruskalMST.run(graph);

                results.add(result);
            }

            Map<String, Object> output = new HashMap<>();
            output.put("results", results);

            Gson prettyGson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeSpecialFloatingPointValues()
                    .setObjectToNumberStrategy(ToNumberPolicy.DOUBLE)
                    .create();

            FileWriter writer = new FileWriter("data/output.json");
            prettyGson.toJson(output, writer);
            writer.close();

            System.out.println("✅ Results saved to output.json");

            generateCSVReport(results, "data/summary.csv");
            generateExcelReport(results, "data/summary.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateCSVReport(List<ResultData> results, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("graph_id,algorithm,total_cost,vertex_count,edge_count,operations_count,execution_time_ms\n");

            for (ResultData result : results) {
                writer.write(String.format("%d,Prim,%.2f,%d,%d,%d,%.3f\n",
                        result.graph_id,
                        result.prim.total_cost,
                        result.input_stats.vertex_count,
                        result.input_stats.edge_count,
                        result.prim.operations_count,
                        result.prim.execution_time_ms));

                writer.write(String.format("%d,Kruskal,%.2f,%d,%d,%d,%.3f\n",
                        result.graph_id,
                        result.kruskal.total_cost,
                        result.input_stats.vertex_count,
                        result.input_stats.edge_count,
                        result.kruskal.operations_count,
                        result.kruskal.execution_time_ms));
            }

            System.out.println("✅ CSV report saved to " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateExcelReport(List<ResultData> results, String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("MST Results");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Graph ID", "Algorithm", "Total Cost", "Vertex Count",
                    "Edge Count", "Operations Count", "Execution Time (ms)"};

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (ResultData result : results) {
                // Prim
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(result.graph_id);
                row.createCell(1).setCellValue("Prim");
                row.createCell(2).setCellValue(result.prim.total_cost);
                row.createCell(3).setCellValue(result.input_stats.vertex_count);
                row.createCell(4).setCellValue(result.input_stats.edge_count);
                row.createCell(5).setCellValue(result.prim.operations_count);
                row.createCell(6).setCellValue(result.prim.execution_time_ms);

                // Kruskal
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(result.graph_id);
                row.createCell(1).setCellValue("Kruskal");
                row.createCell(2).setCellValue(result.kruskal.total_cost);
                row.createCell(3).setCellValue(result.input_stats.vertex_count);
                row.createCell(4).setCellValue(result.input_stats.edge_count);
                row.createCell(5).setCellValue(result.kruskal.operations_count);
                row.createCell(6).setCellValue(result.kruskal.execution_time_ms);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }

            System.out.println("✅ Excel report saved to " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



