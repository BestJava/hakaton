package ru.quantum.domain;

/**
 * Класс описывающий ребра графа и вес
 */
public class Edge {
    private Point pointX;
    private Point pointY;
    private long weight;

    public Edge(long weight) {
        this.weight = weight;
    }

    public Edge(Point from, Point to, long weight) {
        this.pointX = from;
        this.pointY = to;
        this.weight = Math.addExact(weight, pointY.getTimePoint());
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
     * @return вес ребра (время в пути)
     */
    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
