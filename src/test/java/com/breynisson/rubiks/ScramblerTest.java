package com.breynisson.rubiks;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScramblerTest {

    @Test
    void shouldScrambleCubeAndReverseTheActionsBackToTargetState() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        List<Action> actions = Scrambler.createRandomActionList(50, 999);
        cubeModel.applyActions(actions);

        //then
        assertFalse(cubeModel.equalsTargetState());
        System.out.println(cubeModel.displayCube());

        //when
        List<Action> reverseActions = Action.createReverseActions(actions);
        cubeModel.applyActions(reverseActions);

        //then
        assertTrue(cubeModel.equalsTargetState());
    }
}