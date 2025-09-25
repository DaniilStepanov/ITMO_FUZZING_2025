package org.itmo.fuzzing.lab1;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MazeMutator {
    private static final List<String> availableMoves = Arrays.asList("D", "U", "L", "R");
    private static final Random random = new Random();

    // Удаление случайного символа из строки
    public static String insert(String s) {
        int pos = random.nextInt(s.length() + 1);
        int randomCharacterIndex =  random.nextInt(0, availableMoves.size());
        String randomMove = availableMoves.get(randomCharacterIndex);
        return s.substring(0, pos) + randomMove + s.substring(pos);
    }

    public static String append(String s) {
        int randomCharacterIndex =  random.nextInt(0, availableMoves.size());
        String randomMove = availableMoves.get(randomCharacterIndex);
        return s + randomMove;
    }

    public static String deleteLast(String s) {
        return s.substring(0, s.length() - 1);
    }

    public static String mutate(String input) {
        int randomInt = random.nextInt(4);
        switch (randomInt) {
            case 0:
                return MazeMutator.insert(input);
            case 1:
                return MazeMutator.append(input);
            default:
                return MazeMutator.deleteLast(input);
        }
    }
}
