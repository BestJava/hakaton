package ru.quantum;

import ru.quantum.domain.Edge;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Inkassator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static final String FILE_PATH = "/data/dataMaps.json";

    public static void main(String[] args) throws IOException {
        File file = new File(Main.class.getResource(FILE_PATH).getFile());
        String dataMaps = new String(Files.readAllBytes(file.toPath()));
        Inkassator app = new Inkassator();
        app.init(5, dataMaps);
        app.splitForSectors(app.getGraph());
        //System.out.println(print(app.getGraph())); //TODO: для печати матрицы смежности

        /* Алгоритм Флойда-Уоршелла за O(V^3) */
        int vNum = app.getGraph().getPoints().size();
        Edge[][] dist = new Edge[vNum][vNum]; // dist[i][j] = минимальное_расстояние(i, j)
        for (int i = 0; i < vNum; i++)
            System.arraycopy(app.getGraph().getEdgesMap()[i], 0, dist[i], 0, vNum);
        for (int k = 0; k < vNum; k++)
            for (int i = 0; i < vNum; i++)
                for (int j = 0; j < vNum; j++) {
                    if (dist[i][j] == null || dist[i][k] == null || dist[k][j] == null) {
                        continue;
                    }
                    dist[i][j].setTimeEdge(Math.min(dist[i][j].getTimeEdge(), dist[i][k].getTimeEdge() + dist[k][j].getTimeEdge()));
                }
    }

    /**
     * Подготовка строки графа для печати
     *
     * @param row строка матрицы
     * @return строка графа
     */
    private static String printRow(Edge[] row) {
        StringBuilder graphPrint = new StringBuilder();
        for (Edge i : row) {
            if (i == null) {
                graphPrint.append("      ------       |");
            } else {
                graphPrint.append(i.getPointX().getName()).append("->")
                        .append(i.getPointY().getName())
                        .append("[").append(i.getTimeEdge()).append("]|");
            }
            graphPrint.append("\t");
        }
        return graphPrint.append("\n").toString();
    }

    private static String print(Graph graph) {
        StringBuilder graphPrint = new StringBuilder();
        graphPrint.append("                    ");
        for (int i = 1; i <= graph.getPoints().size(); i++) {
            graphPrint.append("          POINT ").append(i).append("      ");
        }
        graphPrint.append("\t\n");
        int p = 1;
        for (Edge[] row : graph.getEdgesMap()) {
            graphPrint.append("       POINT ").append(p).append("      |");
            graphPrint.append(printRow(row));
            p++;
        }
        return graphPrint.toString();
    }
}
