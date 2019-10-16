package ru.quantum.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс описывающий граф
 */
public class Graph {
    private List<Point> points = new ArrayList<>();
    private Edge[][] edges;
    Vertex root;
    List<Branch> branches;

    /**
     * @return вершины (точки) графа
     */
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        if (this.points.isEmpty()) {
            this.points = points;
        } else {
            this.points.addAll(points);
        }
    }

    /**
     * @return ребра графа
     */
    public Edge[][] getEdges() {
        return edges;
    }

    public void setEdges(Edge[][] edges) {
        this.edges = edges;
    }
}
