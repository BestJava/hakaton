package ru.quantum.helpers;

import ru.quantum.domain.EdgeMap;
import ru.quantum.domain.PointMap;

import java.util.List;

public class GraphHelper {
    static final int ROOT_POINT = 1;
    static final double LIMIT_SUM = 1000000;

    public static int getNextPoint(EdgeMap edgeMap, PointMap pointMap, Double carSum, Double remainTime, int currPoint) {
        int nextPoint = 1;
        List<Double> stepEdgesCurrent = edgeMap.routes(currPoint);
        List<Double> stepEdgesRoot = edgeMap.routes(ROOT_POINT);

        // если сумма превышена, ищем самы быстрый путь домой и идем туда
        if (carSum >= LIMIT_SUM) {
            double minWeight = 0;
            for (int i = 0; i < stepEdgesCurrent.size(); i++) {
                Double stepEdge = stepEdgesCurrent.get(i);
                Double stepEdgeRoot = stepEdgesRoot.get(i);
                if (i == currPoint) continue;
                double stepWeight = stepEdge + stepEdgeRoot;
                if (minWeight > stepWeight) {
                    nextPoint = i;
                    minWeight = stepWeight;
                }
            }
        } else { // иначе ищем самый выгодный путь

            double maxCost = 0;
            double rootWeight = 0;
            double minWeight = 0;
            for (int i = 0; i < stepEdgesCurrent.size(); i++) {
                double pointSum = pointMap.get(i); // сумма в точке i;
                if (pointSum == 0) continue;  // если сумма в точке = 0, пропускаем
                if (LIMIT_SUM - carSum > pointSum) continue;
                Double stepEdge = stepEdgesCurrent.get(i);
                Double stepEdgeRoot = stepEdgesRoot.get(i);
                if (i == currPoint) continue;
                if (i == ROOT_POINT) {
                    rootWeight = stepEdge;
                } else {
                    double stepWeight = stepEdge + stepEdgeRoot;
                    double cost = pointSum / stepWeight;
                    if (maxCost < cost) {
                        maxCost = cost;
                        nextPoint = i;
                        minWeight = stepWeight;
                    }
                }
            }

            // если времени не хватает на сбор денег и достаточно для перехода в точку сбора напрямую, идем в точку сбора
            if ((minWeight > remainTime) && (rootWeight <= remainTime)) {
                nextPoint = ROOT_POINT;
            }
        }
        return nextPoint;
    }
}
