package org.itmo.fuzzing.lect3;

import java.util.List;

public class AFLFastSchedule extends PowerSchedule {
    private double exponent;

    public AFLFastSchedule(double exponent) {
        this.exponent = exponent;
    }

    @Override
    public void assignEnergy(List<Seed> population) {
        for (Seed seed : population) {
            int pathId = PathIDGenerator.getPathID(seed.coverage);
            double frequency = getPathFrequency(pathId);
            seed.setEnergy(1 / Math.pow(frequency, exponent));
        }
    }

    private int getPathFrequency(int pathId) {
        return pathFrequency.getOrDefault(pathId, 0);
    }
}