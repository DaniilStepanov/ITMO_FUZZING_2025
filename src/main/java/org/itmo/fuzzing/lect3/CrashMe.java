package org.itmo.fuzzing.lect3;

public class CrashMe {

    public static void crashMe(String s) {
        if (s.length() > 0 && s.charAt(0) == 'b') {
            if (s.length() > 1 && s.charAt(1) == 'a') {
                if (s.length() > 2 && s.charAt(2) == 'd') {
                    if (s.length() > 3 && s.charAt(3) == '!') {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
    }
}
