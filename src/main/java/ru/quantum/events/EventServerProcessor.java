package ru.quantum.events;

import ru.quantum.domain.Car;
import ru.quantum.domain.CarsMap;
import ru.quantum.domain.EdgeMap;
import ru.quantum.domain.PointMap;
import ru.quantum.schemas.ServerConnect;
import ru.quantum.schemas.ServerGoto;
import ru.quantum.schemas.ServerPoints;
import ru.quantum.schemas.ServerRoutes;
import ru.quantum.schemas.ServerTeamsum;
import ru.quantum.schemas.ServerTraffic;

import java.util.stream.Collectors;

/**
 * Обработчик событий
 */
public class EventServerProcessor {
    private final CarsMap cars = new CarsMap();
    private EdgeMap edgeMap = new EdgeMap();
    private PointMap pointMap;
    private String token;
    private final Object carLock = new Object();
    private final Object trafficLock = new Object();

    public void eventConnect(ServerConnect event) {
        this.token = event.getToken();
        synchronized (carLock) {
            this.cars.putAll(event.getCars().stream().collect(Collectors.toMap(key -> key, name -> new Car(name))));
        }
    }

    public void eventGoto(ServerGoto event) {

    }

    public void eventPoints(ServerPoints event) {
        this.pointMap = new PointMap(event.getPoints());
    }

    public void eventRoutes(ServerRoutes event) {
        this.edgeMap.setRouteMap(event.getRoutes());
    }

    public void eventTraffic(ServerTraffic event) {
        synchronized (trafficLock) {
            this.edgeMap.updateTraffic(event.getTraffic());
        }
    }

    public void eventTeamSum(ServerTeamsum event) {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
