package org.itmo.fuzzing.lect4;

import java.util.function.Function;
import java.util.function.Supplier;

public class TriangleCalculatorTests {

    private static final double DELTA = 1e-6;

    public static void main(String[] args) {
        TriangleCalculator.TriangleProperties props = TriangleCalculator.calculateProperties(5, 5, 5);
        assertEquals(10.825317547305483, props.area, DELTA);
        assertEquals(15, props.perimeter, DELTA);
        assertEquals(1.4433756729740645, props.inradius, DELTA);
        assertEquals(2.8867513459481287, props.circumradius, DELTA);
        TriangleCalculator.TriangleProperties props2 = TriangleCalculator.calculateProperties(3, 4, 5);
        assertEquals(6, props2.area, DELTA);
        assertEquals(12, props2.perimeter, DELTA);
        assertEquals(1, props2.inradius, DELTA);
        assertEquals(2.5, props2.circumradius, DELTA);
        TriangleCalculator.TriangleProperties props3 = TriangleCalculator.calculateProperties(7, 8, 9);
        assertEquals(26.832815729997478, props3.area, DELTA);
        assertEquals(24, props3.perimeter, DELTA);
        assertEquals(2.2360679774997898, props3.inradius, DELTA);
        assertThrows(() -> TriangleCalculator.calculateProperties(1, 2, 3));
    }

    private static void assertEquals(double equilateral, double apply, double delta) {
        if (Math.abs(equilateral - apply) > delta) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }

    private static void assertThrows(Supplier f) {
        try {
            f.get();
        } catch (Exception e) {
            return;
        }
        throw new AssertionError("Expected exception not thrown");
    }
}
