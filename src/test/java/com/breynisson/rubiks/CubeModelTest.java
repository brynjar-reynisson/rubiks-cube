package com.breynisson.rubiks;

import org.junit.jupiter.api.Test;

import static com.breynisson.rubiks.Action.*;
import static com.breynisson.rubiks.Side.FRONT;
import static org.junit.jupiter.api.Assertions.*;

class CubeModelTest {

    @Test
    void frontSideInTargetModelShouldBeWhite() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        String frontSideDisplay = cubeModel.displaySide(FRONT);

        //then
        assertEquals("www\nwww\nwww", frontSideDisplay);
    }

    @Test
    void displayTargetCube() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        String cubeDisplay = cubeModel.displayCube();

        //then
        assertTrue(cubeDisplay.contains("Front:\nwww\nwww\nwww"));
        assertTrue(cubeDisplay.contains("Left:\nooo\nooo\nooo"));
        assertTrue(cubeDisplay.contains("Right:\nrrr\nrrr\nrrr"));
    }

    @Test
    void applyLeftDownAction() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        cubeModel.applyAction(LEFT_DOWN);
        String cubeDisplay = cubeModel.displayCube();

        //then
        System.out.println(cubeDisplay);
        assertTrue(cubeDisplay.contains("""
                Front:
                bww
                bww
                bww"""));
        assertTrue(cubeDisplay.contains("""
                Left:
                ooo
                ooo
                ooo
                """));
        assertTrue(cubeDisplay.contains("""
                Bottom:
                wgg
                wgg
                wgg
                """));
    }

    @Test
    void applyLeftUpAction() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        cubeModel.applyAction(LEFT_UP);
        String cubeDisplay = cubeModel.displayCube();

        //then
        System.out.println(cubeDisplay);
        assertTrue(cubeDisplay.contains("""
                Front:
                gww
                gww
                gww
                """));
    }

    @Test
    void applyAllActions() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        cubeModel.applyAction(LEFT_UP);
        String cube1 = cubeModel.displayCube();

        cubeModel.applyAction(RIGHT_UP);
        String cube2 = cubeModel.displayCube();

        cubeModel.applyAction(LEFT_DOWN);
        String cube3 = cubeModel.displayCube();

        cubeModel.applyAction(RIGHT_DOWN);
        String cube4 = cubeModel.displayCube();

        cubeModel.applyAction(TOP_LEFT);
        String cube5 = cubeModel.displayCube();

        cubeModel.applyAction(TOP_RIGHT);
        String cube6 = cubeModel.displayCube();

        cubeModel.applyAction(BOTTOM_LEFT);
        String cube7 = cubeModel.displayCube();

        cubeModel.applyAction(BOTTOM_RIGHT);
        String cube8 = cubeModel.displayCube();

        cubeModel.applyAction(FRONT_LEFT);
        cubeModel.applyAction(FRONT_RIGHT);
        cubeModel.applyAction(BACK_LEFT);
        cubeModel.applyAction(BACK_RIGHT);

        //then
        assertTrue(cubeModel.equalsTargetState());
        assertNotEquals(cube1, cube2);
        assertNotEquals(cube2, cube3);
        assertNotEquals(cube3, cube4);
        assertNotEquals(cube4, cube5);
        assertNotEquals(cube5, cube6);
        assertNotEquals(cube6, cube7);
        assertNotEquals(cube7, cube8);
    }

    @Test
    void isDifferentFromTargetState() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        cubeModel.applyAction(LEFT_UP);
        int differences = cubeModel.differenceWithTargetState();

        //then
        assertEquals(12, differences);
    }

    @Test
    void equalsTargetState() {
        //given
        CubeModel cubeModel = new CubeModel();
        cubeModel.setState(CubeModel.TARGET_STATE);

        //when
        int differences = cubeModel.differenceWithTargetState();

        //then
        assertEquals(0, differences);
    }
}