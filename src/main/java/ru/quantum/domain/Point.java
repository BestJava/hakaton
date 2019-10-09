package ru.quantum.domain;

import java.util.List;

public class Point {
    boolean target; // true если точка сбора
    int summa; // сумма денег в точке
    int time; // время сбора/выгрузки денег
    List<Vertex> partInTree;
    int status; // статус, чтобы отметить если машина уже выехала в точку, чтобы не отпралять туда же вторую
}
