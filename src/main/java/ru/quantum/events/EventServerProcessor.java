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
import java.util.concurrent.atomic.DoubleAdder;

/**
 * Обработчик событий
 */
public class EventServerProcessor {
    private final DoubleAdder teamSum = new DoubleAdder();
    private CarsMap cars;
    private EdgeMap edgeMap;
    private PointMap pointMap;
    private List<Integer> enablePointsMap;
    private List<Integer> priorityPointsMap;
    private String token;

    public void eventConnect(ServerConnect event) {
        this.token = event.getToken();
        this.cars = new CarsMap(event.getCars());
    }

    public int eventGoto(ServerGoto event) {
        Car car = cars.get(event.getCar());
        if (car.getSum() != event.getCarsum()) {
            pointMap.put(event.getPoint(), 0d);
        }
        if (event.getPoint() == 1) {
            car.setReturnSum(car.getReturnSum() + car.getSum());
            car.setSum(0);
        } else {
            car.setSum(event.getCarsum() - car.getReturnSum());
        }
        car.setGoPoint(event.getPoint());
        //
        int newPoint = GraphHelper.getNextPoint(this.edgeMap, getPointMap(car.getName()), car.getSum(), 100000.0, car.getGoPoint());
        car.setGoPoint(newPoint);
        return newPoint;
    }

    public void eventPoints(ServerPoints event) {
        this.pointMap = new PointMap(event.getPoints());
        enablePointsMap = new ArrayList<Integer>();
        priorityPointsMap = new ArrayList<Integer>();
        for (int i=0; i<pointMap.size(); i++) {
            enablePointsMap.add(i, 1);
            priorityPointsMap.add(i, 0);
        }
    }

    public void eventRoutes(ServerRoutes event) {
        this.edgeMap = new EdgeMap(event.getRoutes());
    }

    public void eventTraffic(ServerTraffic event) {
        this.edgeMap.updateTraffic(event.getTraffic());
    }

    public void eventTeamSum(ServerTeamsum event) {
        this.teamSum.add(event.getTeamsum());
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
        return teamSum.doubleValue();
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
            if (priorityPointsMap.get(i) == 1) {
                Double sum = newPointMap.get(i);
                newPointMap.put(i, sum + sum);
            }
        }
        return newPointMap;
    }
}
