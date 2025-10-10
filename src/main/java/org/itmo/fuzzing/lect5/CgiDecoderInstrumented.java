package org.itmo.fuzzing.lect5;

import java.util.*;

public class CgiDecoderInstrumented {

    public static String cgiDecode(String s) {
        // Mapping of hex digits to their integer values
        Map<Character, Integer> hexValues = new HashMap<>();
        hexValues.put('0', 0);
        hexValues.put('1', 1);
        hexValues.put('2', 2);
        hexValues.put('3', 3);
        hexValues.put('4', 4);
        hexValues.put('5', 5);
        hexValues.put('6', 6);
        hexValues.put('7', 7);
        hexValues.put('8', 8);
        hexValues.put('9', 9);
        hexValues.put('a', 10);
        hexValues.put('b', 11);
        hexValues.put('c', 12);
        hexValues.put('d', 13);
        hexValues.put('e', 14);
        hexValues.put('f', 15);
        hexValues.put('A', 10);
        hexValues.put('B', 11);
        hexValues.put('C', 12);
        hexValues.put('D', 13);
        hexValues.put('E', 14);
        hexValues.put('F', 15);
        StringBuilder t = new StringBuilder();
        int i = 0;
        while (BranchDistance.evaluateCondition(4, "LESS", i, s.length())) {
            char c = s.charAt(i);
            if (BranchDistance.evaluateCondition(3, "EQUALS", c, '+')) {
                t.append(' ');
            } else if (BranchDistance.evaluateCondition(2, "EQUALS", c, '%')) {
                char digitHigh = s.charAt(i + 1);
                char digitLow = s.charAt(i + 2);
                i += 2;
                if (BranchDistance.evaluateCondition(0, "IN", digitHigh, hexValues) && BranchDistance.evaluateCondition(1, "IN", digitLow, hexValues)) {
                    int v = hexValues.get(digitHigh) * 16 + hexValues.get(digitLow);
                    t.append((char) v);
                } else {
                    throw new IllegalArgumentException("Invalid encoding");
                }
            } else {
                t.append(c);
            }
            i++;
        }
        return t.toString();
    }

}