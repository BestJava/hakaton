package ru.quantum.algorithms;

import ru.quantum.domain.Edge;
import ru.quantum.domain.Graph;
import ru.quantum.domain.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Astar {

    public Astar() {
    }

    /**
     * Алгоритм кратчайшего пути Astar
     *
     * @param graph граф
     * @param start начальная точка
     * @param goal  конечная точка
     * @return список ребер маршрута или NULL если путь не найден
     */
    public List<Edge> aStar(Graph graph, Point start, Point goal) {
        final int size = graph.getPoints().size();
        final Set<Point> closedSet = new HashSet<>(size);
        final List<Point> openSet = new ArrayList<>(size);
        openSet.add(start);
        final Map<Point, Point> cameFrom = new HashMap<>(size);

        final Map<Point, BigDecimal> gScore = new HashMap<>();
        gScore.put(start, BigDecimal.ZERO);

        final Map<Point, BigDecimal> fScore = new HashMap<>();
        for (Point v : graph.getPoints()) {
            fScore.put(v, BigDecimal.valueOf(Integer.MAX_VALUE));
        }
        fScore.put(start, heuristicCostEstimate(start, goal));

        final Comparator<Point> comparator = new Comparator<Point>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public int compare(Point o1, Point o2) {
                if (fScore.get(o1).compareTo(fScore.get(o2)) < 0)
                    return -1;
                if (fScore.get(o2).compareTo(fScore.get(o1)) < 0)
                    return 1;
                return 0;
            }
        };

        while (!openSet.isEmpty()) {
            final Point current = openSet.get(0);
            if (current.equals(goal))
                return reconstructPath(cameFrom, goal);

            openSet.remove(0);
            closedSet.add(current);
            for (Edge edge : current.getEdges()) {
                final Point neighbor = edge.getPointY();
                if (closedSet.contains(neighbor))
                    continue;

                final BigDecimal tenativeGScore = gScore.get(current).add(distanceBetween(current, neighbor));
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);
                else if (tenativeGScore.compareTo(gScore.get(neighbor)) > 0)
                    continue;

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tenativeGScore);
                final BigDecimal estimatedFScore = gScore.get(neighbor).add(heuristicCostEstimate(neighbor, goal));
                fScore.put(neighbor, estimatedFScore);

                openSet.sort(comparator);
            }
        }
        return null;
    }

    /**
     * Вес пути
     */
    protected BigDecimal distanceBetween(Point start, Point next) {
        for (Edge e : start.getEdges()) {
            if (e.getPointY().equals(next))
                return e.getWeight();
        }
        return BigDecimal.valueOf(Integer.MAX_VALUE);
    }

    /**
     * ПО умолчанию
     */
    @SuppressWarnings("unused")
    protected BigDecimal heuristicCostEstimate(Point start, Point goal) {
        return BigDecimal.ZERO;
    }

    private List<Edge> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        final List<Edge> totalPath = new ArrayList<>();

        while (current != null) {
            final Point previous = current;
            current = cameFrom.get(current);
            if (current != null) {
                final Edge edge = current.getEdge(previous);
                totalPath.add(edge);
            }
        }
        Collections.reverse(totalPath);
        return totalPath;
    }

    public static void main(String[] args) {
        Astar astar = new Astar();


    }
}
