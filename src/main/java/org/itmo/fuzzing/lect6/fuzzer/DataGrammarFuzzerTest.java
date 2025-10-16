package org.itmo.fuzzing.lect6.fuzzer;

import java.io.*;

public class DataGrammarFuzzerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        var grammar = DataGrammar.getBetterGrammar();
        var fuzzer = new GrammarFuzzer(grammar);
        var iter = 0L;
        while (true) {
            iter++;
            var tree = fuzzer.initTree();
            var expanded = fuzzer.expandTree(tree);
            var string = expanded.treeToString();
            var writer = new FileWriter("data1.txt");
            writer.write(string);
            writer.close();
            var processBuilder = new ProcessBuilder(
                    "java",
                    "-jar",
                    "/home/zver/IdeaProjects/FuzzingCourseCode2/src/main/java/target.jar",
                    "/home/zver/IdeaProjects/FuzzingCourseCode2/data1.txt"
            );
            var process = processBuilder.start();
//        // Capture output from stdout
//        try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line); // Output to console
//            }
//        }
//
//        // Capture output from stderr (optional, if you want to capture errors too)
        try (var errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine); // Output errors to console
            }
        }
            process.waitFor();
//            System.out.println(process.exitValue());
            if (process.exitValue() != 0) {
                System.out.println("I FOUND A BUG ON ITERATION " + iter);
                System.out.println("DATA:\n------\n" + string + "\n------\n");
                System.exit(0);
            }
        }
    }
}
