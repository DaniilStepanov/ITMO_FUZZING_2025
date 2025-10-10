package org.itmo.fuzzing.lect5;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.itmo.fuzzing.lect2.StringMutator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class CgiDecode2 {

    private static Random random = new Random();

    public static List<String> neighborStrings(String x) {
        List<String> n = new ArrayList<>();
        for (int pos = 0; pos < x.length(); pos++) {
            char c = x.charAt(pos);
            if (c < 126) {
                n.add(x.substring(0, pos) + (char) (c + 1) + x.substring(pos + 1));
            }
            if (c > 32) {
                n.add(x.substring(0, pos) + (char) (c - 1) + x.substring(pos + 1));
            }
        }
        return n;
    }

    public static int distanceCharacter(int target, List<Integer> values) {
        // Initialize with very large value so that any comparison is better
        int minimum = Integer.MAX_VALUE;

        for (int elem : values) {
            int distance = Math.abs(target - elem);
            if (distance < minimum) {
                minimum = distance;
            }
        }
        return minimum;
    }

    public static double normalize(int x) {
        return x / (1.0 + x);
    }

    public static double getFitnessCgi(String s) {
        BranchDistance.distancesTrue.clear();
        BranchDistance.distancesFalse.clear();

        try {
            CgiDecoderInstrumented.cgiDecode(s);
        } catch (Throwable e) {
        }

        var fitness = 0.0;

        for (int branch : new int[]{0, 1, 2, 4}) {
            if (BranchDistance.distancesTrue.containsKey(branch)) {
                fitness += normalize(BranchDistance.distancesTrue.get(branch));
            } else {
                fitness += 1.0;
            }
        }

        for (int branch : new int[]{3}) {
            if (BranchDistance.distancesFalse.containsKey(branch)) {
                fitness += normalize(BranchDistance.distancesFalse.get(branch));
            } else {
                fitness += 1.0;
            }
        }

        return fitness;
    }

    public static void main(String[] args) throws IOException {
//        neighborStrings("Hello").forEach(System.out::println);
//        CgiDecoderInstrumented.cgiDecode("Hello+reader");
//        BranchDistance.distancesTrue.forEach((integer, integer2) -> System.out.println("Num:" + integer + " Distance: " + integer2));
//        System.out.println("--------------");
//        BranchDistance.distancesFalse.forEach((integer, integer2) -> System.out.println("Num:" + integer + " Distance: " + integer2));
//        System.out.println(getFitnessCgi(""));
//        System.out.println(getFitnessCgi("Hello+Reader"));
//        System.out.println(getFitnessCgi("%UU"));
//        System.out.println(getFitnessCgi("%AU"));
//        System.out.println(getFitnessCgi("%AA"));
//        hillclimbCgi();
//        hillclimbCgi(100_000);
//        randomizedHillclimbCgi();
//        var population = GeneticAlgorithm.createPopulation(10);
//        var evaluated = GeneticAlgorithm.evaluatePopulation(population);
//        var selected = GeneticAlgorithm.selection(evaluated, 10);
//        var parent1 = "Hello World";
//        var parent2 = "Goodbye Book";
//
//        Arrays.asList(GeneticAlgorithm.crossover(parent1, parent2)).forEach(System.out::println);
        GeneticAlgorithm.geneticAlgorithm();
    }

    public static void instrumentCGI() throws IOException {
//        String filePath = "/home/zver/IdeaProjects/FuzzingCourseCode2/src/main/java/org/itmo/fuzzing/lect2/CgiDecoder.java";
//        String code = new String(Files.readAllBytes(Paths.get(filePath)));
//        CompilationUnit cu = StaticJavaParser.parse(code);
//
//        // Создаем экземпляр Visitor для замены условий
//        cu.accept(new ConditionMutator(), null);
//
//        // Выводим измененный код
//        System.out.println(cu.toString());
    }


    public static void hillclimbCgi() {
        String x = randomString(10);
        double fitness = getFitnessCgi(x);
        System.out.println("Initial input: " + x + " at fitness " + String.format("%.4f", fitness));

        while (fitness > 0) {
            boolean changed = false;
            for (String nextX : neighborStrings(x)) {
                double newFitness = getFitnessCgi(nextX);
                if (newFitness < fitness) {
                    x = nextX;
                    fitness = newFitness;
                    changed = true;
                    System.out.println("New value: " + x + " at fitness " + String.format("%.4f", fitness));
                    break;
                }
            }

            // Random restart if necessary
            if (!changed) {
                x = randomString(10);
                fitness = getFitnessCgi(x);
            }
        }

        System.out.println("Optimum at " + x + ", fitness " + String.format("%.4f", fitness));
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomCharacter = (char) (random.nextInt(95) + 32);
            sb.append(randomCharacter);
        }
        return sb.toString();
    }

    public static String randomUnicodeString(int length) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // Ограничения для диапазона UTF-16
            int randomCharacter = random.nextInt(65536);
            s.append((char) randomCharacter);
        }
        return s.toString();
    }

    public static List<String> unicodeStringNeighbors(String x) {
        List<String> n = new ArrayList<>();
        for (int pos = 0; pos < x.length(); pos++) {
            char c = x.charAt(pos);
            // Ограничения для диапазона UTF-16
            if (c < 65535) {
                n.add(x.substring(0, pos) + (char) (c + 1) + x.substring(pos + 1));
            }
            if (c > 0) {
                n.add(x.substring(0, pos) + (char) (c - 1) + x.substring(pos + 1));
            }
        }
        return n;
    }

    public static void hillclimbCgi(int max_iterations) {
        String x = randomUnicodeString(10);
        double fitness = getFitnessCgi(x);
        System.out.println("Initial input: " + x + " at fitness " + String.format("%.4f", fitness));
        int iteration = 0;
        while (fitness > 0 && iteration < max_iterations) {
            boolean changed = false;
            for (String nextX : neighborStrings(x)) {
                double newFitness = getFitnessCgi(nextX);
                if (newFitness < fitness) {
                    x = nextX;
                    fitness = newFitness;
                    changed = true;
                    System.out.println("New value: " + x + " at fitness " + String.format("%.4f", fitness));
                    break;
                }
            }

            // Random restart if necessary
            if (!changed) {
                x = randomString(10);
                fitness = getFitnessCgi(x);
            }
            iteration++;
        }

        System.out.println("Optimum at " + x + ", fitness " + String.format("%.4f", fitness));
    }

    public static String flipRandomCharacter(String s) {
        int pos = random.nextInt(s.length());
        char newC = (char) random.nextInt(65536);
        return s.substring(0, pos) + newC + s.substring(pos + 1);
    }

    public static void randomizedHillclimbCgi() {
        String x = randomString(10);
        double fitness = getFitnessCgi(x);
        System.out.println("Initial input: " + x + " at fitness " + String.format("%.4f", fitness));

        while (fitness > 0) {
            boolean changed = false;
            var mutated = flipRandomCharacter(x);
            double newFitness = getFitnessCgi(mutated);
            //IMPORTANT
            if (newFitness <= fitness) {
                x = mutated;
                fitness = newFitness;
            }
        }

        System.out.println("Optimum at " + x + ", fitness " + String.format("%.4f", fitness));
    }
}
