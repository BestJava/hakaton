package ru.quantum.domain;

import java.math.BigDecimal;

/**
 * Класс описывающий ребра графа и вес
 */
public class Edge {
    private Point pointX;
    private Point pointY;
    private long timeEdge;
    private long timeEdgeStart;
    private BigDecimal weight;

    public Edge(Point from, Point to, long timeEdge) {
        this.pointX = from;
        this.pointY = to;
        this.timeEdge = timeEdge;
        this.weight = this.pointY.getSumPoint().divide(BigDecimal.valueOf(Math.addExact(timeEdge, pointY.getTimePoint())),2, BigDecimal.ROUND_UP);
    }

    /**
     * @return исходная точка ребра
     */
    public Point getPointX() {
        return pointX;
    }

    public void setPointX(Point pointX) {
        this.pointX = pointX;
    }

    /**
     * @return конечная точка ребра
     */
    public Point getPointY() {
        return pointY;
    }

    public void setPointY(Point pointY) {
        this.pointY = pointY;
    }

    /**
     * @return время в пути
     */
    public long getTimeEdge() {
        return timeEdge;
    }

    public void setTimeEdge(long timeEdge) {
        this.timeEdge = timeEdge;
    }

    /**
     * @return вес ребра Формула: сумма на точке Y/(время в пути + разгрузки)
     */
    public BigDecimal getWeight() {
        return weight;
    }
}
