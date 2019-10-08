public class inkassator {
    private int mashineCount;
    Graph graph;

    public inkassator() {
        graph = new Graph();
    }

    void init(int mashineCount, int pointCount, String jsonMap ) {
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
