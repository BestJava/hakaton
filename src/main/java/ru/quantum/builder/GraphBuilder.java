package ru.quantum.builder;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.quantum.domain.Edge;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Point;

import java.lang.reflect.Type;
import java.math.BigDecimal;
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

        List<Point> points = graph.getPoints();
        int countPoint = points.size();
        Edge[][] edges = new Edge[countPoint][countPoint];
        for (int i = 0; i < countPoint; i++) {
            String eqNameI = points.get(i).getName();
            for (int j = 0; j < countPoint; j++) {
                String eqNameJ = points.get(j).getName();
                if (!points.get(j).getName().equals(eqNameI) && !points.get(j).getEdge().isEmpty()) {
                    Map<String, Integer> mapJ = points.get(j).getEdge().stream()
                            .filter(it -> it.containsKey(eqNameI))
                            .findFirst().orElse(null);
                    Map<String, Integer> mapI = points.get(i).getEdge().stream()
                            .filter(it -> it.containsKey(eqNameJ))
                            .findFirst().orElse(null);
                    if (Objects.nonNull(mapJ)) {
                        edges[i][j] = new Edge(points.get(i), points.get(j), BigDecimal.valueOf(mapJ.get(eqNameI)));
                    } else if (Objects.nonNull(mapI)) {
                        edges[i][j] = new Edge(points.get(i), points.get(j), BigDecimal.valueOf(mapI.get(eqNameJ)));
                    } else {
                        edges[i][j] = new Edge(BigDecimal.ZERO);
                    }
                } else {
                    edges[i][j] = new Edge(BigDecimal.ZERO);
                }
            }
        }
        graph.setEdges(edges);
        return graph;
    }
}
