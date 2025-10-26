package org.example.model;

import java.util.List;

public class AlgorithmResult {
    public List<EdgeData> mst_edges;
    public double total_cost;
    public int operations_count;
    public double execution_time_ms;

    public AlgorithmResult() {}

    public AlgorithmResult(List<EdgeData> mst_edges, double total_cost, int operations_count, double execution_time_ms) {
        this.mst_edges = mst_edges;
        this.total_cost = total_cost;
        this.operations_count = operations_count;
        this.execution_time_ms = execution_time_ms;
    }

    public List<EdgeData> getMstEdges() {
        return mst_edges;
    }

    public double getTotalCost() {
        return total_cost;
    }

    public int getOperationsCount() {
        return operations_count;
    }

    public double getExecutionTimeMs() {
        return execution_time_ms;
    }
}