package org.itmo.fuzzing.lect2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MutationFuzzer {
    protected List<String> seeds;
    protected int minMutations;
    protected int maxMutations;
    protected List<String> population;
    protected int seedIndex;
    private static final Random random = new Random();

    /**
     * Конструктор.
     *
     * @param seeds - список строк для мутации.
     * @param minMutations - минимальное количество мутаций.
     * @param maxMutations - максимальное количество мутаций.
     */
    public MutationFuzzer(List<String> seeds, int minMutations, int maxMutations) {
        this.seeds = seeds;
        this.population = new ArrayList<>(seeds);
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
    }

    /**
     * Устанавливает популяцию на начальный набор.
     * Должен быть переопределен в подклассах.
     */
    public void reset() {
        this.population = new ArrayList<>(seeds);
        this.seedIndex = 0;
    }

    public String createCandidate() {
        String candidate = population.get(random.nextInt(population.size()));
        int trials = random.nextInt(maxMutations - minMutations + 1) + minMutations;
        for (int i = 0; i < trials; i++) {
            candidate = mutate(candidate);
        }
        return candidate;
    }

    public String fuzz() {
        String inp = "";
        //Сначала проходимся по существующим сидам
        if (seedIndex < seeds.size()) {
            inp = seeds.get(seedIndex);
            seedIndex++;
        } else {
        //Затем начинаем мутировать
            inp = createCandidate();
        }
        return inp;
    }

    // Пример метода мутации (может быть реализован в подклассах)
    public abstract String mutate(String input);

    // Геттеры и сеттеры для полей, если нужно

    public List<String> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public int getMinMutations() {
        return minMutations;
    }

    public void setMinMutations(int minMutations) {
        this.minMutations = minMutations;
    }

    public int getMaxMutations() {
        return maxMutations;
    }

    public void setMaxMutations(int maxMutations) {
        this.maxMutations = maxMutations;
    }

    public List<String> getPopulation() {
        return population;
    }

    public void setPopulation(List<String> population) {
        this.population = population;
    }

    public int getSeedIndex() {
        return seedIndex;
    }

    public void setSeedIndex(int seedIndex) {
        this.seedIndex = seedIndex;
    }
}
