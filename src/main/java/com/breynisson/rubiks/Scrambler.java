package com.breynisson.rubiks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.breynisson.rubiks.Action.*;

public class Scrambler {

    public static List<Action> createRandomActionList(int actionCount, long randomSeed) {
        Random random = new Random(randomSeed);
        List<Action> actions = new ArrayList<>();
        for (int i=0; i<actionCount; i++) {
            int nextOrdinal = random.nextInt(BOTTOM_RIGHT.ordinal()+1);
            actions.add(actionByOrdinal.get(nextOrdinal));
        }
        return actions;
    }
}
