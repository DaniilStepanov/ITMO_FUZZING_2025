package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect9.FuzzMutator;
import org.jsoup.Jsoup;

import java.util.Arrays;

public class JsoupHTMLParserExample {

    public static void main(String[] args) {
        var jsoupRunner = new FunctionRunner(s -> {
            Jsoup.parse(s);
            return null;
        });
        int N = 5_000;

        //<html><body><p>Hello</p></body></html>
        var fuzzer = new CountingGreyboxFuzzer(
                Arrays.asList("<html><body><p>Hello</p></body></html>"),
                new FuzzMutator(),
                new AFLFastSchedule(5.0),
                1,
                5
        );
        var startTime = System.currentTimeMillis();
        fuzzer.fuzz(jsoupRunner, N);
        var endTime = System.currentTimeMillis();
        var coverage = fuzzer.coveragesSeen;
//        coverage.forEach(System.out::println);
        System.out.println("Time taken: " + (endTime - startTime));
        System.out.println("COVERED = " + coverage.size());
    }
}
