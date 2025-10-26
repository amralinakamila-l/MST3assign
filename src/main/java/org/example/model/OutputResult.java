package org.example.model;

import java.util.List;

public class OutputResult {
    public int id;
    public String algorithm;
    public List<EdgeData> mst_edges;
    public double total_cost;
    public int vertex_count;
    public int edge_count;
    public int operations_count;
    public double execution_time_ms;
}
