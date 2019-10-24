package ru.quantum.dto;

/**
 * Инфа о пробке
 */
public class TrafficDTO {
    int a;
    int b;
    double time;

    @Override
    public String toString() {
        return "TrafficDTO{" +
                "a=" + a +
                ", b=" + b +
                ", time=" + time +
                '}';
    }
}
