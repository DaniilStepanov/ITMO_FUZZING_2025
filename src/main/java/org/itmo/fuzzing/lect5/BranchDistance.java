package org.itmo.fuzzing.lect5;

import java.util.HashMap;
import java.util.Map;

public class BranchDistance {


    public static Map<Integer, Integer> distancesTrue = new HashMap<>();
    public static Map<Integer, Integer> distancesFalse = new HashMap<>();


    public static boolean evaluateCondition(int num, String op, Object lhs, Object rhs) {
        int distanceTrue = 0;
        int distanceFalse = 0;

        // Убеждаемся, что расстояние можно вычислить для числовых и символьных сравнений
        if (lhs instanceof String) {
            lhs = (int) ((String) lhs).charAt(0);
        }
        if (rhs instanceof String) {
            rhs = (int) ((String) rhs).charAt(0);
        }

        if (lhs instanceof Character) {
            lhs = (int) ((Character) lhs).charValue();
        }

        if (rhs instanceof Character) {
            rhs = (int) ((Character) rhs).charValue();
        }

        if (op.equals("EQUALS")) {
            if (lhs.equals(rhs)) {
                distanceFalse = 1;
            } else {
                distanceTrue = Math.abs((int) lhs - (int) rhs);
            }
        } else if (op.equals("LESS")) {
            if ((int) lhs < (int) rhs) {
                distanceFalse = (int) rhs - (int) lhs;
            } else {
                distanceTrue = (int) lhs - (int) rhs + 1;
            }
        } else if (op.equals("IN")) {
            int minimum = Integer.MAX_VALUE;
            if (rhs instanceof Map) {
                for (Object elem : ((Map<?, ?>) rhs).keySet()) {
                    int distance = Math.abs((Integer) lhs - (int) ((Character) elem).charValue());
                    if (distance < minimum) {
                        minimum = distance;
                    }
                }
                distanceTrue = minimum;
                if (distanceTrue == 0) {
                    distanceFalse = 1;
                }
            }
        }

        updateMaps(num, distanceTrue, distanceFalse);

        return distanceTrue == 0;
    }

    private static void updateMaps(int conditionNum, int dTrue, int dFalse) {
        if (distancesTrue.containsKey(conditionNum)) {
            distancesTrue.put(conditionNum, Math.min(distancesTrue.get(conditionNum), dTrue));
        } else {
            distancesTrue.put(conditionNum, dTrue);
        }

        if (distancesFalse.containsKey(conditionNum)) {
            distancesFalse.put(conditionNum, Math.min(distancesFalse.get(conditionNum), dFalse));
        } else {
            distancesFalse.put(conditionNum, dFalse);
        }
    }

}
