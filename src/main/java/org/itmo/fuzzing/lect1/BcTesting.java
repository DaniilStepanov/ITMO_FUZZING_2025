package org.itmo.fuzzing.lect1;

import java.io.*;

public class BcTesting {

    public static void main(String[] args) throws IOException {
        fuzzBc("2 + 2");
        SimpleFuzz sf = new SimpleFuzz();
        String data = sf.fuzzer(100, 32, 32);
        fuzzBc(data);
        for (int i = 0; i < 100; ++i) {
            fuzzBc(sf.fuzzer(100, 32, 32));
        }
    }

    public static void fuzzBc(String data) {
        File testFile = new File("src/main/java/org/itmo/fuzzing/lect1/in.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(data + "\nquit");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String program = "bc";
        try {
            ProcessBuilder builder = new ProcessBuilder(program, testFile.getAbsolutePath());

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            StringBuilder errorOutput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            String errorLine;
            while ((errorLine = errorStreamReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }

            // Ожидание завершения процесса и получение кода завершения
            int exitCode = process.waitFor();
            System.out.println("Output:\n" + output);
            System.out.println("Error output:\n" + errorOutput);
            System.out.println("Exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
