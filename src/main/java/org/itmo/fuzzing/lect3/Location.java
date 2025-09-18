package org.itmo.fuzzing.lect3;

import java.util.Objects;

public class Location {
    private String filename;
    private int lineno;
    private String function;

    public Location(String filename, int lineno, String function) {
        this.filename = filename;
        this.lineno = lineno;
        this.function = function;
    }

    public String getFilename() {
        return filename;
    }

    public int getLineno() {
        return lineno;
    }

    public String getFunction() {
        return function;
    }

    public static Location buildFromString(String coverage) {
        String filename = "";
        String function = coverage.split(":")[0];
        String lineNumber = coverage.split(":")[1];
        return new Location(filename, Integer.parseInt(lineNumber), function);
    }

    public String covertToString() {
        return filename + ":" + lineno;
    }

    @Override
    public String toString() {
        return "Location{filename='" + filename + "', lineno=" + lineno + ", function='" + function + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;
        return lineno == location.lineno && filename.equals(location.filename) && function.equals(location.function);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(filename);
        result = 31 * result + lineno;
        result = 31 * result + Objects.hashCode(function);
        return result;
    }
}
