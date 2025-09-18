package org.itmo.fuzzing.lect1;

import java.util.Random;

public class SimpleFuzz {

    public String fuzzer(int max_length, int char_start, int char_range) {
        Random r = new Random();
        int length = r.nextInt(max_length);
        String out = "";
        for (int i = 0; i < length; i++) {
            out += (char) (r.nextInt(char_range) + char_start);
        }
        return out;
    }

    public static void main(String[] args) {
        SimpleFuzz fuzz = new SimpleFuzz();
        System.out.println(fuzz.fuzzer(100, 'a', 26));
    }
}
