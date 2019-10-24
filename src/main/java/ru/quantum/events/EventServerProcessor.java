package ru.quantum.events;

import ru.quantum.domain.*;
import ru.quantum.schemas.ServerConnect;
import ru.quantum.schemas.ServerGoto;
import ru.quantum.schemas.ServerPoints;
import ru.quantum.schemas.ServerRoutes;
import ru.quantum.schemas.ServerTeamsum;
import ru.quantum.schemas.ServerTraffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Обработчик событий
 */
public class EventServerProcessor {
    private final CarsMap cars = new CarsMap();
    private EdgeMap edgeMap = new EdgeMap();
    private PointMap pointMap;
    private List<Integer> enablePointsMap;
    private String token;

    public void eventConnect(ServerConnect event) {
        this.token = event.getToken();
        this.cars.putAll(event.getCars().stream().collect(Collectors.toMap(key -> key, name -> new Car(name))));
    }

    public void eventGoto(ServerGoto event) {

    }

    public void eventPoints(ServerPoints event) {
        this.pointMap = new PointMap(event.getPoints());
        enablePointsMap = new ArrayList<Integer>();
        for (int i=0; i<pointMap.size(); i++) {
            enablePointsMap.set(i, 1);
        }
    }

    public void eventRoutes(ServerRoutes event) {
        this.edgeMap.setRouteMap(event.getRoutes());
    }

    public void eventTraffic(ServerTraffic event) {
        this.edgeMap.updateTraffic(event.getTraffic());
    }

    public void eventTeamSum(ServerTeamsum event) {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // генерирует PointMap для передачи в getNextPoint
    private PointMap getPointMap(String carName) {
        PointMap newPointMap = (PointMap)pointMap.clone();
        for (Car car : cars.values()) {
            if (!car.getName().equals(carName)) {
                int toPoint = car.getGoPoint();
                if (toPoint > 1) {
                    newPointMap.put(toPoint, 0.0);
                }
            }
        }
        for (int i=0; i<enablePointsMap.size(); i++) {
            if (enablePointsMap.get(i) == 0) {
                newPointMap.put(i, 0.0);
            }
        }
        return newPointMap;
    }
}
