package org.example.util;
/// лщаошполкопок
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.model.*;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class JsonReader {
    public static List<Graph> loadGraphs(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray graphsArray = jsonObject.getAsJsonArray("graphs");

            List<Graph> graphs = new ArrayList<>();
            for (JsonElement graphEl : graphsArray) {
                JsonObject g = graphEl.getAsJsonObject();
                int id = g.get("id").getAsInt();

                List<String> nodes = new Gson().fromJson(g.get("nodes"),
                        new TypeToken<List<String>>(){}.getType());

                List<Edge> edges = new ArrayList<>();
                for (JsonElement e : g.getAsJsonArray("edges")) {
                    JsonObject eo = e.getAsJsonObject();
                    edges.add(new Edge(
                            eo.get("from").getAsString(),
                            eo.get("to").getAsString(),
                            eo.get("weight").getAsInt()
                    ));
                }

                graphs.add(new Graph(id, nodes, edges));
            }
            return graphs;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

