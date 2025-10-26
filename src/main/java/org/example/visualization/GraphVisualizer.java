package org.example.visualization;

import org.example.graph.Graph;
import org.example.graph.Edge;
import org.example.algorithms.MSTResult;
import org.example.algorithms.PrimMST;
import org.example.algorithms.KruskalMST;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphVisualizer {

    public static void createSimpleGraphImage(Graph graph, String filename) {
        try {
            int width = 1000, height = 800;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            Map<Integer, Point> vertexPositions = new HashMap<>();
            int vertices = graph.getVerticesCount();
            int centerX = width / 2, centerY = height / 2;
            int radius = Math.min(width, height) / 3;

            for (int i = 0; i < vertices; i++) {
                double angle = 2 * Math.PI * i / vertices;
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                vertexPositions.put(i, new Point(x, y));
            }

            g2d.setColor(Color.LIGHT_GRAY);
            for (Edge edge : graph.getEdges()) {
                Point from = vertexPositions.get(edge.getSource());
                Point to = vertexPositions.get(edge.getDestination());

                if (from != null && to != null) {
                    g2d.drawLine(from.x, from.y, to.x, to.y);
                    int midX = (from.x + to.x) / 2;
                    int midY = (from.y + to.y) / 2;
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.format("%.1f", edge.getWeight()), midX, midY);
                    g2d.setColor(Color.LIGHT_GRAY);
                }
            }

            g2d.setColor(Color.BLUE);
            for (Point pos : vertexPositions.values()) {
                g2d.fillOval(pos.x - 15, pos.y - 15, 30, 30);
            }

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            for (Map.Entry<Integer, Point> entry : vertexPositions.entrySet()) {
                String nodeName = graph.getNodeName(entry.getKey());
                Point pos = entry.getValue();
                int textWidth = g2d.getFontMetrics().stringWidth(nodeName);
                g2d.drawString(nodeName, pos.x - textWidth / 2, pos.y + 5);
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String title = String.format("Graph %d: %d vertices, %d edges",
                    graph.getGraphId(), vertices, graph.getEdges().size());
            g2d.drawString(title, 20, 30);

            ImageIO.write(image, "PNG", new File(filename));
            System.out.println("✅ Image saved: " + filename);

            g2d.dispose();

        } catch (Exception e) {
            System.err.println("Error while creating graph image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createAllGraphImages(List<Graph> graphs) {
        try {
            System.out.println("\n=== Generating graph images ===");

            for (Graph graph : graphs) {
                System.out.println("Processing graph " + graph.getGraphId());

                String originalImage = String.format("graph_%d_original.png", graph.getGraphId());
                createSimpleGraphImage(graph, originalImage);

                try {
                    MSTResult primResult = PrimMST.findMST(graph);
                    String primImage = String.format("graph_%d_prim_mst.png", graph.getGraphId());
                    createMSTImage(graph, primResult.getMstEdges(), primImage);

                    MSTResult kruskalResult = KruskalMST.findMST(graph);
                    String kruskalImage = String.format("graph_%d_kruskal_mst.png", graph.getGraphId());
                    createMSTImage(graph, kruskalResult.getMstEdges(), kruskalImage);

                } catch (Exception e) {
                    System.err.println("Failed to create MST images for graph " + graph.getGraphId());
                }
            }

            System.out.println("✅ All images generated!");

        } catch (Exception e) {
            System.err.println("Error while creating images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createMSTImage(Graph graph, List<Edge> mstEdges, String filename) {
        try {
            int width = 1000, height = 800;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            Map<Integer, Point> vertexPositions = new HashMap<>();
            int vertices = graph.getVerticesCount();
            int centerX = width / 2, centerY = height / 2;
            int radius = Math.min(width, height) / 3;

            for (int i = 0; i < vertices; i++) {
                double angle = 2 * Math.PI * i / vertices;
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                vertexPositions.put(i, new Point(x, y));
            }

            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(2.0f));
            for (Edge edge : mstEdges) {
                Point from = vertexPositions.get(edge.getSource());
                Point to = vertexPositions.get(edge.getDestination());

                if (from != null && to != null) {
                    g2d.drawLine(from.x, from.y, to.x, to.y);
                }
            }

            g2d.setColor(Color.BLUE);
            for (Point pos : vertexPositions.values()) {
                g2d.fillOval(pos.x - 15, pos.y - 15, 30, 30);
            }

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            for (Map.Entry<Integer, Point> entry : vertexPositions.entrySet()) {
                String nodeName = graph.getNodeName(entry.getKey());
                Point pos = entry.getValue();
                int textWidth = g2d.getFontMetrics().stringWidth(nodeName);
                g2d.drawString(nodeName, pos.x - textWidth / 2, pos.y + 5);
            }

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String title = String.format("MST for Graph %d (Cost: %.1f)",
                    graph.getGraphId(), calculateMSTCost(mstEdges));
            g2d.drawString(title, 20, 30);

            ImageIO.write(image, "PNG", new File(filename));
            System.out.println("✅ MST image saved: " + filename);

            g2d.dispose();

        } catch (Exception e) {
            System.err.println("Error while creating MST image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static double calculateMSTCost(List<Edge> mstEdges) {
        return mstEdges.stream().mapToDouble(Edge::getWeight).sum();
    }
}

