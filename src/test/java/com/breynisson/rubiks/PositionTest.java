package com.breynisson.rubiks;

import org.junit.jupiter.api.Test;

import static com.breynisson.rubiks.Color.*;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void verifyTargetColours() {
        for (Position position : Position.values()) {
            int ordinal = position.ordinal();
            if (ordinal < 9) {
                assertEquals(WHITE, position.side.targetColor);
            } else if (ordinal < 18) {
                assertEquals(BLUE, position.side.targetColor);
            } else if (ordinal < 27) {
                assertEquals(ORANGE, position.side.targetColor);
            } else if (ordinal < 36) {
                assertEquals(RED, position.side.targetColor);
            } else if (ordinal < 45) {
                assertEquals(GREEN, position.side.targetColor);
            } else {
                assertEquals(YELLOW, position.side.targetColor);
            }
        }
    }
}