package ru.quantum.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Класс описывающий вершины(точки) графа
 */
public class Point {
    private String name;
    private boolean target;
    private BigDecimal sumPoint;
    private long timePoint;
    private int status;
    private BigDecimal weight;
    private List<Map<String, Integer>> path;
    private List<Vertex> partInTree;

    public Point(String name) {
        this.name = name;
    }

    /**
     * @return Наименование точки
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Является ли точкой сбора
     */
    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    /**
     * @return сумма денег в точке
     */
    public BigDecimal getSumPoint() {
        return sumPoint;
    }

    public void setSumPoint(BigDecimal sumPoint) {
        this.sumPoint = sumPoint;
    }

    /**
     * @return время сбора/выгрузки денег
     */
    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    /**
     * @return пути(ребра) до других точек
     */
    public List<Map<String, Integer>> getPath() {
        return path;
    }

    public void setPath(List<Map<String, Integer>> path) {
        this.path = path;
    }

    /**
     * <ul>
     *     <li>0 - в данную точку машина не выезжала</li>
     *     <li>1 - в данную точку уже выехала машина</li>
     * </ul>
     *
     * @return Текущий статус точки
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return текущий вес точки (сумма/время загрузки)
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * посчитаем вес точки
     */
    public void calcWeightPoint() {
        this.weight = (this.timePoint <= 0) ? this.sumPoint : this.sumPoint.divide(BigDecimal.valueOf(this.timePoint), 2, BigDecimal.ROUND_UP);
    }

    public List<Vertex> getPartInTree() {
        return partInTree;
    }

    public void setPartInTree(List<Vertex> partInTree) {
        this.partInTree = partInTree;
    }
}
