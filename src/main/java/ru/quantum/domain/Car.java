package ru.quantum.domain;

public class Car {
    private String name;
    private double sum;
    private int goPoint;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getGoPoint() {
        return goPoint;
    }

    public void setGoPoint(int goPoint) {
        this.goPoint = goPoint;
    }
}
