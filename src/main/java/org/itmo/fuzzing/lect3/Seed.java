package org.itmo.fuzzing.lect3;

import java.util.Set;
import java.util.TreeSet;

public class Seed {
    private String data;
    public TreeSet<Location> coverage;
    private double distance;
    private double energy;

    /**
     * Initialize from seed data
     */
    public Seed(String data) {
        this.data = data;
        this.coverage = new TreeSet<>();
        this.distance = -1;
        this.energy = 0.0;
    }

    /**
     * Returns data as string representation of the seed
     */
    @Override
    public String toString() {
        return this.data;
    }

    // Getters and setters

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<Location> getCoverage() {
        return coverage;
    }

    public void setCoverage(TreeSet<Location> coverage) {
        this.coverage = coverage;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
}
