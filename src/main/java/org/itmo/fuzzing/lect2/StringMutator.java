package org.itmo.fuzzing.lect2;

import java.util.Random;

public class StringMutator {

    private static final Random random = new Random();

    // Удаление случайного символа из строки
    public static String deleteRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }
        int pos = random.nextInt(s.length());
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    // Вставка случайного символа в строку
    public static String insertRandomCharacter(String s) {
        int pos = random.nextInt(s.length() + 1);
        char randomCharacter = (char) (random.nextInt(96) + 32); // Генерация случайного символа в диапазоне ASCII (32-126)
        return s.substring(0, pos) + randomCharacter + s.substring(pos);
    }

    // Изменение случайного бита в случайном символе строки
    public static String flipRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }

        int pos = random.nextInt(s.length());
        char c = s.charAt(pos);
        int bit = 1 << random.nextInt(7); // Генерация случайного бита (от 0 до 6)
        char newC = (char) (c ^ bit); // Изменение бита в символе
        return s.substring(0, pos) + newC + s.substring(pos + 1);
    }

    public static String mutate(String input) {
        int randomInt = random.nextInt(4);
        switch (randomInt) {
            case 0:
                 return StringMutator.flipRandomCharacter(input);
            case 1:
                return StringMutator.deleteRandomCharacter(input);
            default:
                return StringMutator.insertRandomCharacter(input);
        }
    }

    // Пример использования
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String input = "example";
            String result = mutate(input);
            System.out.println("Original string: " + input);
            System.out.println("Modified string: " + result);
        }
        System.out.println("--------------");
        String input = "example";
        for (int i = 0; i < 10; i++) {
            input = mutate(input);
            System.out.println("Modified string: " + input);
        }
        //Теперь идем мутировать URL
    }



    // Method to flip bits in a row, from 1 to 4 bits
    public static byte[] walkingBitFlips(byte[] input, int bitsToFlip) {
        byte[] mutatedInput = input.clone();
        int numBits = bitsToFlip;

        for (int i = 0; i < mutatedInput.length * 8 - numBits + 1; i++) {
            // Flip the bits by shifting and XORing
            for (int j = 0; j < numBits; j++) {
                int byteIndex = (i + j) / 8;
                int bitIndex = (i + j) % 8;
                mutatedInput[byteIndex] ^= (1 << bitIndex);  // Flip the bit
            }
        }
        return mutatedInput;
    }

    public static byte[] walkingByteFlips(byte[] input, int numBytes) {
        byte[] mutatedInput = input.clone();
        for (int i = 0; i <= input.length - numBytes; i++) {
            for (int j = 0; j < numBytes; j++) {
                mutatedInput[i + j] ^= 0xFF;  // Flip the byte
            }
        }
        return mutatedInput;
    }

    public static byte[] simpleArithmetic(byte[] input, int range) {
        byte[] mutatedInput = input.clone();

        // 8-bit arithmetic mutations
        for (int i = 0; i < mutatedInput.length; i++) {
            mutatedInput[i] = (byte)(mutatedInput[i] + range);  // Add the range
        }

        // 16-bit mutations (little endian and big endian)
        for (int i = 0; i < mutatedInput.length - 1; i++) {
            short original = (short)((mutatedInput[i] & 0xFF) | (mutatedInput[i + 1] << 8));
            short newValue = (short)(original + range);
            mutatedInput[i] = (byte)(newValue & 0xFF);
            mutatedInput[i + 1] = (byte)((newValue >> 8) & 0xFF);
        }

        // 32-bit mutations
        for (int i = 0; i < mutatedInput.length - 3; i++) {
            int original = (mutatedInput[i] & 0xFF) |
                    ((mutatedInput[i + 1] & 0xFF) << 8) |
                    ((mutatedInput[i + 2] & 0xFF) << 16) |
                    ((mutatedInput[i + 3] & 0xFF) << 24);
            int newValue = original + range;
            mutatedInput[i] = (byte)(newValue & 0xFF);
            mutatedInput[i + 1] = (byte)((newValue >> 8) & 0xFF);
            mutatedInput[i + 2] = (byte)((newValue >> 16) & 0xFF);
            mutatedInput[i + 3] = (byte)((newValue >> 24) & 0xFF);
        }

        return mutatedInput;
    }

    public static byte[] knownIntegerMutations(byte[] input) {
        int[] knownIntegers = {-1, 256, 1024, Integer.MAX_VALUE - 1, Integer.MAX_VALUE};
        byte[] mutatedInput = input.clone();

        // 8-bit mutation
        for (int i = 0; i < mutatedInput.length; i++) {
            for (int known : knownIntegers) {
                mutatedInput[i] = (byte) known;  // Replace with known integer
            }
        }

        // 16-bit mutation
        for (int i = 0; i < mutatedInput.length - 1; i++) {
            for (int known : knownIntegers) {
                mutatedInput[i] = (byte)(known & 0xFF);
                mutatedInput[i + 1] = (byte)((known >> 8) & 0xFF);
            }
        }

        // 32-bit mutation
        for (int i = 0; i < mutatedInput.length - 3; i++) {
            for (int known : knownIntegers) {
                mutatedInput[i] = (byte)(known & 0xFF);
                mutatedInput[i + 1] = (byte)((known >> 8) & 0xFF);
                mutatedInput[i + 2] = (byte)((known >> 16) & 0xFF);
                mutatedInput[i + 3] = (byte)((known >> 24) & 0xFF);
            }
        }

        return mutatedInput;
    }

}