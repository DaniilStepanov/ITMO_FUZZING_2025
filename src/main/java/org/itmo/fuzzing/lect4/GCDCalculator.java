package org.itmo.fuzzing.lect4;

public class GCDCalculator {

    public static Integer gcd(int a, int b) {
        if (a < b) {
            int temp = a;
            a = b;
            b = temp;
        }
        while (b != 0) {
            int temp = a;
            a = b;
            b = temp % a;
        }
        return a;
    }
}
