package ru.quantum.domain;
import java.util.List;

public class Vertex {
    Vertex parent;
    Edge parentEdge;
    List<Vertex> children;
    Point point;
}
