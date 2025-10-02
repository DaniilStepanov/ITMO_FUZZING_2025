package org.itmo.fuzzing.lect4;


import java.util.function.Function;

public class TriangleTest {

    public void strongOracle() {
        Function<Integer[], String> fn = args -> Triangle.classifyTriangle(args[0], args[1], args[2]);

        assertEquals("Equilateral", fn.apply(new Integer[]{1, 1, 1}));
        assertEquals("Isosceles", fn.apply(new Integer[]{1, 2, 1}));
        assertEquals("Isosceles", fn.apply(new Integer[]{2, 2, 1}));
        assertEquals("Isosceles", fn.apply(new Integer[]{1, 2, 2}));
        assertEquals("Scalene", fn.apply(new Integer[]{1, 2, 3}));
    }

    public void weakOracle() {
        Function<Integer[], String> fn = args -> Triangle.classifyTriangle(args[0], args[1], args[2]);

        assertEquals("Equilateral", fn.apply(new Integer[]{1, 1, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{2, 2, 1}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 2}));
        assertNotEquals("Equilateral", fn.apply(new Integer[]{1, 2, 3}));
    }


    private void assertEquals(String equilateral, String apply) {
        if (!equilateral.equals(apply)) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }

    private void assertNotEquals(String equilateral, String apply) {
        if (equilateral.equals(apply)) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }


}