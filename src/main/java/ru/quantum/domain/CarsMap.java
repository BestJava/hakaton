package ru.quantum.domain;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Список машин
 */
public class CarsMap extends HashMap<String, Car> {

    public CarsMap(List<String> cars) {
        putAll(cars.stream().collect(Collectors.toMap(key -> key, Car::new)));
    }

    public void updateCarSum(String name, double sum) {
        get(name).setSum(sum);
    }

    public void updateCarGoPoint(String name, int point) {
        get(name).setGoPoint(point);
    }
}
