package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect9.FuzzMutator;

import java.util.HashMap;
import java.util.List;

public class CountingGreyboxFuzzer extends GreyBoxFuzzer {


    public CountingGreyboxFuzzer(List<String> seeds, FuzzMutator mutator, AFLFastSchedule schedule, int minMutations, int maxMutations) {
        super(seeds, mutator, schedule, minMutations, maxMutations);
    }

    @Override
    public void reset() {
        super.reset();
        schedule.pathFrequency = new HashMap<>();
    }

    @Override
    public Object run(FunctionRunner runner, String input) {
        var res = super.run(runner, input);
        var path_id = PathIDGenerator.getPathID(runner.getCoverageAsLocations());
        if (schedule.pathFrequency.containsKey(path_id)) {
            schedule.pathFrequency.put(path_id, schedule.pathFrequency.get(path_id) + 1);
        } else {
            schedule.pathFrequency.put(path_id, 1);
        }
        return res;
    }
}
