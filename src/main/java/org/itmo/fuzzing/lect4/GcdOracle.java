package org.itmo.fuzzing.lect4;

import java.util.function.Function;

public class GcdOracle {

    public static void main(String[] args) {
        Function<Integer[], Integer> fn = arguments -> GCDCalculator.gcd(arguments[0], arguments[1]);
        assertEquals(1, fn.apply(new Integer[]{1, 0}));
        assertEquals(1, fn.apply(new Integer[]{0, 1}));
    }

    private static void assertEquals(Integer equilateral, Integer apply) {
        if (!equilateral.equals(apply)) {
            throw new AssertionError(equilateral + " != " + apply);
        }
    }

}
