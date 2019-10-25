package ru.quantum.helpers;

import ru.quantum.domain.EdgeMap;
import ru.quantum.domain.PointMap;

import java.util.List;

public class GraphHelper {
    static final int ROOT_POINT = 1;
    static final double LIMIT_SUM = 1000000;

    public static double time = 0d;

    public static int getNextPoint(EdgeMap edgeMap, PointMap pointMap, List<Integer> priorityPointMap, Double carSum, Double remainTime, int currPoint, int carCount) {
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
            time +=minWeight;
            System.out.println("next point:" + nextPoint + ", sum: " + pointMap.get(nextPoint) + ", time: " + time + ", duration:" + remainTime);
        } else { // иначе ищем самый выгодный путь

            double maxCost = 0;
            double rootWeight = 0;
            double minWeight = 0;
            double maxSum = 0;
            double maxPointSum = 0d;
            double stepEdgeRoot = 0d;
            for (int i = 0; i < stepEdgesCurrent.size(); i++) {
                double pointSum = pointMap.get(i); // сумма в точке i;
                if (maxSum < pointSum) {
                    maxSum = pointSum;
                }
                Double stepEdge = stepEdgesCurrent.get(i);
                if (i == ROOT_POINT) {
                    rootWeight = stepEdge;
                } else {
                    if (i == currPoint) continue;
                    if (pointSum == 0) continue;  // если сумма в точке = 0, пропускаем
                    if (LIMIT_SUM - carSum < pointSum) continue;
                    double stepWeight = stepEdge;// + stepEdgeRoot;
                    double scoreSum = priorityPointMap.get(i) == 1 ? pointSum + pointSum : pointSum;
                    double cost = scoreSum / stepWeight;
                    if (maxCost < cost) {
                        maxCost = cost;
                        nextPoint = i;
                        minWeight = stepWeight;
                        maxPointSum = scoreSum;
                        stepEdgeRoot = stepEdgesRoot.get(i);
                    }
                }
            }

            if (maxPointSum * 5d <= maxSum && (carSum + maxSum > LIMIT_SUM) && currPoint != ROOT_POINT) {
                nextPoint = ROOT_POINT;
            }

         /*   if (rootWeight != 0d && (maxPointSum/(6.75d * minWeight)) < (maxSum / (3 * rootWeight))) {
                nextPoint = ROOT_POINT;
            }*/

            // если времени не хватает на сбор денег и достаточно для перехода в точку сбора напрямую, идем в точку сбора
            //todo: remove car count
            if ((minWeight + stepEdgeRoot > (remainTime / (double) carCount)) && (rootWeight <= remainTime) && currPoint != ROOT_POINT) {
                nextPoint = ROOT_POINT;
            }
            time +=minWeight;
            System.out.println("next point:" + nextPoint + ", sum: " + pointMap.get(nextPoint) + ", time to root: " + minWeight + stepEdgeRoot + ", duration:" + remainTime);
        }


        return nextPoint;
    }
}
