package org.itmo.fuzzing.lect2.instrumentation;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class CoverageTracker {

    public static final TreeSet<String> coverage = new TreeSet<String>();
    public static final TreeSet<String> fullCoverage = new TreeSet<String>();

    public static void logCoverage(String methodSignature, String lineNumber) {
        coverage.add(methodSignature + ":" + lineNumber);
    }

    public static void logFullCoverage(String methodSignature, String lineNumber) {
        fullCoverage.add(methodSignature + ":" + lineNumber);
    }

    /**
     * Возвращает непокрытые строки для указанного метода
     * @param methodName имя метода
     * @return множество непокрытых строк в формате "methodName:lineNumber"
     */
    public static Set<String> getDiffFor(String methodName) {
        Set<String> result = new TreeSet<>();

        // Получаем все строки метода из fullCoverage
        for (String entry : fullCoverage) {
            if (entry.startsWith(methodName + ":")) {
                // Если эта строка не покрыта, добавляем в результат
                if (!coverage.contains(entry)) {
                    result.add(entry);
                }
            }
        }

        return result;
    }

    // Дополнительные полезные методы:

    /**
     * Возвращает процент покрытия для указанного метода
     */
    public static double getCoveragePercentageFor(String methodName) {
        int totalLines = 0;
        int coveredLines = 0;

        for (String entry : fullCoverage) {
            if (entry.startsWith(methodName + ":")) {
                totalLines++;
                if (coverage.contains(entry)) {
                    coveredLines++;
                }
            }
        }

        return totalLines == 0 ? 100.0 : (coveredLines * 100.0) / totalLines;
    }

    /**
     * Возвращает только номера непокрытых строк для метода
     */
    public static Set<Integer> getUncoveredLineNumbersFor(String methodName) {
        Set<Integer> result = new TreeSet<>();

        for (String entry : fullCoverage) {
            if (entry.startsWith(methodName + ":")) {
                if (!coverage.contains(entry)) {
                    String lineNumber = entry.substring(entry.indexOf(':') + 1);
                    result.add(Integer.parseInt(lineNumber));
                }
            }
        }

        return result;
    }

    /**
     * Возвращает общую статистику покрытия
     */
    public static String getCoverageStats() {
        int covered = coverage.size();
        int total = fullCoverage.size();
        double percentage = total == 0 ? 100.0 : (covered * 100.0) / total;

        return String.format("Coverage: %d/%d (%.2f%%)", covered, total, percentage);
    }

    /**
     * Возвращает статистику покрытия для конкретного метода
     */
    public static String getCoverageStatsFor(String methodName) {
        int totalLines = 0;
        int coveredLines = 0;

        for (String entry : fullCoverage) {
            if (entry.startsWith(methodName + ":")) {
                totalLines++;
                if (coverage.contains(entry)) {
                    coveredLines++;
                }
            }
        }

        double percentage = totalLines == 0 ? 100.0 : (coveredLines * 100.0) / totalLines;
        return String.format("Coverage for %s: %d/%d (%.2f%%)",
                methodName, coveredLines, totalLines, percentage);
    }
}