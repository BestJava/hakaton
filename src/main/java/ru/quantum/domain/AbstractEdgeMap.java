package ru.quantum.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEdgeMap extends ConcurrentHashMap<String, Double> {

    public Double get(Integer a, Integer b) {
        return get(createKey(a, b));
    }

    public Double put(Integer a, Integer b, Double value) {
        return put(createKey(a, b), value);
    }

    public List<Double> routes(Integer point) {
        int size = keySet().size();
        List<Double> routes = new ArrayList<>();
        for (int i = 0; i < size; i ++) {
            if (i == point) {
                routes.add(0d);
            } else {
                Double weight = get(i, point);
                if (weight == null) {
                    break;
                }
                routes.add(get(i, point));
            }
        }

        return routes;
    }

    public static String createKey(Integer a, Integer b) {
        return a < b ? String.format("%d-%d", a, b) : String.format("%d-%d", b, a);
    }
}
