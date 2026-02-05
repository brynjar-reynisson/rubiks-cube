package com.breynisson.rubiks.solver;

import com.breynisson.rubiks.Action;
import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Solver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.breynisson.rubiks.Action.BOTTOM_RIGHT;
import static com.breynisson.rubiks.Action.actionsByOrdinal;

public class MonteCarlo implements Solver {

    private final int iterationsPerRound;
    private final int parallelIterationCount;
    private final Random random;

    public MonteCarlo(int iterationsPerRound, int parallelIterationCount) {
        this(iterationsPerRound, parallelIterationCount, LocalDateTime.now().getNano());
    }

    public MonteCarlo(int iterationsPerRound, int parallelIterationCount, long randomSeed) {
        this.iterationsPerRound = iterationsPerRound;
        this.parallelIterationCount = parallelIterationCount;
        random = new Random(randomSeed);
    }

    @Override
    public List<Action> solve(CubeModel cubeModel) {
        long iterations = 0L;
        boolean solved = false;
        List<Action> selectedActionSequence = new ArrayList<>();
        CubeModel bestModel = cubeModel;
        while (!solved && iterations < 10000000L) {
            List<Iteration> iterationList = new ArrayList<>();
            for(int i=0; i < parallelIterationCount; i++) {
                iterationList.add(new Iteration(bestModel, createRandomActionList()));
                iterations++;
            }
            Iteration bestIteration = iterationList.get(0);
            int minDifference = Integer.MAX_VALUE;
            for (Iteration iteration : iterationList) {
                int difference = iteration.result.differenceWithTargetState();
                if (difference < minDifference) {
                    bestIteration = iteration;
                }
            }
            selectedActionSequence.addAll(bestIteration.actions);
            bestModel = bestIteration.result;
        }
        return selectedActionSequence;
    }

    private List<Action> createRandomActionList() {
        List<Action> actions = new ArrayList<>();
        for (int i=0; i<iterationsPerRound; i++) {
            int nextOrdinal = random.nextInt(BOTTOM_RIGHT.ordinal()+1);
            actions.add(actionsByOrdinal.get(nextOrdinal));
        }
        return actions;
    }

    private static final class Iteration {

        private final List<Action> actions;
        private final CubeModel result;

        private Iteration(CubeModel initialModel, List<Action> actions) {
            this.actions = actions;
            result = initialModel.createClone();
            result.applyActions(actions);
        }
    }
}
