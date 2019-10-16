package ru.quantum.domain;

import ru.quantum.builder.GraphBuilder;

public class Inkassator {
    private int autoCashCount;
    private Graph graph;

    public Inkassator() {
        graph = new Graph();
    }

    public void init(int mashineCount, String jsonMap) {
        this.autoCashCount = mashineCount;
        this.graph = new GraphBuilder(jsonMap).build();
    }

    public Mashine getMashine(int mashineNum) {
        return null;
    }

    void getNextPoint(int mashineNum) {
        Mashine mashine = getMashine(mashineNum);
    }

    public int getAutoCashCount() {
        return autoCashCount;
    }

    public Graph getGraph() {
        return graph;
    }
}
