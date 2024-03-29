package ru.quantum.domain;

import ru.quantum.builder.GraphBuilder;

import java.util.Comparator;

public class Inkassator {
    private int autoCashCount;
    private Graph graph;

    public Inkassator() {
        graph = new Graph();
    }

    public void init(int carCount, String jsonMap) {
        this.autoCashCount = carCount;
        this.graph = new GraphBuilder(jsonMap).build();
    }

    /**
     * Метод для выделение секторов графа
     *
     * @param graph граф
     */
    public void splitForSectors(Graph graph) {
        graph.getEdges().sort(Comparator.comparing(Edge::getTimeEdge));
        for (Edge ed : graph.getEdges()) {
            System.out.println("Ребро [" + ed.getPointX().getName() + "->" + ed.getPointY().getName() + "] вес = " + ed.getWeight());
        }
    }

    public Car getCar(int carNum) {
        return null;
    }

    void getNextPoint(int carNum) {
        Car car = getCar(carNum);
    }

    public int getAutoCashCount() {
        return autoCashCount;
    }

    public Graph getGraph() {
        return graph;
    }
}
