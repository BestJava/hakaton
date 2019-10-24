package ru.quantum.domain;

import ru.quantum.schemas.Route;
import ru.quantum.schemas.Traffic;

import java.util.List;

public class EdgeMap extends AbstractEdgeMap {

    private RouteMap routeMap;
    private TrafficMap trafficMap;

    public EdgeMap(List<Route> routes) {
        routeMap = new RouteMap(routes);
    }

    public EdgeMap(List<Route> routes, List<Traffic> traffics) {
        this(routes);
        updateTraffic(traffics);
    }

    public void updateTraffic(List<Traffic> traffics) {
        if (trafficMap == null) {
            trafficMap = new TrafficMap(traffics);
        } else {
            trafficMap.update(traffics);
        }
    }

    @Override
    public List<Double> routes(Integer point) {
        List<Double> routes = routeMap.routes(point);
        List<Double> traffics = trafficMap.routes(point);

        for (int i = 0; i < routes.size(); i ++) {
            routes.add(i, routes.get(i) * traffics.get(i));
        }

        return routes;
    }
}
