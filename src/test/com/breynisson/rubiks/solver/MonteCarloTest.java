package com.breynisson.rubiks.solver;

import com.breynisson.rubiks.Action;
import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Scrambler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonteCarloTest {

    @Test
    void shouldSolve() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);
        List<Action> actions = Scrambler.createRandomActionList(50, 999);
        cubeModel.applyActions(actions);

        //when
        List<Action> solution = new MonteCarlo(5, 5).solve(cubeModel);
        cubeModel.applyActions(solution);

        //then
        assertTrue(cubeModel.equalsTargetState());
    }

}