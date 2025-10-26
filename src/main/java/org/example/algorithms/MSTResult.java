package org.example.algorithms;

import org.example.graph.Edge;
import java.util.List;

public class MSTResult {
    private final double totalCost;
    private final int operationsCount;
    private final List<Edge> mstEdges;

    public MSTResult(double totalCost, int operationsCount, List<Edge> mstEdges) {
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.mstEdges = mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }
}
