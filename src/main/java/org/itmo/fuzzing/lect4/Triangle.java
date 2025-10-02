package org.itmo.fuzzing.lect4;

public class Triangle {

    public static String classifyTriangle(int a, int b, int c) {
        if (a == b) {
            if (b == c) {
                return "Equilateral";
            } else {
                return "Isosceles";
            }
        } else {
            if (b == c) {
                return "Isosceles";
            } else {
                if (a == c) {
                    return "Isosceles";
                } else {
                    return "Scalene";
                }
            }
        }
    }
}
