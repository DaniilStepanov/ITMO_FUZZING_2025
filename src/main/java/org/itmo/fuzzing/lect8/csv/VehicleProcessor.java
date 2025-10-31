package org.itmo.fuzzing.lect8.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleProcessor {

    public String processInventory(String inventory) {
        List<String> res = new ArrayList<>();
        for (String vehicle : inventory.split("\n")) {
            res.addAll(processVehicle(vehicle));
        }
        return String.join("\n", res);
    }

    private List<String> processVehicle(String vehicle) {
        String[] parts = vehicle.split(",");
        String year = parts[0];
        String kind = parts[1];
        String company = parts[2];
        String model = parts[3];

        switch (kind) {
            case "van":
                return processVan(year, company, model);
            case "car":
                return processCar(year, company, model);
            default:
                throw new IllegalArgumentException("Invalid entry");
        }
    }

    private List<String> processVan(String year, String company, String model) {
        List<String> res = new ArrayList<>();
        res.add(String.format("We have a %s %s van from %s vintage.", company, model, year));

        int iYear = Integer.parseInt(year);
        if (iYear > 2010) {
            res.add("It is a recent model!");
        } else {
            res.add("It is an old but reliable model!");
        }
        return res;
    }

    private List<String> processCar(String year, String company, String model) {
        List<String> res = new ArrayList<>();
        res.add(String.format("We have a %s %s car from %s vintage.", company, model, year));

        int iYear = Integer.parseInt(year);
        if (iYear > 2016) {
            res.add("It is a recent model!");
        } else {
            res.add("It is an old but reliable model!");
        }
        return res;
    }
}
