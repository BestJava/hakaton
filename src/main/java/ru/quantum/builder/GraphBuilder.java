package ru.quantum.builder;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Point;

import java.lang.reflect.Type;
import java.util.List;

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

        return graph;
    }
}
