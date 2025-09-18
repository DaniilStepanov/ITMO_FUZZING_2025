package org.itmo.fuzzing.lect2;

import org.itmo.fuzzing.lect1.SimpleFuzz;
import org.itmo.fuzzing.lect2.instrumentation.CoverageAgent;
import org.itmo.fuzzing.lect2.instrumentation.CoverageTracker;

import java.util.*;

public class CgiDecoder {

    public static String cgiDecode(String s) {
        // Mapping of hex digits to their integer values
        Map<Character, Integer> hexValues = new HashMap<>();
        hexValues.put('0', 0); hexValues.put('1', 1); hexValues.put('2', 2); hexValues.put('3', 3); hexValues.put('4', 4);
        hexValues.put('5', 5); hexValues.put('6', 6); hexValues.put('7', 7); hexValues.put('8', 8); hexValues.put('9', 9);
        hexValues.put('a', 10); hexValues.put('b', 11); hexValues.put('c', 12); hexValues.put('d', 13); hexValues.put('e', 14); hexValues.put('f', 15);
        hexValues.put('A', 10); hexValues.put('B', 11); hexValues.put('C', 12); hexValues.put('D', 13); hexValues.put('E', 14); hexValues.put('F', 15);

        StringBuilder t = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '+') {
                t.append(' ');
            } else if (c == '%') {
                char digitHigh = s.charAt(i + 1);
                char digitLow = s.charAt(i + 2);
                i += 2;
                if (hexValues.containsKey(digitHigh) && hexValues.containsKey(digitLow)) {
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

    public static void main(String[] args) {
        // Example usage
        String encoded = "Hello+World%21";
        String decoded = cgiDecode(encoded);
        System.out.println("DECODED = " + decoded);
//        cgiDecode("+");
//        cgiDecode("%20");
//        cgiDecode("abc");
//        System.out.println("COVERAGE = ");
//        System.out.println(CoverageTracker.coverage.size());
//        CoverageTracker.coverage.stream().forEach(System.out::println);
//        try {
//            cgiDecode("%?a");
//        } catch (Throwable e) {
//        }
//        SimpleFuzz sf = new SimpleFuzz();
//        for (int i = 0; i < 10_000; i++) {
//            String input = sf.fuzzer(10, 32, 91);
//            try {
//                cgiDecode(input);
//            }catch (IllegalArgumentException e) {
//
//            } catch (Exception e) {
//                System.out.println("BUG FOUND INPUT = " + input);
//            }
//            System.out.println(CoverageTracker.getCoverageStatsFor("cgiDecode"));
//        }


//        System.out.println("Decoded string: " + decoded);  // Output: "Hello World!"
//        for (Map.Entry<String, TreeSet<String>> entry : CoverageTracker.coverage) {
//            String key = entry.getKey();
//            TreeSet<String> valueList = entry.getValue();
//            System.out.println("Method: " + key);
//            System.out.println("Lines: ");
//            valueList.forEach(System.out::println);
//        }


        //        String seed = "http://www.google.com/search?q=fuzzing";
//        FunctionRunner fr = new FunctionRunner(s -> URLValidator.httpProgram(s));
//        MutationCoverageFuzzer mutationCoverageFuzzer = new MutationCoverageFuzzer(
//                Arrays.asList(seed),
//                5,
//                100
//        ) {
//            @Override
//            public String mutate(String input) {
//                return StringMutator.mutate(input);
//            }
//        };
//        mutationCoverageFuzzer.fuzz(fr);
    }

}
