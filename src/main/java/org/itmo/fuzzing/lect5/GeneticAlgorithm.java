package org.itmo.fuzzing.lect5;

import org.itmo.fuzzing.lect2.instrumentation.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.itmo.fuzzing.lect5.CgiDecode2.getFitnessCgi;
import static org.itmo.fuzzing.lect5.CgiDecode2.randomUnicodeString;
import static org.itmo.fuzzing.lect5.TestMe.LOG_VALUES;

public class GeneticAlgorithm {

    private static final Random random = new Random();


    public static String[] createPopulation(int size) {
        String[] population = new String[size];
        for (int i = 0; i < size; i++) {
            population[i] = randomUnicodeString(10);
        }
        return population;
    }

    public static List<Pair<String, Double>> evaluatePopulation(String[] population) {
        List<Pair<String, Double>> fitnessList = new ArrayList<>();
        for (String individual : population) {
            fitnessList.add(new Pair<>(individual, getFitnessCgi(individual)));
        }
        return fitnessList;
    }

    public static String selection(List<Pair<String, Double>> evaluatedPopulation, int tournamentSize) {
        List<Pair<String, Double>> competition = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(evaluatedPopulation.size());
            competition.add(evaluatedPopulation.get(randomIndex));
        }
        return competition.stream().min(Comparator.comparingDouble(Pair::getValue)).get().first;
    }

    public static String[] crossover(String parent1, String parent2) {
        int pos = random.nextInt(parent1.length());
        String offspring1 = parent1.substring(0, pos) + parent2.substring(pos);
        String offspring2 = parent2.substring(0, pos) + parent1.substring(pos);
        return new String[]{offspring1, offspring2};
    }

    public static String mutate(String chromosome) {
        StringBuilder mutated = new StringBuilder(chromosome);
        double P = 1.0 / mutated.length();

        for (int pos = 0; pos < mutated.length(); pos++) {
            if (random.nextDouble() < P) {
                int newChar = (int) (random.nextGaussian() + mutated.charAt(pos));
                mutated.setCharAt(pos, (char) (newChar % 65536));
            }
        }
        return mutated.toString();
    }

    public static void geneticAlgorithm() {
        int generation = 0;
        String[] population = createPopulation(100); // Начинаем с 100 особей
        List<Pair<String, Double>> fitness = evaluatePopulation(population);
        Pair<String, Double> best = fitness.stream().min(Comparator.comparingDouble(Pair::getValue)).get();
        String bestIndividual = best.getKey();
        double bestFitness = best.getValue();

        System.out.printf("Лучший фитнес начальной популяции: %s - %.10f%n", bestIndividual, bestFitness);
        int logs = 0;

        while (bestFitness > 0 && generation < 1_000_000) {
            List<String> newPopulation = new ArrayList<>();
            while (newPopulation.size() < population.length) {
                // Отбор
                String offspring1 = selection(fitness, 10);
                String offspring2 = selection(fitness, 10);

                // Кроссовер
                if (random.nextDouble() < 0.7) { // 70% вероятность выполнения кроссовера
                    String[] offspring = crossover(offspring1, offspring2);
                    offspring1 = offspring[0];
                    offspring2 = offspring[1];
                }

                // Мутация
                offspring1 = mutate(offspring1);
                offspring2 = mutate(offspring2);

                newPopulation.add(offspring1);
                newPopulation.add(offspring2);
            }

            generation++;
            population = newPopulation.toArray(new String[0]);
            fitness = evaluatePopulation(population);

            best = fitness.stream().min(Comparator.comparingDouble(Pair::getValue)).get();
            bestIndividual = best.getKey();
            bestFitness = best.getValue();
            if (logs < LOG_VALUES) {
                System.out.printf("Лучший фитнес на поколении %d: %s - %.8f%n", generation, bestIndividual, bestFitness);
            } else if (logs == LOG_VALUES) {
                System.out.println("...");
            }
            logs++;
        }

        System.out.printf("Лучший индивидуум: %s, фитнес %.10f%n", bestIndividual, bestFitness);
    }


}
