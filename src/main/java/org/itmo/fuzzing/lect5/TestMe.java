package org.itmo.fuzzing.lect5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.ToDoubleBiFunction;

public class TestMe {

    private static final int MIN = -1000;
    private static final int MAX = 1000;
    private static double distance = Double.MAX_VALUE;
    public static final int LOG_VALUES = 20;

    public static boolean test_me(int x, int y) {
        if (x == 2 * (y + 1)) {
            return true;
        }
        return false;
    }

    public static List<int[]> neighbors(int x, int y) {
        List<int[]> neighbors = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx != 0 || dy != 0) && (MIN <= x + dx && x + dx <= MAX) && (MIN <= y + dy && y + dy <= MAX)) {
                    neighbors.add(new int[]{x + dx, y + dy});
                }
            }
        }

        return neighbors;
    }

    public static boolean testMeInstrumented(int x, int y) {
        distance = calculateDistance(x, y);
//        System.out.println("Инструментирование: Входные данные = (" + x + ", " + y + "), расстояние = " + distance);
        if (x == 2 * (y + 1)) {
            return true;
        } else {
            return false;
        }
    }

    public static double getFitness(int x, int y) {
        testMeInstrumented(x, y);
        return distance;
    }

    public static double calculateDistance(int x, int y) {
        return Math.abs(x - 2 * (y + 1));
    }

    public static void hillclimber(ToDoubleBiFunction<Integer, Integer> fitnessFunction) {
        Random random = new Random();

        // Create and evaluate starting point
        int x = random.nextInt(MAX - MIN + 1) + MIN;
        int y = random.nextInt(MAX - MIN + 1) + MIN;
        double fitness = fitnessFunction.applyAsDouble(x, y);
        System.out.printf("Initial value: %d, %d at fitness %.4f%n", x, y, fitness);
        int iterations = 0;
        int logs = 0;

        // Stop once we have found an optimal solution
        while (fitness > 0) {
            iterations++;
            // Move to first neighbor with a better fitness
            for (int[] neighbor : neighbors(x, y)) {
                int nextx = neighbor[0];
                int nexty = neighbor[1];
                double newFitness = fitnessFunction.applyAsDouble(nextx, nexty);

                // Smaller fitness values are better
                if (newFitness < fitness) {
                    x = nextx;
                    y = nexty;
                    fitness = newFitness;
                    if (logs < LOG_VALUES) {
                        System.out.printf("New value: %d, %d at fitness %.4f%n", x, y, fitness);
                    } else if (logs == LOG_VALUES) {
                        System.out.println("...");
                    }
                    logs++;
                    //Comment
                    break;
                }
            }

        }

        System.out.printf("Found optimum after %d iterations at %d, %d%n", iterations, x, y);
        System.out.println("With fitness value: " + fitnessFunction.applyAsDouble(x, y));
    }


    public static void main(String[] args) {
//        neighborsExample();
        hillclimber(TestMe::getFitness);
        //Steepest ascent hillclimbing
    }


    public static void neighborsExample() {
        int x = 523;
        int y = 402;
        System.out.println("Fitness value of ( " + x + ", " + y + " ) = " + getFitness(x, y));
        var neighbors = neighbors(x, y);
        for (int[] neighbor : neighbors) {
            System.out.println("Neighbor: " + neighbor[0] + ", " + neighbor[1] + " has fitness: " + getFitness(neighbor[0], neighbor[1]));
        }
    }



    public static void restartingHillclimber(ToDoubleBiFunction<Integer, Integer> fitnessFunction) {
        Random random = new Random();

        // Create and evaluate starting point
        int x = random.nextInt(MAX - MIN + 1) + MIN;
        int y = random.nextInt(MAX - MIN + 1) + MIN;
        double fitness = fitnessFunction.applyAsDouble(x, y);
        System.out.printf("Initial value: %d, %d at fitness %.4f%n", x, y, fitness);
        int iterations = 0;
        int logs = 0;

        // Stop once we have found an optimal solution
        while (fitness > 0) {
            boolean changed = false;
            iterations++;
            // Move to first neighbor with a better fitness
            for (int[] neighbor : neighbors(x, y)) {
                int nextx = neighbor[0];
                int nexty = neighbor[1];
                double newFitness = fitnessFunction.applyAsDouble(nextx, nexty);

                // Smaller fitness values are better
                if (newFitness < fitness) {
                    x = nextx;
                    y = nexty;
                    fitness = newFitness;
                    if (logs < LOG_VALUES) {
                        System.out.printf("New value: %d, %d at fitness %.4f%n", x, y, fitness);
                    } else if (logs == LOG_VALUES) {
                        System.out.println("...");
                    }
                    logs++;
                    changed = true;
                    break;
                }
            }
            if (!changed) {
                x = random.nextInt(MAX - MIN + 1) + MIN;
                y = random.nextInt(MAX - MIN + 1) + MIN;
                fitness = fitnessFunction.applyAsDouble(x, y);
            }
        }

        System.out.printf("Found optimum after %d iterations at %d, %d%n", iterations, x, y);
        System.out.println("With fitness value: " + fitnessFunction.applyAsDouble(x, y));
    }

}
