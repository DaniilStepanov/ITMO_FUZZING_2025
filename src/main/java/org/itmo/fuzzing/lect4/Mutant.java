package org.itmo.fuzzing.lect4;

import org.itmo.fuzzing.lect3.Location;

public class Mutant {
    Location myLocation;
    String name;
    String src;
    Boolean detected = false;

    public Mutant(Location location, String name, String src) {
        this.myLocation = location;
        this.name = name;
        this.src = src;
        this.detected = false;
    }
}
