package org.itmo.fuzzing.lab1;

public class MazeGenerated {
    public static String printMaze(String out, int row, int col) {
        String maze = "+-+-----+\n" +
                "|X|     |\n" +
                "| | --+ |\n" +
                "| |   | |\n" +
                "| +-- | |\n" +
                "|     |#|\n" +
                "+-----+-+\n";
        StringBuilder output = new StringBuilder(out + "\n");
        int cRow = 0;
        int cCol = 0;
        for (char c : maze.toCharArray()) {
            if (c == '\n') {
                cRow++;
                cCol = 0;
                output.append("\n");
            } else {
                if (cRow == row && cCol == col) output.append("X");
                else if (c == 'X') output.append(" ");
                else output.append(c);
                cCol++;
            }
        }
        return output.toString();
    }
    public static String tile_0_0(char[] input, int index) {
        return printMaze("INVALID", 0, 0);
    }
    public static String tile_0_1(char[] input, int index) {
        return printMaze("INVALID", 0, 1);
    }
    public static String tile_0_2(char[] input, int index) {
        return printMaze("INVALID", 0, 2);
    }
    public static String tile_0_3(char[] input, int index) {
        return printMaze("INVALID", 0, 3);
    }
    public static String tile_0_4(char[] input, int index) {
        return printMaze("INVALID", 0, 4);
    }
    public static String tile_0_5(char[] input, int index) {
        return printMaze("INVALID", 0, 5);
    }
    public static String tile_0_6(char[] input, int index) {
        return printMaze("INVALID", 0, 6);
    }
    public static String tile_0_7(char[] input, int index) {
        return printMaze("INVALID", 0, 7);
    }
    public static String tile_0_8(char[] input, int index) {
        return printMaze("INVALID", 0, 8);
    }
    public static String tile_1_0(char[] input, int index) {
        return printMaze("INVALID", 1, 0);
    }
    public static String tile_1_1(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 1);
        else if (input[index] == 'L') return tile_1_0(input, index + 1);
        else if (input[index] == 'R') return tile_1_2(input, index + 1);
        else if (input[index] == 'U') return tile_0_1(input, index + 1);
        else if (input[index] == 'D') return tile_2_1(input, index + 1);
        else return tile_1_1(input, index + 1);
    }
    public static String maze(String input) {
        return tile_1_1(input.toCharArray(), 0);
    }
    public static String tile_1_2(char[] input, int index) {
        return printMaze("INVALID", 1, 2);
    }
    public static String tile_1_3(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 3);
        else if (input[index] == 'L') return tile_1_2(input, index + 1);
        else if (input[index] == 'R') return tile_1_4(input, index + 1);
        else if (input[index] == 'U') return tile_0_3(input, index + 1);
        else if (input[index] == 'D') return tile_2_3(input, index + 1);
        else return tile_1_3(input, index + 1);
    }
    public static String tile_1_4(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 4);
        else if (input[index] == 'L') return tile_1_3(input, index + 1);
        else if (input[index] == 'R') return tile_1_5(input, index + 1);
        else if (input[index] == 'U') return tile_0_4(input, index + 1);
        else if (input[index] == 'D') return tile_2_4(input, index + 1);
        else return tile_1_4(input, index + 1);
    }
    public static String tile_1_5(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 5);
        else if (input[index] == 'L') return tile_1_4(input, index + 1);
        else if (input[index] == 'R') return tile_1_6(input, index + 1);
        else if (input[index] == 'U') return tile_0_5(input, index + 1);
        else if (input[index] == 'D') return tile_2_5(input, index + 1);
        else return tile_1_5(input, index + 1);
    }
    public static String tile_1_6(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 6);
        else if (input[index] == 'L') return tile_1_5(input, index + 1);
        else if (input[index] == 'R') return tile_1_7(input, index + 1);
        else if (input[index] == 'U') return tile_0_6(input, index + 1);
        else if (input[index] == 'D') return tile_2_6(input, index + 1);
        else return tile_1_6(input, index + 1);
    }
    public static String tile_1_7(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 1, 7);
        else if (input[index] == 'L') return tile_1_6(input, index + 1);
        else if (input[index] == 'R') return tile_1_8(input, index + 1);
        else if (input[index] == 'U') return tile_0_7(input, index + 1);
        else if (input[index] == 'D') return tile_2_7(input, index + 1);
        else return tile_1_7(input, index + 1);
    }
    public static String tile_1_8(char[] input, int index) {
        return printMaze("INVALID", 1, 8);
    }
    public static String tile_2_0(char[] input, int index) {
        return printMaze("INVALID", 2, 0);
    }
    public static String tile_2_1(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 2, 1);
        else if (input[index] == 'L') return tile_2_0(input, index + 1);
        else if (input[index] == 'R') return tile_2_2(input, index + 1);
        else if (input[index] == 'U') return tile_1_1(input, index + 1);
        else if (input[index] == 'D') return tile_3_1(input, index + 1);
        else return tile_2_1(input, index + 1);
    }
    public static String tile_2_2(char[] input, int index) {
        return printMaze("INVALID", 2, 2);
    }
    public static String tile_2_3(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 2, 3);
        else if (input[index] == 'L') return tile_2_2(input, index + 1);
        else if (input[index] == 'R') return tile_2_4(input, index + 1);
        else if (input[index] == 'U') return tile_1_3(input, index + 1);
        else if (input[index] == 'D') return tile_3_3(input, index + 1);
        else return tile_2_3(input, index + 1);
    }
    public static String tile_2_4(char[] input, int index) {
        return printMaze("INVALID", 2, 4);
    }
    public static String tile_2_5(char[] input, int index) {
        return printMaze("INVALID", 2, 5);
    }
    public static String tile_2_6(char[] input, int index) {
        return printMaze("INVALID", 2, 6);
    }
    public static String tile_2_7(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 2, 7);
        else if (input[index] == 'L') return tile_2_6(input, index + 1);
        else if (input[index] == 'R') return tile_2_8(input, index + 1);
        else if (input[index] == 'U') return tile_1_7(input, index + 1);
        else if (input[index] == 'D') return tile_3_7(input, index + 1);
        else return tile_2_7(input, index + 1);
    }
    public static String tile_2_8(char[] input, int index) {
        return printMaze("INVALID", 2, 8);
    }
    public static String tile_3_0(char[] input, int index) {
        return printMaze("INVALID", 3, 0);
    }
    public static String tile_3_1(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 3, 1);
        else if (input[index] == 'L') return tile_3_0(input, index + 1);
        else if (input[index] == 'R') return tile_3_2(input, index + 1);
        else if (input[index] == 'U') return tile_2_1(input, index + 1);
        else if (input[index] == 'D') return tile_4_1(input, index + 1);
        else return tile_3_1(input, index + 1);
    }
    public static String tile_3_2(char[] input, int index) {
        return printMaze("INVALID", 3, 2);
    }
    public static String tile_3_3(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 3, 3);
        else if (input[index] == 'L') return tile_3_2(input, index + 1);
        else if (input[index] == 'R') return tile_3_4(input, index + 1);
        else if (input[index] == 'U') return tile_2_3(input, index + 1);
        else if (input[index] == 'D') return tile_4_3(input, index + 1);
        else return tile_3_3(input, index + 1);
    }
    public static String tile_3_4(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 3, 4);
        else if (input[index] == 'L') return tile_3_3(input, index + 1);
        else if (input[index] == 'R') return tile_3_5(input, index + 1);
        else if (input[index] == 'U') return tile_2_4(input, index + 1);
        else if (input[index] == 'D') return tile_4_4(input, index + 1);
        else return tile_3_4(input, index + 1);
    }
    public static String tile_3_5(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 3, 5);
        else if (input[index] == 'L') return tile_3_4(input, index + 1);
        else if (input[index] == 'R') return tile_3_6(input, index + 1);
        else if (input[index] == 'U') return tile_2_5(input, index + 1);
        else if (input[index] == 'D') return tile_4_5(input, index + 1);
        else return tile_3_5(input, index + 1);
    }
    public static String tile_3_6(char[] input, int index) {
        return printMaze("INVALID", 3, 6);
    }
    public static String tile_3_7(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 3, 7);
        else if (input[index] == 'L') return tile_3_6(input, index + 1);
        else if (input[index] == 'R') return tile_3_8(input, index + 1);
        else if (input[index] == 'U') return tile_2_7(input, index + 1);
        else if (input[index] == 'D') return tile_4_7(input, index + 1);
        else return tile_3_7(input, index + 1);
    }
    public static String tile_3_8(char[] input, int index) {
        return printMaze("INVALID", 3, 8);
    }
    public static String tile_4_0(char[] input, int index) {
        return printMaze("INVALID", 4, 0);
    }
    public static String tile_4_1(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 4, 1);
        else if (input[index] == 'L') return tile_4_0(input, index + 1);
        else if (input[index] == 'R') return tile_4_2(input, index + 1);
        else if (input[index] == 'U') return tile_3_1(input, index + 1);
        else if (input[index] == 'D') return tile_5_1(input, index + 1);
        else return tile_4_1(input, index + 1);
    }
    public static String tile_4_2(char[] input, int index) {
        return printMaze("INVALID", 4, 2);
    }
    public static String tile_4_3(char[] input, int index) {
        return printMaze("INVALID", 4, 3);
    }
    public static String tile_4_4(char[] input, int index) {
        return printMaze("INVALID", 4, 4);
    }
    public static String tile_4_5(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 4, 5);
        else if (input[index] == 'L') return tile_4_4(input, index + 1);
        else if (input[index] == 'R') return tile_4_6(input, index + 1);
        else if (input[index] == 'U') return tile_3_5(input, index + 1);
        else if (input[index] == 'D') return tile_5_5(input, index + 1);
        else return tile_4_5(input, index + 1);
    }
    public static String tile_4_6(char[] input, int index) {
        return printMaze("INVALID", 4, 6);
    }
    public static String tile_4_7(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 4, 7);
        else if (input[index] == 'L') return tile_4_6(input, index + 1);
        else if (input[index] == 'R') return tile_4_8(input, index + 1);
        else if (input[index] == 'U') return tile_3_7(input, index + 1);
        else if (input[index] == 'D') return tile_5_7(input, index + 1);
        else return tile_4_7(input, index + 1);
    }
    public static String tile_4_8(char[] input, int index) {
        return printMaze("INVALID", 4, 8);
    }
    public static String tile_5_0(char[] input, int index) {
        return printMaze("INVALID", 5, 0);
    }
    public static String tile_5_1(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 5, 1);
        else if (input[index] == 'L') return tile_5_0(input, index + 1);
        else if (input[index] == 'R') return tile_5_2(input, index + 1);
        else if (input[index] == 'U') return tile_4_1(input, index + 1);
        else if (input[index] == 'D') return tile_6_1(input, index + 1);
        else return tile_5_1(input, index + 1);
    }
    public static String tile_5_2(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 5, 2);
        else if (input[index] == 'L') return tile_5_1(input, index + 1);
        else if (input[index] == 'R') return tile_5_3(input, index + 1);
        else if (input[index] == 'U') return tile_4_2(input, index + 1);
        else if (input[index] == 'D') return tile_6_2(input, index + 1);
        else return tile_5_2(input, index + 1);
    }
    public static String tile_5_3(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 5, 3);
        else if (input[index] == 'L') return tile_5_2(input, index + 1);
        else if (input[index] == 'R') return tile_5_4(input, index + 1);
        else if (input[index] == 'U') return tile_4_3(input, index + 1);
        else if (input[index] == 'D') return tile_6_3(input, index + 1);
        else return tile_5_3(input, index + 1);
    }
    public static String tile_5_4(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 5, 4);
        else if (input[index] == 'L') return tile_5_3(input, index + 1);
        else if (input[index] == 'R') return tile_5_5(input, index + 1);
        else if (input[index] == 'U') return tile_4_4(input, index + 1);
        else if (input[index] == 'D') return tile_6_4(input, index + 1);
        else return tile_5_4(input, index + 1);
    }
    public static String tile_5_5(char[] input, int index) {
        if (index == input.length) return printMaze("VALID", 5, 5);
        else if (input[index] == 'L') return tile_5_4(input, index + 1);
        else if (input[index] == 'R') return tile_5_6(input, index + 1);
        else if (input[index] == 'U') return tile_4_5(input, index + 1);
        else if (input[index] == 'D') return tile_6_5(input, index + 1);
        else return tile_5_5(input, index + 1);
    }
    public static String tile_5_6(char[] input, int index) {
        return printMaze("INVALID", 5, 6);
    }
    public static String tile_5_7(char[] input, int index) {
        return printMaze("SOLVED", 5, 7);
    }

    public static String targetTile() {
        return "tile_5_7";
    }
    public static String tile_5_8(char[] input, int index) {
        return printMaze("INVALID", 5, 8);
    }
    public static String tile_6_0(char[] input, int index) {
        return printMaze("INVALID", 6, 0);
    }
    public static String tile_6_1(char[] input, int index) {
        return printMaze("INVALID", 6, 1);
    }
    public static String tile_6_2(char[] input, int index) {
        return printMaze("INVALID", 6, 2);
    }
    public static String tile_6_3(char[] input, int index) {
        return printMaze("INVALID", 6, 3);
    }
    public static String tile_6_4(char[] input, int index) {
        return printMaze("INVALID", 6, 4);
    }
    public static String tile_6_5(char[] input, int index) {
        return printMaze("INVALID", 6, 5);
    }
    public static String tile_6_6(char[] input, int index) {
        return printMaze("INVALID", 6, 6);
    }
    public static String tile_6_7(char[] input, int index) {
        return printMaze("INVALID", 6, 7);
    }
    public static String tile_6_8(char[] input, int index) {
        return printMaze("INVALID", 6, 8);
    }

 }