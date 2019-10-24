package ru.quantum.events;

import ru.quantum.domain.*;
import ru.quantum.helpers.GraphHelper;
import ru.quantum.schemas.ServerConnect;
import ru.quantum.schemas.ServerGoto;
import ru.quantum.schemas.ServerPoints;
import ru.quantum.schemas.ServerRoutes;
import ru.quantum.schemas.ServerTeamsum;
import ru.quantum.schemas.ServerTraffic;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик событий
 */
public class EventServerProcessor {
    private double teamSum;
    private CarsMap cars;
    private EdgeMap edgeMap;
    private PointMap pointMap;
    private List<Integer> enablePointsMap;
    private String token;
    private final Object teamSumLock = new Object();

    public void eventConnect(ServerConnect event) {
        this.token = event.getToken();
        this.cars = new CarsMap(event.getCars());
    }

    public int eventGoto(ServerGoto event) {
        Car car = cars.get(event.getCar());
        car.setSum(event.getCarsum());
        car.setGoPoint(event.getPoint());
        //
        int newPoint = GraphHelper.getNextPoint(this.edgeMap, getPointMap(car.getName()), car.getSum(), 100000.0, car.getGoPoint());
        car.setGoPoint(newPoint);
        return newPoint;
    }

    public void eventPoints(ServerPoints event) {
        this.pointMap = new PointMap(event.getPoints());
        enablePointsMap = new ArrayList<Integer>();
        for (int i=0; i<pointMap.size(); i++) {
            enablePointsMap.add(i, 1);
        }
    }

    public void eventRoutes(ServerRoutes event) {
        this.edgeMap = new EdgeMap(event.getRoutes());
    }

    public void eventTraffic(ServerTraffic event) {
        this.edgeMap.updateTraffic(event.getTraffic());
    }

    public void eventTeamSum(ServerTeamsum event) {
        synchronized (teamSumLock) {
            this.teamSum += event.getTeamsum();
        }
    }

    public String getToken() {
        return token;
    }

    public CarsMap getCars() {
        return cars;
    }

    public EdgeMap getEdgeMap() {
        return edgeMap;
    }

    public PointMap getPointMap() {
        return pointMap;
    }

    public double getTeamSum() {
        return teamSum;
    }

    // генерирует PointMap для передачи в getNextPoint
    private PointMap getPointMap(String carName) {
        PointMap newPointMap = new PointMap();
        newPointMap.putAll(pointMap);

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
