package org.itmo.fuzzing.lab1;

public class MazeRunner {

    String maze = """
                +-+-----+
                |X|     |
                | | --+ |
                | |   | |
                | +-- | |
                |     |#|
                +-----+-+
                """;
    public static void main(String[] args) {
        System.out.println(MazeGenerated.maze("DDDDRRRRUULLUURRRRDDDD"));
    }
}
