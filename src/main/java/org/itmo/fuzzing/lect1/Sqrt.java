package org.itmo.fuzzing.lect1;

import java.util.Random;

public class Sqrt {
    public static double EPSILON = 1e-6;

    public static double mySqrt(double x) {
        double approx = 0.0;
        double guess = x / 2;
        while (approx != guess) {
            approx = guess;
            guess = (approx + x / approx) / 2;
        }
        return approx;
    }

    public static void main(String[] args) {

        var x = 2.0;
        double result = mySqrt(x);
        System.out.println("ORIG" + x + " " + (mySqrt(x) * mySqrt(x)));
        System.exit(0);








        var r = new Random();
        var beginTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            var n = r.nextDouble();
            assertEqualsWithEpsilon(mySqrt(n) * mySqrt(n), n);
        }
        var endTime = System.currentTimeMillis();
        System.out.println("TIME = " + (endTime - beginTime));






//        System.out.println(mySqrt(16.0));
//        System.out.println(mySqrt(9.0));
        //Как проверить, что функция работает корректно?
//        //Поздравляю, мы написали тест
//        //Плюсы: у нас есть тест
//        //Минусы: ну есть у нас один ручной тест и что?
//        //Все-таки есть поинт в том, чтобы автоматизировать тесты?
//        double result = mySqrt(4.0);
//        System.out.println(result);
//        double expected_result = 2.0;
//        if (result != expected_result) {
//            System.out.println("Test failed");
//        } else {
//            System.out.println("Test passed");
//        }
////        //Список грустей:
////        //1. Нам нужно пять строк кода для одного теста
////        //2. Забили на ошибки округления
////        //3. Мы проверяем только один ввод (и один результат)
////        // 1.
////        assert mySqrt(4.0) == 2;
////        // 2.
//        assert (Math.abs(mySqrt(4.0) - 2.0) < EPSILON);
//        assert (Math.abs(mySqrt(9.0) - 3.0) < EPSILON);
//        assert (Math.abs(mySqrt(16.0) - 4.0) < EPSILON);
////        //3.
////        //А давайте напишем цикл??
////        //Сколько времени это займёт?
///         //А давайте рандомные числа?
////        //Code
////        //А что если вставить проверку прям в метод?
////        System.out.println(sqrt_checked(2.0));
////        //В чём проблемы? 1. нужно уметь формулировать такие чеки; 2. иногда это очень дорого
////
////        //Ну вроде все ок. Предположим, что отдали нашу программу в виде библиотеки
//        System.out.println(sqrt_checked(-1.0));

        System.out.println(sqrt_dep("4.0"));
//        System.out.println(sqrt_dep("-1.0"));
    }

    public static void assertEqualsWithEpsilon(double x, double y) {
        assert Math.abs(x - y) < EPSILON;
    }

    public static double sqrt_checked(double x) {
        double result = mySqrt(x);
        assertEqualsWithEpsilon(result * result, x);
        return result;
    }


    public static double sqrt_dep(String x) {
        double doubleX = Double.parseDouble(x);
        System.out.println("The root of " + x + " is " + sqrt_checked(doubleX));
        return sqrt_checked(doubleX);
    }
}
