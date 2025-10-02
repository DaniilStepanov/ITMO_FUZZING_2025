package org.itmo.fuzzing.lect4;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.itmo.fuzzing.lect3.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MuFunctionAnalyzer {

    private final String name;
    private CompilationUnit ast;
    private final Mutator mutator;
    private final int nmutations;
    public List<Mutant> mutants;
    public String sourceCode;
    public String pathToSrc;

    public MuFunctionAnalyzer(MethodDeclaration fn, CompilationUnit ast, Mutator mutator, String sourceCode, String pathToSrc) {
        this.name = fn.getNameAsString();
        this.ast = ast;
        this.mutator = mutator;
        this.nmutations = getMutationCount();
        this.mutants = new ArrayList<>();
        this.sourceCode = sourceCode;
        this.pathToSrc = pathToSrc;
    }

    public int getMutationCount() {
        mutator.visit(ast, null);
        return mutator.count;
    }

    public void generateMutants() {
        for (int i = 0; i < nmutations; i++) {
            int mutantsNumber = mutator.getMutationsPerStatement();
            for (int j = 0; j < mutantsNumber; j++) {
                ast = StaticJavaParser.parse(sourceCode);
                mutator.reset();
                mutator.mutationLocation = i + 1;
                mutator.ind = j;
                var mutatedCode = mutator.mutableVisit(ast);
                var mutant = new Mutant(
                        new Location("", -1, name),
                        "mutant_" + i,
                        mutatedCode.toString()
                );
                mutants.add(mutant);
            }
        }
    }

    public double executeMutants(String className) throws IOException, InterruptedException {
        int errors = 0;
        int compilableMutants = 0;
        for (var m : mutants) {
            var oldText = Files.readString(Path.of(pathToSrc));
            try {
                Files.writeString(Path.of(pathToSrc), m.src);
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("gradle", "build");
                var gradleProcess = processBuilder.start();
                StringBuilder stdout = new StringBuilder();
                StringBuilder stderr = new StringBuilder();

                try (var reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(gradleProcess.getInputStream()));
                     var errReader = new java.io.BufferedReader(
                             new java.io.InputStreamReader(gradleProcess.getErrorStream()))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        stdout.append(line).append(System.lineSeparator());
                    }

                    while ((line = errReader.readLine()) != null) {
                        stderr.append(line).append(System.lineSeparator());
                    }
                }
                gradleProcess.waitFor();
                if (!stdout.toString().contains("BUILD SUCCESSFUL")) {
                    System.out.println("CANNOT COMPILE MUTANT");
                    continue;
                } else {
                    compilableMutants++;
                }

                ProcessBuilder processBuilder2 = new ProcessBuilder();
                processBuilder2.command("java", "-cp", "build/classes/java/main", className);

                var process = processBuilder2.start();
                try {
                    if (process.waitFor(3, TimeUnit.SECONDS)) {
                        if (process.exitValue() != 0) {
                            errors++;
                        }
                        System.out.println("EXIT VALUE = " + process.exitValue());
                    } else {
                        errors++;
                    }
                } catch (IllegalThreadStateException e) {
                    continue;
                }

            } finally {
                Files.writeString(Path.of(pathToSrc), oldText);
            }
        }
        return (double) errors / compilableMutants;
    }

}