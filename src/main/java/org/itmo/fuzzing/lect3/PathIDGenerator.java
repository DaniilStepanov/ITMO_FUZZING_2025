package org.itmo.fuzzing.lect3;

import java.util.TreeSet;

public class PathIDGenerator {

    public static int getPathID(TreeSet<Location> locations) {
        int hash = 5381;

        for (Location loc : locations) {
            // Используем String.hashCode() вместо посимвольного хеширования
            hash = ((hash << 5) + hash) + loc.getFilename().hashCode();
            hash = ((hash << 5) + hash) + loc.getLineno();
            hash = ((hash << 5) + hash) + loc.getFunction().hashCode();
        }

        return hash;
    }


}
