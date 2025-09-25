package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect9.FuzzMutator;

import java.util.Arrays;

public class CrashMeFuzzer {

    public static void main(String[] args) {
        var crashMeRunner = new FunctionRunner(s -> {
            CrashMe.crashMe(s);
            return null;
        });
//        List<String> seeds,
//        StringMutator mutator,
//        PowerSchedule schedule,
//        int minMutations,
//        int maxMutations

        //Usage of AdvancedMutationFuzzer
        var advancedFuzzer = new AdvancedMutationFuzzer(
                Arrays.asList("good"),
                new FuzzMutator(),
                new PowerSchedule(),
                1,
                5
        );
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());
//        System.exit(0);

        int N = 30_000;
//        var startTime = System.currentTimeMillis();
//        advancedFuzzer.fuzz(crashMeRunner, N);
//        var endTime = System.currentTimeMillis();
//        System.out.println("COVERAGE: ");
//        System.out.println("Time taken: " + (endTime - startTime));
//        advancedFuzzer.population.forEach(System.out::println);
//        System.out.println(advancedFuzzer.coveragesSeen.stream().filter(c -> c.contains("crashMe")).collect(Collectors.toSet()));
//
//        System.exit(0);


//        var greyBoxFuzzer = new GreyBoxFuzzer(
//                Arrays.asList("good"),
//                new FuzzMutator(),
//                new PowerSchedule(),
//                1,
//                5
//        );
//        var startTime = System.currentTimeMillis();
//        greyBoxFuzzer.fuzz(crashMeRunner, N);
//        var endTime = System.currentTimeMillis();
//        var coverage = greyBoxFuzzer.coveragesSeen;
//        coverage.stream().filter(c -> c.contains("crashMe")).forEach(System.out::println);
//        System.out.println("Time taken: " + (endTime - startTime));
//        System.exit(0);


        var countingFuzzer = new CountingGreyboxFuzzer(
                Arrays.asList("good"),
                new FuzzMutator(),
                new AFLFastSchedule(5.0),
                1,
                5
        );
        var startTime = System.currentTimeMillis();
        countingFuzzer.fuzz(crashMeRunner, N);
        var endTime = System.currentTimeMillis();
        var coverage = countingFuzzer.coveragesSeen;
        coverage.stream().filter(c -> c.contains("crashMe")).forEach(System.out::println);
        System.out.println("Time taken: " + (endTime - startTime));
    }

}
