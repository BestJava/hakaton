package ru.quantum.builder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.quantum.domain.Edge;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Point;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Класс для создания и наполнение класса-графа {@link Graph}
 */
public class GraphBuilder {
    private String dataJson;

    public GraphBuilder(String dataJson) {
        this.dataJson = dataJson;
    }

    /**
     * Разбор входных данных формата JSON
     *
     * @param dataJson строка в формате JSON
     * @return список вершин(точек)
     */
    public List<Point> parseOfPoints(String dataJson) {
        // распарсим входные данные JSON
        Type typeJson = TypeToken.getParameterized(List.class, Point.class).getType();
        return new Gson().fromJson(dataJson, typeJson);
    }

    /**
     * Создание и наполнения данными графа
     *
     * @return граф
     */
    public Graph build() {
        Graph graph = new Graph();
        graph.setPoints(parseOfPoints(this.dataJson));
        initEdges(graph); // построим граф
        return graph;
    }

    /**
     * Наполним ребра и построим матрицу связанности вершин графа
     *
     * @param graph граф с вершинами
     */
    private void initEdges(Graph graph) {
        List<Point> points = graph.getPoints();
        if (!points.isEmpty()) {
            graph.setEdgesMap(new Edge[points.size()][points.size()]);
            Point pointI, pointJ;
            Edge edge;
            Map<String, Integer> mapPaths;
            int edgeCnt = 0;
            for (int i = 0; i < graph.getEdgesMap().length; i++) {
                pointI = points.get(i);
                String eqNameI = pointI.getName();
                for (int j = 0; j < graph.getEdgesMap()[i].length; j++) {
                    if (graph.getEdgesMap()[i][j] != null) {
                        continue;
                    }
                    pointJ = points.get(j);
                    String eqNameJ = pointJ.getName();
                    if (Objects.nonNull(pointI.getPath())) {
                        mapPaths = pointI.getPath().stream()
                                .filter(it -> it.containsKey(eqNameJ))
                                .findFirst().orElse(null);
                        if (Objects.nonNull(mapPaths)) {
                            edge = new Edge(pointI, pointJ, Long.valueOf(mapPaths.get(eqNameJ)));
                            pointI.getEdges().add(edge);
                            graph.setEdges(pointI.getEdges());
                            graph.getEdgesMap()[i][j] = edge;
                            edgeCnt++;
                            if (!pointI.getName().equals(pointJ.getName())) {
                                edge = new Edge(pointJ, pointI, Long.valueOf(mapPaths.get(eqNameJ)));
                                pointI.getEdges().add(edge);
                                graph.setEdges(pointI.getEdges());
                                graph.getEdgesMap()[j][i] = edge;
                                edgeCnt++;
                            }
                            continue;
                        }
                    }
                    if (Objects.nonNull(pointJ.getPath())) {
                        mapPaths = pointJ.getPath().stream()
                                .filter(it -> it.containsKey(eqNameI))
                                .findFirst().orElse(null);
                        if (Objects.nonNull(mapPaths)) {
                            edge = new Edge(pointI, pointJ, Long.valueOf(mapPaths.get(eqNameI)));
                            pointI.getEdges().add(edge);
                            graph.setEdges(pointI.getEdges());
                            graph.getEdgesMap()[i][j] = edge;
                            edgeCnt++;
                            if (!pointI.getName().equals(pointJ.getName())) {
                                edge = new Edge(pointJ, pointI, Long.valueOf(mapPaths.get(eqNameI)));
                                pointI.getEdges().add(edge);
                                graph.setEdges(pointI.getEdges());
                                graph.getEdgesMap()[j][i] = edge;
                                edgeCnt++;
                            }
                        }
                    }
                }
            }
        }
    }
}
