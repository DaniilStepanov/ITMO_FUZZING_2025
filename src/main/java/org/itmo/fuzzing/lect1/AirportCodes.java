package org.itmo.fuzzing.lect1;

import java.util.HashMap;
import java.util.Map;

public class AirportCodes {

    private static Map<String, String> airportCodes = new HashMap<>();

    static {
        airportCodes.put("YVR", "Vancouver");
        airportCodes.put("JFK", "New York-JFK");
        airportCodes.put("CDG", "Paris-Charles de Gaulle");
        airportCodes.put("CAI", "Cairo");
        airportCodes.put("LED", "St. Petersburg");
        airportCodes.put("PEK", "Beijing");
        airportCodes.put("HND", "Tokyo-Haneda");
        airportCodes.put("AKL", "Auckland");
    }

    public static void main(String[] args) {
        try {
            assert codeRepOK("SEA");
            assert airportCodesRepOK();

            // Добавление нового аэропорта
            addNewAirport("BER", "Berlin");
            // Попытка добавить некорректный код
            addNewAirport("London-Heathrow", "LHR");
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

    private static boolean codeRepOK(String code) {
        if (code.length() != 3) {
            throw new AssertionError("Airport code must have three characters: " + code);
        }
        for (char c : code.toCharArray()) {
            if (!Character.isLetter(c) || !Character.isUpperCase(c)) {
                throw new AssertionError("Invalid character in airport code: " + code);
            }
        }
        return true;
    }

    private static boolean airportCodesRepOK() {
        for (String code : airportCodes.keySet()) {
            codeRepOK(code);
        }
        return true;
    }

    private static void addNewAirport(String code, String city) {
        codeRepOK(code);
        airportCodesRepOK();
        airportCodes.put(code, city);
        airportCodesRepOK();
    }
}
