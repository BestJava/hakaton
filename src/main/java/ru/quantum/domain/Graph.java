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

    /**
     * Подготовка строки графа для печати
     *
     * @param row строка матрицы
     * @return строка графа
     */
    private String printRow(Edge[] row) {
        StringBuilder graphPrint = new StringBuilder();
        for (Edge i : row) {
            if (i.getPointX() == null || i.getPointY() == null) {
                graphPrint.append("      ------       |");
            } else {
                graphPrint.append(i.getPointX().getName()).append("->")
                        .append(i.getPointY().getName())
                        .append("[").append(i.getWeight()).append("]|");
            }
            graphPrint.append("\t");
        }
        return graphPrint.append("\n").toString();
    }

    @Override
    public String toString() {
        StringBuilder graphPrint = new StringBuilder();
        graphPrint.append("Размерность графа: ").append(this.getEdges().length)
                .append(" X ").append(this.getEdges().length).append("\n");
        graphPrint.append("                    ").append("        POINT 1       ");
        for (int i = 2; i <= this.getEdges().length; i++) {
            graphPrint.append("          POINT ").append(i).append("      ");
        }
        graphPrint.append("\t\n");
        int p = 1;
        for (Edge[] row : this.getEdges()) {
            graphPrint.append("       POINT ").append(p).append("      |");
            graphPrint.append(printRow(row));
            p++;
        }
        return graphPrint.toString();
    }
}
