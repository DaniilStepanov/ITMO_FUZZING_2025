package org.itmo.fuzzing.lect1;

public class TroffSimulator {

    // 1. Проверка: последовательность \D + непечатаемый символ
    public static void checkBackslashD(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '\\' && s.charAt(i + 1) == 'D') {
                if (i + 2 < s.length()) {
                    char next = s.charAt(i + 2);
                    if (!Character.isISOControl(next) && !Character.isWhitespace(next)) {
                        continue;
                    }
                    // Если следующий символ непечатаемый
                    if (Character.isISOControl(next)) {
                        throw new RuntimeException("Troff failure: \\D followed by non-printable character");
                    }
                }
            }
        }
    }

    // 2. Проверка: символы с 8-м битом (128-255) перед переводом строки
    public static void checkHighAscii(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            char c = s.charAt(i);
            if ((c & 0x80) != 0 && s.charAt(i + 1) == '\n') {
                throw new RuntimeException("Troff failure: high ASCII character followed by newline");
            }
        }
    }

    // 3. Проверка: одиночная точка на строке
    public static void checkSingleDot(String s) {
        String[] lines = s.split("\n");
        for (String line : lines) {
            if (line.equals(".")) {
                throw new RuntimeException("Troff failure: single dot line");
            }
        }
    }

    // Основной метод для проверки всех условий
    public static void simulateTroff(String input) {
        checkBackslashD(input);
        checkHighAscii(input);
        checkSingleDot(input);
    }

    public static void main(String[] args) {
        String input = ".NH\nSome Heading\n.LP\nSome paragraph\n";
        simulateTroff(input);
        System.out.println("Input passed Troff simulation!");
    }
}
