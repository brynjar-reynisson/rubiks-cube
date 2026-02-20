package com.breynisson.rubiks.solver;

import com.breynisson.rubiks.Action;
import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Solver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.breynisson.rubiks.Action.*;

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
            Iteration bestIteration = null;
            int minDifference = bestModel.differenceWithTargetState();
            for (Iteration iteration : iterationList) {
                int difference = iteration.result.differenceWithTargetState();
                if (difference < minDifference) {
                    bestIteration = iteration;
                    minDifference = difference;
                }
            }
            if (bestIteration != null) {
                selectedActionSequence.addAll(bestIteration.actions);
                bestModel = bestIteration.result;
                if (bestModel.equalsTargetState()) {
                    break;
                }
            }
        }
        return selectedActionSequence;
    }

    private List<Action> createRandomActionList() {
        List<Action> actions = new ArrayList<>();
        for (int i=0; i<iterationsPerRound; i++) {
            int nextOrdinal = random.nextInt(BACK_RIGHT.ordinal()+1);
            actions.add(actionByOrdinal.get(nextOrdinal));
        }
        return actions;
    }

    private static final class Iteration {

        private final List<Action> actions;
        private final CubeModel result;

        private Iteration(CubeModel initialModel, List<Action> actions) {
            result = initialModel.createClone();
            for (int i=0; i<actions.size(); i++) {
                result.applyAction(actions.get(i));
                if (result.equalsTargetState()) {
                    this.actions = actions.subList(0, i+1);
                    return;
                }
            }
            this.actions = actions;
        }
    }
}
