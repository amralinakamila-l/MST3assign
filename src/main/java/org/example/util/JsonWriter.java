package org.example.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.ResultData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonWriter {
    public static void writeResults(String filePath, List<ResultData> results) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filePath);
        gson.toJson(new Object() {
            final List<ResultData> resultsList = results;
        }, writer);
        writer.close();
    }
}

