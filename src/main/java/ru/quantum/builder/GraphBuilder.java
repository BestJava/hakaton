package ru.quantum.builder;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.quantum.domain.Edge;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Point;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Класс для создания и наполнение класса-графа
 */
public class GraphBuilder {
    private String dataJson;

    public GraphBuilder(String dataJson) {
        this.dataJson = dataJson;
    }

    /**
     * Создание и наполнения данными графа
     *
     * @return граф
     */
    public Graph build() {
        Graph graph = new Graph();
        // распарсим входные данные JSON
        Type typeJson = TypeToken.getParameterized(List.class, Point.class).getType();
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(typeJson, new PointDeserializer());
        graph.setPoints(gson.create().fromJson(dataJson, typeJson));
        initEdge(graph); // построим граф
        return graph;
    }

    /**
     * Построим матрицу связанности вершин графа
     *
     * @param graph граф с вершинами
     */
    private void initEdge(Graph graph) {
        List<Point> points = graph.getPoints();
        if (!points.isEmpty()) {
            Point pointI, pointJ;
            Map<String, Integer> mapI, mapJ;
            int countPoint = points.size();
            Edge[][] edges = new Edge[countPoint][countPoint];
            for (int i = 0; i < countPoint; i++) {
                pointI = points.get(i);
                String eqNameI = pointI.getName();
                for (int j = 0; j < countPoint; j++) {
                    pointJ = points.get(j);
                    String eqNameJ = pointJ.getName();
                    if (!eqNameJ.equals(eqNameI) && !pointJ.getEdge().isEmpty()) {
                        mapJ = pointJ.getEdge().stream()
                                .filter(it -> it.containsKey(eqNameI))
                                .findFirst().orElse(null);
                        if (Objects.nonNull(mapJ)) {
                            edges[i][j] = new Edge(pointI, pointJ, Long.valueOf(mapJ.get(eqNameI)));
                            continue;
                        }
                        mapI = pointI.getEdge().stream()
                                .filter(it -> it.containsKey(eqNameJ))
                                .findFirst().orElse(null);
                        if (Objects.nonNull(mapI)) {
                            edges[i][j] = new Edge(pointI, pointJ, Long.valueOf(mapI.get(eqNameJ)));
                            continue;
                        }
                        edges[i][j] = new Edge(0L);
                    } else {
                        edges[i][j] = new Edge(-0L);
                    }
                }
            }
            graph.setEdges(edges);
        }
    }
}
