package org.itmo.fuzzing.lect2;

import org.itmo.fuzzing.lect2.instrumentation.CoverageTracker;
import org.itmo.fuzzing.lect3.Location;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionRunner {
    private static final String PASS = "PASS";
    private static final String FAIL = "FAIL";
    private final Function<String, Object> function;
    public ConcurrentSkipListSet<String> coverage;
    public Set<String> fullCoverage;

    /**
     * Инициализация.
     *
     * @param function - функция, которую нужно выполнить.
     */
    public FunctionRunner(Function<String, Object> function) {
        this.function = function;
    }

    /**
     * Выполняет функцию и возвращает результат.
     *
     * @param inp - входная строка.
     * @return результат выполнения функции.
     */
    public Object runFunction(String inp) {
        return function.apply(inp);
    }

    public Object runFunctionWithCoverage(String inp) {
        CoverageTracker.coverage.clear();
        coverage = CoverageTracker.coverage;
        fullCoverage = CoverageTracker.fullCoverage;
        return function.apply(inp);
    }

    public TreeSet<Location> getCoverageAsLocations() {
        return coverage.stream()
                .map(Location::buildFromString)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Выполняет функцию и возвращает результат и исход выполнения.
     *
     * @param inp - входная строка.
     * @return кортеж из результата и исхода выполнения.
     */
    public Tuple<Object, String> run(String inp) {
        Object result;
        String outcome;
        try {
            result = runFunctionWithCoverage(inp);
            outcome = PASS;
        } catch (Throwable e) {
            result = null;
            outcome = FAIL;
        }
        return new Tuple<>(result, outcome);
    }

    /**
     * Класс для представления кортежа из двух элементов.
     */
    public static class Tuple<T, U> {
        public final T first;
        public final U second;

        public Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    // Пример использования
    public static void main(String[] args) {
        Function<String, Object> func = s -> {
            // Пример функции, которая может выбрасывать исключения
            if (s.equals("error")) throw new RuntimeException("Test exception");
            return s.toUpperCase();
        };

        FunctionRunner runner = new FunctionRunner(func);
        Tuple<Object, String> result = runner.run("test");
        System.out.println("Result: " + result.first + ", Outcome: " + result.second);

        result = runner.run("error");
        System.out.println("Result: " + result.first + ", Outcome: " + result.second);
    }
}
