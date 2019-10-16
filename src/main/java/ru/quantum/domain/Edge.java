package ru.quantum.domain;

import java.math.BigDecimal;

/**
 * Класс описывающий ребра графа и вес
 */
public class Edge {
    private Point pointFrom;
    private Point pointTo;
    private BigDecimal weight;

    public Edge(BigDecimal weight) {
        this.weight = weight;
    }

    public Edge(Point from, Point to, BigDecimal weight) {
        this.pointFrom = from;
        this.pointTo = to;
        this.weight = weight;
    }

    /**
     * @return исходная точка ребра
     */
    public Point getPointFrom() {
        return pointFrom;
    }

    public void setPointFrom(Point pointFrom) {
        this.pointFrom = pointFrom;
    }

    /**
     * @return конечная точка ребра
     */
    public Point getPointTo() {
        return pointTo;
    }

    public void setPointTo(Point pointTo) {
        this.pointTo = pointTo;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight вес ребра
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
