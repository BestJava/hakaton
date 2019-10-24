package ru.quantum.domain;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс описывающий граф
 */
public class Graph {
    private List<Point> points = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<Integer, Edge> stepEdges = new HashMap<>();
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
            if (currPoint == edge.getPointY()) {
                stepEdges.put(edge.getPointX().getNum(), edge);
            } else {
                stepEdges.put(edge.getPointY().getNum(), edge);
            }
        }
        for (Edge edge: root.point.getEdges()) {
            if (!(((edge.getPointX() == root.point) && (edge.getPointY() == currPoint)) ||
               ((edge.getPointY() == root.point) && (edge.getPointX() == currPoint)))) {
                if (root.point == edge.getPointY()) {
                    stepEdges.put(edge.getPointX().getNum(), edge);
                } else {
                    stepEdges.put(edge.getPointY().getNum(), edge);
                }
            }
        }
    }

    Point GetOtherPoint(Edge edge, Point currPoint) {
        if (currPoint == edge.getPointY()) {
            return edge.getPointX();
        } else {
            return edge.getPointY();
        }
    }

    Point getNextPoint(Point currPoint) {
        Point nextPoint = null;
        Map<Integer, Edge> stepEdgesCurrent = new HashMap<>();
        Map<Integer, Edge> stepEdgesRoot = new HashMap<>();
        for (Edge edge: currPoint.getEdges()){
            if (currPoint == edge.getPointY()) {
                stepEdgesCurrent.put(edge.getPointX().getNum(), edge);
            } else {
                stepEdgesCurrent.put(edge.getPointY().getNum(), edge);
            }
        }
        for (Edge edge: root.point.getEdges()) {
            if (!(((edge.getPointX() == root.point) && (edge.getPointY() == currPoint)) ||
                    ((edge.getPointY() == root.point) && (edge.getPointX() == currPoint)))) {
                if (root.point == edge.getPointY()) {
                    stepEdgesRoot.put(edge.getPointX().getNum(), edge);
                } else {
                    stepEdgesRoot.put(edge.getPointY().getNum(), edge);
                }
            } else {
                // TODO посчитать прямой путь

            }
        }

        // TODO остаток времени и заполнение тачки и вернуться домой

        BigDecimal maxCost = BigDecimal.ZERO;
        for (Map.Entry<Integer, Edge> stepEdge: stepEdgesCurrent.entrySet()){
            Edge e = stepEdge.getValue();
            Point p = GetOtherPoint(e, currPoint);
            BigDecimal cost = p.getSumPoint().divide(stepEdge.getValue().getWeight().add(stepEdgesRoot.get(p.getNum()).getWeight()));
            if (maxCost.compareTo(cost) < 0) {
                maxCost = cost;
                nextPoint = p;
            }
        }

        return nextPoint;
    }
}
