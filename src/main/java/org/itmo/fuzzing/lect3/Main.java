package org.itmo.fuzzing.lect3;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        var population = Arrays.asList(new Seed("A"), new Seed("B"), new Seed("C"));
        var schedule = new PowerSchedule();
        var hits = new HashMap<String, Integer>();
        hits.put("A", 0);
        hits.put("B", 0);
        hits.put("C", 0);
        for (int i = 0; i < 10_000; i++) {
            var seed = schedule.choose(population);
            hits.put(seed.getData(), hits.get(seed.getData()) + 1);
        }
        for (var h : hits.entrySet()) {
            System.out.println(h.getKey() + ": " + h.getValue());
        }
    }
}
