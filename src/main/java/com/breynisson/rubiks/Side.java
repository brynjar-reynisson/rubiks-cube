package com.breynisson.rubiks;

import static com.breynisson.rubiks.Color.*;

public enum Side {
    FRONT(WHITE),
    LEFT(ORANGE),
    TOP(BLUE),
    RIGHT(RED),
    BOTTOM(GREEN),
    BACK(YELLOW)
    ;
    public final Color targetColor;
    Side(Color targetColor) {
        this.targetColor = targetColor;
    }
}
