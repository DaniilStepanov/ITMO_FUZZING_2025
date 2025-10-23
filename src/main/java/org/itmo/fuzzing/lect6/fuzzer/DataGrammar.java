package org.itmo.fuzzing.lect6.fuzzer;

import org.itmo.fuzzing.lect6.grammar.BetterGrammar;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


public class DataGrammar {

    public static final LinkedHashMap<String, List<String>> DATA_GRAMMAR = new LinkedHashMap<>() {{
        put("<start>", Arrays.asList("<document>"));

    }};


    public static BetterGrammar getBetterGrammar() {
        return new BetterGrammar(DATA_GRAMMAR);
    }


}
