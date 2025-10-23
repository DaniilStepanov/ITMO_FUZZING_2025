package org.itmo.fuzzing.lect6.fuzzer;

import org.itmo.fuzzing.task1.DataParser;

import java.io.*;

public class DataGrammarFuzzerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        var grammar = DataGrammar.getBetterGrammar();
        var fuzzer = new GrammarFuzzer(grammar);
        var iter = 0L;
        while (true) {
            iter++;
            System.out.println("ITER = " + iter);

            var tree = fuzzer.initTree();
            var expanded = fuzzer.expandTree(tree);
            var string = expanded.treeToString();
            var writer = new FileWriter("data1.txt");
            writer.write(string);
            writer.close();
            var arr = new String[]{(new File("data1.txt").getAbsolutePath())};
            new DataParser().main(arr);
        }
    }
}
