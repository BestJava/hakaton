package ru.quantum.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс описывающий граф
 */
public class Graph {
    private List<Point> points = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private List<Edge> stepEdges = new ArrayList<>();
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

    public void BFS() {
        final int size = points.size();
        final List<Point> pointSet = new ArrayList<>(size);

        int level = 0;
        Point rootPoint = root.point;
        rootPoint.setDistance(level);
        pointSet.add(rootPoint);
        Point currPoint;
        Point tempPoint;

        while (!pointSet.isEmpty()) {
            currPoint = pointSet.get(0);
            level = currPoint.getDistance() + 1;
            for (Edge e : currPoint.getEdges()) {
                if (e.getPointX() == currPoint) {
                    tempPoint = e.getPointY();
                } else {
                    tempPoint = e.getPointX();
                }
                tempPoint.setDistance(level);
                pointSet.add(tempPoint);
            }
            currPoint = pointSet.remove(0);
        }
    }

    void makeStepEdges(Point currPoint) {
        stepEdges.clear();
        for (Edge edge: currPoint.getEdges()){
            stepEdges.add(edge);
        }
        for (Edge edge: root.point.getEdges()){
            if (!(((edge.getPointX() == root.point) && (edge.getPointY() == currPoint)) ||
               ((edge.getPointY() == root.point) && (edge.getPointX() == currPoint)))) {
                stepEdges.add(edge);
            }
        }
    }
}
