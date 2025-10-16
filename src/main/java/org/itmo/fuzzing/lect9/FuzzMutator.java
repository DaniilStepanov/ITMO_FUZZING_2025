package org.itmo.fuzzing.lect9;

import org.itmo.fuzzing.lect2.StringMutator;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FuzzMutator {
    protected List<MutatorFunction> mutators = new ArrayList<>();
    protected Random random = new Random(42);

    public FuzzMutator() {
        random = new Random();
        mutators = new ArrayList<>();
        mutators.add(this::deleteRandomCharacter);
        mutators.add(this::insertRandomCharacter);
        mutators.add(this::flipRandomCharacter);
    }

    public String insertRandomCharacter(String s) {
        int pos = random.nextInt(s.length() + 1);
        char randomCharacter = (char) (random.nextInt(95) + 32);
        return s.substring(0, pos) + randomCharacter + s.substring(pos);
    }

    public String deleteRandomCharacter(String s) {
        if (s.isEmpty()) {
            return insertRandomCharacter(s);
        }
        int pos = random.nextInt(s.length());
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    public String flipRandomCharacter(String s) {
        if (s.isEmpty()) {
            return insertRandomCharacter(s);
        }
        int pos = random.nextInt(s.length());
        char c = s.charAt(pos);
        int bit = 1 << random.nextInt(7);
        char newChar = (char) (c ^ bit);
        return s.substring(0, pos) + newChar + s.substring(pos + 1);
    }

    public String mutate(String s) {
        MutatorFunction mutator = mutators.get(random.nextInt(mutators.size()));
        return mutator.apply(s);
    }

    @FunctionalInterface
    protected interface MutatorFunction {
        String apply(String s);
    }
}

