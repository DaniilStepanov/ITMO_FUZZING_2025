package org.itmo.fuzzing.lab1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MazeGenerator {

    public static String generatePrintMaze(String mazeString) {
        return String.format("""
                    public static String printMaze(String out, int row, int col) {
                        String maze = "+-+-----+\\n" +
                                "|X|     |\\n" +
                                "| | --+ |\\n" +
                                "| |   | |\\n" +
                                "| +-- | |\\n" +
                                "|     |#|\\n" +
                                "+-----+-+\\n";
                        StringBuilder output = new StringBuilder(out + "\\n");
                        int cRow = 0;
                        int cCol = 0;
                        for (char c : maze.toCharArray()) {
                            if (c == '\\n') {
                                cRow++;
                                cCol = 0;
                                output.append("\\n");
                            } else {
                                if (cRow == row && cCol == col) output.append("X");
                                else if (c == 'X') output.append(" ");
                                else output.append(c);
                                cCol++;
                            }
                        }
                        return output.toString();
                    }
                """);
    }

    public static String generateTrapTile(int row, int col) {
        return String.format("""
                    public static String tile_%d_%d(char[] input, int index) {
                        return printMaze("INVALID", %d, %d);
                    }
                """, row, col, row, col);
    }

    public static String generateGoodTile(char c, int row, int col) {
        String code = String.format("""
                            public static String tile_%d_%d(char[] input, int index) {
                                if (index == input.length) return printMaze("VALID", %d, %d);
                                else if (input[index] == 'L') return tile_%d_%d(input, index + 1);
                                else if (input[index] == 'R') return tile_%d_%d(input, index + 1);
                                else if (input[index] == 'U') return tile_%d_%d(input, index + 1);
                                else if (input[index] == 'D') return tile_%d_%d(input, index + 1);
                                else return tile_%d_%d(input, index + 1);
                            }
                        """, row, col, row, col,
                row, col - 1,
                row, col + 1,
                row - 1, col,
                row + 1, col,
                row, col);

        if (c == 'X') {
            code += String.format("""
                        public static String maze(String input) {
                            return tile_%d_%d(input.toCharArray(), 0);
                        }
                    """, row, col);
        }

        return code;
    }

    public static String generateTargetTile(int row, int col) {
        return String.format("""
                    public static String tile_%d_%d(char[] input, int index) {
                        return printMaze("SOLVED", %d, %d);
                    }

                    public static String targetTile() {
                        return "tile_%d_%d";
                    }
                """, row, col, row, col, row, col);
    }

    public static String generateMazeCode(String maze) {
        int row = 0;
        int col = 0;
        StringBuilder code = new StringBuilder(generatePrintMaze(maze));

        for (char c : maze.toCharArray()) {
            if (c == '\n') {
                row++;
                col = 0;
            } else {
                if (c == '-' || c == '+' || c == '|') {
                    code.append(generateTrapTile(row, col));
                } else if (c == ' ' || c == 'X') {
                    code.append(generateGoodTile(c, row, col));
                } else if (c == '#') {
                    code.append(generateTargetTile(row, col));
                } else {
                    System.out.println("Invalid maze! Try another one.");
                }
                col++;
            }
        }

        return code.toString();
    }

    public static void main(String[] args) throws IOException {
        String maze = """
                +-+-----+
                |X|     |
                | | --+ |
                | |   | |
                | +-- | |
                |     |#|
                +-----+-+
                """;

        File f = new File("src/main/java/org/itmo/fuzzing/lab1/MazeGenerated.java");
        String header = "package org.itmo.fuzzing.lab1;\n" +
                "\n" +
                "import org.jsoup.Jsoup;\n" +
                "import java.util.Arrays;\n\n";
        StringBuilder generated = new StringBuilder();
        generated.append(header + "public class MazeGenerated {\n" + generateMazeCode(maze) + "\n }");
        FileWriter fw = new FileWriter(f);
        fw.write(generated.toString());
        fw.flush();
        fw.close();
    }
}
