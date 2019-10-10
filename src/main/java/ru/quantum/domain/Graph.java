package ru.quantum.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    Vertex root;
    private List<Point> points = new ArrayList<>();
    List<Branch> branches;

    public List<Point> getPoints() {
        return points;
    }

    void parseJSON(String dataJson) {
        Type typeJson = new TypeToken<List<Point>>() {
        }.getType();
        List<Point> data = new Gson().fromJson(dataJson, typeJson);
        //TODO логика пере/на-полнения
        this.points = data;
    }
}
