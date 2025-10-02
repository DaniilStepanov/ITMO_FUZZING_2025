package org.itmo.fuzzing.lect4;

public class TriangleCalculator {

    public static class TriangleProperties {
        public double area;
        public double perimeter;
        public double inradius;
        public double circumradius;

        public TriangleProperties(double area, double perimeter, double inradius, double circumradius) {
            this.area = area;
            this.perimeter = perimeter;
            this.inradius = inradius;
            this.circumradius = circumradius;
        }
    }

    public static TriangleProperties calculateProperties(int a, int b, int c) {
        // Проверка на существование треугольника
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Such triangle does not exist");
        }

        // Вычисление полупериметра
        double s = (a + b + c) / 2.0;

        // Вычисление площади по формуле Герона
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

        // Вычисление периметра
        double perimeter = a + b + c;

        // Вычисление радиуса вписанной окружности
        double inradius = area / s;

        // Вычисление радиуса описанной окружности
        double circumradius = (a * b * c) / (4 * area);

        return new TriangleProperties(area, perimeter, inradius, circumradius);
    }
}
