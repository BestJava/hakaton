package ru.quantum.domain;

import ru.quantum.schemas.Point;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PointMap extends ConcurrentHashMap<Integer,Double> {
    public PointMap() {
        super();
    }

    public PointMap(List<Point> points) {
        for (Point point : points) {
            put(point.getP(), point.getMoney());
        }
    }
}
