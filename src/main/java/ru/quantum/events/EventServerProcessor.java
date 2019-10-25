package ru.quantum.events;

import ru.quantum.domain.Car;
import ru.quantum.domain.CarsMap;
import ru.quantum.domain.EdgeMap;
import ru.quantum.domain.PointMap;
import ru.quantum.helpers.GraphHelper;
import ru.quantum.schemas.ServerConnect;
import ru.quantum.schemas.ServerGoto;
import ru.quantum.schemas.ServerPoints;
import ru.quantum.schemas.ServerPointsupdate;
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
    private static final double TOTAL_TIME = 500d;

    public void eventConnect(ServerConnect event) {
        this.token = event.getToken();
        this.cars = new CarsMap(event.getCars());
    }

    private Double getDuration(Double duration) {
        return duration == null ? TOTAL_TIME : duration;
    }

    public int eventGoto(ServerGoto event) {
        Car car = cars.get(event.getCar());
        if (car.getSum() != event.getCarsum()) {
            pointMap.put(event.getPoint(), 0d);
        }
        if (event.getPoint() == 1) {
            car.setSum(0);
        } else {
            car.setSum(event.getCarsum());
        }
        car.setGoPoint(event.getPoint());
        //
        int newPoint = GraphHelper.getNextPoint(this.edgeMap, getPointMap(car.getName()), priorityPointsMap, car.getSum(), getDuration(event.getDuration()), car.getGoPoint());
        car.setGoPoint(newPoint);
        return newPoint;
    }

    public void eventPoints(ServerPoints event) {
        this.pointMap = new PointMap(event.getPoints());
        enablePointsMap = new ArrayList<Integer>();
        priorityPointsMap = new ArrayList<Integer>();
        for (int i = 0; i < pointMap.size(); i++) {
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

    public void eventPointsUpdate(ServerPointsupdate event) {
        event.getPoints().forEach(it -> {
            if (it.getDisable() != null) {
                if (it.getDisable()) {
                    enablePointsMap.set(it.getP(), 0);
                } else {
                    enablePointsMap.set(it.getP(), 1);
                }
            }
            if (it.getPriority() != null) {
                if (it.getPriority()) {
                    priorityPointsMap.set(it.getP(), 1);
                } else {
                    priorityPointsMap.set(it.getP(), 0);
                }
            }
        });
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
        for (int i = 0; i < enablePointsMap.size(); i++) {
            if (enablePointsMap.get(i) == 0) {
                newPointMap.put(i, 0.0);
            }
        }
        return newPointMap;
    }
}
