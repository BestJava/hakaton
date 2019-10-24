package ru.quantum.domain;

import ru.quantum.schemas.Point;

import java.util.HashMap;
import java.util.List;

public class PointMap extends HashMap<Integer, Double> {

    public PointMap(List<Point> points) {
        for (Point point : points) {
            put(point.getP(), point.getMoney());
        }
    }
}
