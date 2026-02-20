package com.breynisson.rubiks;

import static com.breynisson.rubiks.Side.*;

public enum Color {
    WHITE(FRONT),
    BLUE(TOP),
    RED(RIGHT),
    GREEN(BOTTOM),
    ORANGE(LEFT),
    YELLOW(BACK)
    ;

    public final Side targetSide;
    Color(Side targetSide) {
        this.targetSide = targetSide;
    }

    public static String shortDisplay(Color color) {
        return switch (color) {
            case WHITE -> "w";
            case BLUE -> "b";
            case RED -> "r";
            case GREEN -> "g";
            case ORANGE -> "o";
            case YELLOW -> "y";
        };
    }
}
