package org.example.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.model.GraphData;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    public static List<GraphData> readGraphs(String filePath) throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);
        return gson.fromJson(jsonObject.getAsJsonArray("graphs"), new TypeToken<List<GraphData>>() {}.getType());
    }
}

