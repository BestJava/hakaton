package ru.quantum.domain;

import java.util.List;
import java.util.Map;

public class Point {
    private String name;
    private boolean target; // true если точка сбора
    private int summa; // сумма денег в точке
    private int time; // время сбора/выгрузки денег
    private List<Vertex> partInTree;
    private List<Map<String, Integer>> edge; // пути(ребра) до других точек
    private int status; // статус, чтобы отметить если машина уже выехала в точку, чтобы не отпралять туда же вторую

    public Point() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public int getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Vertex> getPartInTree() {
        return partInTree;
    }

    public void setPartInTree(List<Vertex> partInTree) {
        this.partInTree = partInTree;
    }

    public List<Map<String, Integer>> getEdge() {
        return edge;
    }

    public void setEdge(List<Map<String, Integer>> edge) {
        this.edge = edge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
