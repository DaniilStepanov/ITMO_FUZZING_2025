package org.itmo.fuzzing.lect3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class PowerSchedule {
    public Map<Integer, Integer> pathFrequency;
    private Random random;

    /**
     * Constructor
     */
    public PowerSchedule() {
        this.pathFrequency = new HashMap<>();
        this.random = new Random();
    }

    /**
     * Assigns each seed the same energy
     */
    public void assignEnergy(List<Seed> population) {
        for (Seed seed : population) {
            seed.setEnergy(1.0);
        }
    }

    /**
     * Normalize energy
     */
    public List<Double> normalizedEnergy(List<Seed> population) {
        List<Double> energy = population.stream()
                .map(Seed::getEnergy)
                .toList();

        double sumEnergy = energy.stream().mapToDouble(Double::doubleValue).sum();
        assert sumEnergy != 0;

        return energy.stream()
                .map(nrg -> nrg / sumEnergy)
                .collect(Collectors.toList());
    }

    /**
     * Choose weighted by normalized energy.
     */
    public Seed choose(List<Seed> population) {
        assignEnergy(population);
        List<Double> normEnergy = normalizedEnergy(population);

        double totalWeight = normEnergy.stream().mapToDouble(Double::doubleValue).sum();
        double randomValue = random.nextDouble() * totalWeight;

        for (int i = 0; i < population.size(); i++) {
            randomValue -= normEnergy.get(i);
            if (randomValue <= 0) {
                return population.get(i);
            }
        }

        // This should never happen if the weights sum to 1
        return population.get(population.size() - 1);
    }
}