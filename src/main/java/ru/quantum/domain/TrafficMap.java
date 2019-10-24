package ru.quantum.domain;

import ru.quantum.schemas.Traffic;

import java.util.List;

public class TrafficMap extends AbstractEdgeMap {

    public TrafficMap(List<Traffic> traffics) {
        update(traffics);
    }

    public void update(List<Traffic> traffics) {
        for (Traffic traffic: traffics) {
            put(traffic.getA(), traffic.getB(), traffic.getJam());
        }
    }
}
