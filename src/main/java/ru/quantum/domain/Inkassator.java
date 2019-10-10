package ru.quantum.domain;

public class Inkassator {
    private int autoCashCount;
    private Graph graph;

    public Inkassator() {
        graph = new Graph();
    }

    public void init(int mashineCount, int pointCount, String jsonMap) {
        this.autoCashCount = mashineCount;
        graph.parseJSON(jsonMap);
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
