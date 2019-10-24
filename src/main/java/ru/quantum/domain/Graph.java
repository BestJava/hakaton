package ru.quantum.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывающий граф
 */
public class Graph {
    private List<Point> points = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Edge[][] edgesMap;
    Vertex root;

    /**
     * @return вершины (точки) графа
     */
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points.addAll(points);
    }

    /**
     * @return ребра графа
     */
    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        if (this.edges.isEmpty()) {
            this.edges.addAll(edges);
        } else {
            this.edges.addAll(edges);
        }
    }

    /**
     * @return карта смежности вершин
     */
    public Edge[][] getEdgesMap() {
        return edgesMap;
    }

    public void setEdgesMap(Edge[][] edgesMap) {
        this.edgesMap = edgesMap;
    }

    public Vertex makeTree {

    }
}
