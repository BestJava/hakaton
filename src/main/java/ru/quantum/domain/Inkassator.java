package ru.quantum.domain;

public class Inkassator {
    private int mashineCount;
    Graph graph;

    public Inkassator() {
        graph = new Graph();
    }

    void init(int mashineCount, int pointCount, String jsonMap) {
        this.mashineCount = mashineCount;
        graph.parseJSON(jsonMap);
    }

    Mashine getMashine(int mashineNum) {
        return null;
    }

    void getNextPoint(int mashineNum) {
        Mashine mashine = getMashine(mashineNum);
    }
}
