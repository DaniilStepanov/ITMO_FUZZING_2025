package org.itmo.fuzzing.lect5;

public class TestMe2 {

    private static double distance = Double.MAX_VALUE;

    public static boolean testMe2(int x, int y) {
        if (x * x == y * y * (x % 20)) {
            return true;
        }
        return false;
    }

    public static boolean testMe2Instrumented(int x, int y) {
        distance = Math.abs(x * x - y * y * (x % 20));
        if (x * x == y * y * (x % 20)) {
            return true;
        }
        return false;
    }

    public static double badFitness(int x, int y) {
        testMe2Instrumented(x, y);
        return distance;
    }

    public static void main(String[] args) {
//        neighborsExample();
        TestMe.restartingHillclimber(TestMe2::badFitness);
        //Steepest ascent hillclimbing
    }



}
