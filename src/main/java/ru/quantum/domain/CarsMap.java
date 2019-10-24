package ru.quantum.domain;

import java.util.HashMap;

/**
 * Список машин
 */
public class CarsMap extends HashMap<String, Car> {

    public void updateCarSum(String name, double sum) {
        get(name).setSum(sum);
    }

    public void updateCarGoPoint(String name, int point) {
        get(name).setGoPoint(point);
    }
}
