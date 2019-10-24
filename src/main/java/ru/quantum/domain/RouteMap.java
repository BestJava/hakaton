package ru.quantum.domain;

import ru.quantum.schemas.Route;

import java.util.List;

public class RouteMap extends AbstractEdgeMap {

    public RouteMap(List<Route> routes) {
        for (Route route :routes) {
            put(route.getA(), route.getB(), route.getTime());
        }
    }
}
