package org.itmo.fuzzing.lect4;

import java.util.function.Function;

public class WeakOracle {

    public static void main(String[] args) {
        Function<Integer[], String> fn = arguments -> Triangle.classifyTriangle(arguments[0], arguments[1], arguments[2]);

        assertEquals("Equilateral", fn.apply(new Integer[]{1, 1, 1}));
        assertEquals("Isosceles", fn.apply(new Integer[]{1, 2, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{2, 2, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 2}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 3}));
    }

    private static void assertEquals(String equilateral, String apply) {
        if (!equilateral.equals(apply)) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }

    private static void assertNotEquals(String equilateral, String apply) {
        if (equilateral.equals(apply)) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }

}
