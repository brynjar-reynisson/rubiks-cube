package com.breynisson.rubiks;

import java.util.*;

import static com.breynisson.rubiks.Position.*;
import static com.breynisson.rubiks.Position.BACK_TOP_RIGHT;

public enum Action {
    LEFT_DOWN("L"),
    LEFT_UP("L'"),
    RIGHT_DOWN("R'"),
    RIGHT_UP("R"),
    TOP_LEFT("U'"),
    TOP_RIGHT("U"),
    BOTTOM_LEFT("D"),
    BOTTOM_RIGHT("D'"),
    FRONT_LEFT("F'"),
    FRONT_RIGHT("F"),
    BACK_LEFT("B"),
    BACK_RIGHT("B'")
    ;

    final String standardNotation;//clock-wise L, U (top), R, D (bottom), F (front), B (back) and inverse counter-clockwise, L', U'...
    Action(String standardNotation) {
        this.standardNotation = standardNotation;
    }

    public static final Map<String, Action> actionByStandardNotation = new HashMap<>();
    static {
        for (Action action : Action.values()) {
            actionByStandardNotation.put(action.standardNotation, action);
        }
    }

    public static final Map<Integer, Action> actionByOrdinal = new HashMap<>(Map.of(
            LEFT_DOWN.ordinal(), LEFT_DOWN,
            LEFT_UP.ordinal(), LEFT_UP,
            RIGHT_DOWN.ordinal(), RIGHT_DOWN,
            RIGHT_UP.ordinal(), RIGHT_UP,
            TOP_LEFT.ordinal(), TOP_LEFT,
            TOP_RIGHT.ordinal(), TOP_RIGHT,
            BOTTOM_LEFT.ordinal(), BOTTOM_LEFT,
            BOTTOM_RIGHT.ordinal(), BOTTOM_RIGHT,
            FRONT_LEFT.ordinal(), FRONT_LEFT,
            FRONT_RIGHT.ordinal(), FRONT_RIGHT
            //Map.of takes max 10 key/value pairs
            //BACK_LEFT.ordinal(), BACK_LEFT,
            //BACK_RIGHT.ordinal(), BACK_RIGHT
    ));

    static {
        actionByOrdinal.put(BACK_LEFT.ordinal(), BACK_LEFT);
        actionByOrdinal.put(BACK_RIGHT.ordinal(), BACK_RIGHT);
    }

    static Action reverseAction(Action action) {
        return switch (action) {
            case LEFT_DOWN -> LEFT_UP;
            case LEFT_UP -> LEFT_DOWN;
            case RIGHT_DOWN -> RIGHT_UP;
            case RIGHT_UP -> RIGHT_DOWN;
            case TOP_LEFT -> TOP_RIGHT;
            case TOP_RIGHT -> TOP_LEFT;
            case BOTTOM_LEFT -> BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> BOTTOM_LEFT;
            case FRONT_LEFT -> FRONT_RIGHT;
            case FRONT_RIGHT -> FRONT_LEFT;
            case BACK_LEFT -> BACK_RIGHT;
            case BACK_RIGHT -> BACK_LEFT;
        };
    }

    static List<Action> createReverseActions(Collection<Action> actions) {
        List<Action> orderedActions = new ArrayList<>(actions);
        List<Action> reverseActions = new ArrayList<>();
        Collections.reverse(orderedActions);
        for (Action action : orderedActions) {
            reverseActions.add(Action.reverseAction(action));
        }
        return reverseActions;
    }

    static final Map<Action, List<Result>> results = new HashMap<>(Map.of(
            LEFT_DOWN, List.of(
                    $(FRONT_TOP_LEFT, BOTTOM_FRONT_LEFT),
                    $(FRONT_MIDDLE_LEFT, BOTTOM_MIDDLE_LEFT),
                    $(FRONT_BOTTOM_LEFT, BOTTOM_BACK_LEFT),
                    $(TOP_BACK_LEFT, FRONT_TOP_LEFT),
                    $(TOP_MIDDLE_LEFT, FRONT_MIDDLE_LEFT),
                    $(TOP_FRONT_LEFT, FRONT_BOTTOM_LEFT),
                    $(BACK_BOTTOM_LEFT, TOP_BACK_LEFT),
                    $(BACK_MIDDLE_LEFT, TOP_MIDDLE_LEFT),
                    $(BACK_TOP_LEFT, TOP_FRONT_LEFT),
                    $(BOTTOM_FRONT_LEFT, BACK_BOTTOM_LEFT),
                    $(BOTTOM_MIDDLE_LEFT, BACK_MIDDLE_LEFT),
                    $(BOTTOM_BACK_LEFT, BACK_TOP_LEFT),
                    $(LEFT_FRONT_TOP, LEFT_FRONT_BOTTOM),
                    $(LEFT_FRONT_MIDDLE, LEFT_MIDDLE_BOTTOM),
                    $(LEFT_FRONT_BOTTOM, LEFT_BACK_BOTTOM),
                    $(LEFT_MIDDLE_BOTTOM, LEFT_BACK_MIDDLE),
                    $(LEFT_BACK_BOTTOM, LEFT_BACK_TOP),
                    $(LEFT_BACK_MIDDLE, LEFT_MIDDLE_TOP),
                    $(LEFT_BACK_TOP, LEFT_FRONT_TOP),
                    $(LEFT_MIDDLE_TOP, LEFT_FRONT_MIDDLE)
            ),
            LEFT_UP, List.of(
                    $(FRONT_TOP_LEFT, TOP_BACK_LEFT),
                    $(FRONT_MIDDLE_LEFT, TOP_MIDDLE_LEFT),
                    $(FRONT_BOTTOM_LEFT, TOP_FRONT_LEFT),
                    $(TOP_BACK_LEFT, BACK_BOTTOM_LEFT),
                    $(TOP_MIDDLE_LEFT, BACK_MIDDLE_LEFT),
                    $(TOP_FRONT_LEFT, BACK_TOP_LEFT),
                    $(BACK_BOTTOM_LEFT, BOTTOM_FRONT_LEFT),
                    $(BACK_MIDDLE_LEFT, BOTTOM_MIDDLE_LEFT),
                    $(BACK_TOP_LEFT, BOTTOM_BACK_LEFT),
                    $(BOTTOM_FRONT_LEFT, FRONT_TOP_LEFT),
                    $(BOTTOM_MIDDLE_LEFT, FRONT_MIDDLE_LEFT),
                    $(BOTTOM_BACK_LEFT, FRONT_BOTTOM_LEFT),
                    $(LEFT_FRONT_TOP, LEFT_BACK_TOP),
                    $(LEFT_FRONT_MIDDLE, LEFT_MIDDLE_TOP),
                    $(LEFT_FRONT_BOTTOM, LEFT_FRONT_TOP),
                    $(LEFT_MIDDLE_BOTTOM, LEFT_FRONT_MIDDLE),
                    $(LEFT_BACK_BOTTOM, LEFT_FRONT_BOTTOM),
                    $(LEFT_BACK_MIDDLE, LEFT_MIDDLE_BOTTOM),
                    $(LEFT_BACK_TOP, LEFT_BACK_BOTTOM),
                    $(LEFT_MIDDLE_TOP, LEFT_BACK_MIDDLE)
            ),
            RIGHT_DOWN, List.of(
                    $(FRONT_TOP_RIGHT, BOTTOM_FRONT_RIGHT),
                    $(FRONT_MIDDLE_RIGHT, BOTTOM_MIDDLE_RIGHT),
                    $(FRONT_BOTTOM_RIGHT, BOTTOM_BACK_RIGHT),
                    $(TOP_BACK_RIGHT, FRONT_TOP_RIGHT),
                    $(TOP_MIDDLE_RIGHT, FRONT_MIDDLE_RIGHT),
                    $(TOP_FRONT_RIGHT, FRONT_BOTTOM_RIGHT),
                    $(BACK_BOTTOM_RIGHT, TOP_BACK_RIGHT),
                    $(BACK_MIDDLE_RIGHT, TOP_MIDDLE_RIGHT),
                    $(BACK_TOP_RIGHT, TOP_FRONT_RIGHT),
                    $(BOTTOM_FRONT_RIGHT, BACK_BOTTOM_RIGHT),
                    $(BOTTOM_MIDDLE_RIGHT, BACK_MIDDLE_RIGHT),
                    $(BOTTOM_BACK_RIGHT, BACK_TOP_RIGHT),
                    $(RIGHT_FRONT_TOP, RIGHT_FRONT_BOTTOM),
                    $(RIGHT_FRONT_MIDDLE, RIGHT_MIDDLE_BOTTOM),
                    $(RIGHT_FRONT_BOTTOM, RIGHT_BACK_BOTTOM),
                    $(RIGHT_MIDDLE_TOP, RIGHT_FRONT_MIDDLE),
                    $(RIGHT_MIDDLE_BOTTOM, RIGHT_BACK_MIDDLE),
                    $(RIGHT_BACK_TOP, RIGHT_FRONT_TOP),
                    $(RIGHT_BACK_MIDDLE, RIGHT_MIDDLE_TOP),
                    $(RIGHT_BACK_BOTTOM, RIGHT_BACK_TOP)
            ),
            RIGHT_UP, List.of(
                    $(FRONT_TOP_RIGHT, TOP_BACK_RIGHT),
                    $(FRONT_MIDDLE_RIGHT, TOP_MIDDLE_RIGHT),
                    $(FRONT_BOTTOM_RIGHT, TOP_FRONT_RIGHT),
                    $(TOP_BACK_RIGHT, BACK_BOTTOM_RIGHT),
                    $(TOP_MIDDLE_RIGHT, BACK_MIDDLE_RIGHT),
                    $(TOP_FRONT_RIGHT, BACK_TOP_RIGHT),
                    $(BACK_BOTTOM_RIGHT, BOTTOM_FRONT_RIGHT),
                    $(BACK_MIDDLE_RIGHT, BOTTOM_MIDDLE_RIGHT),
                    $(BACK_TOP_RIGHT, BOTTOM_BACK_RIGHT),
                    $(BOTTOM_FRONT_RIGHT, FRONT_TOP_RIGHT),
                    $(BOTTOM_MIDDLE_RIGHT, FRONT_MIDDLE_RIGHT),
                    $(BOTTOM_BACK_RIGHT, FRONT_BOTTOM_RIGHT),
                    $(RIGHT_FRONT_TOP, RIGHT_BACK_TOP),
                    $(RIGHT_FRONT_MIDDLE, RIGHT_MIDDLE_TOP),
                    $(RIGHT_FRONT_BOTTOM, RIGHT_FRONT_TOP),
                    $(RIGHT_MIDDLE_TOP, RIGHT_BACK_MIDDLE),
                    $(RIGHT_MIDDLE_BOTTOM, RIGHT_FRONT_MIDDLE),
                    $(RIGHT_BACK_TOP, RIGHT_BACK_BOTTOM),
                    $(RIGHT_BACK_MIDDLE, RIGHT_MIDDLE_BOTTOM),
                    $(RIGHT_BACK_BOTTOM, RIGHT_FRONT_BOTTOM)
            ),
            TOP_LEFT, List.of(
                    $(FRONT_TOP_LEFT, LEFT_BACK_TOP),
                    $(FRONT_TOP_MIDDLE, LEFT_MIDDLE_TOP),
                    $(FRONT_TOP_RIGHT, LEFT_FRONT_TOP),
                    $(RIGHT_FRONT_TOP, FRONT_TOP_LEFT),
                    $(RIGHT_MIDDLE_TOP, FRONT_TOP_MIDDLE),
                    $(RIGHT_BACK_TOP, FRONT_TOP_RIGHT),
                    $(BACK_TOP_RIGHT, RIGHT_FRONT_TOP),
                    $(BACK_TOP_MIDDLE, RIGHT_MIDDLE_TOP),
                    $(BACK_TOP_LEFT, RIGHT_BACK_TOP),
                    $(LEFT_BACK_TOP, BACK_TOP_RIGHT),
                    $(LEFT_MIDDLE_TOP, BACK_TOP_MIDDLE),
                    $(LEFT_FRONT_TOP, BACK_TOP_LEFT),
                    $(TOP_FRONT_LEFT, TOP_BACK_LEFT),
                    $(TOP_FRONT_MIDDLE, TOP_MIDDLE_LEFT),
                    $(TOP_FRONT_RIGHT, TOP_FRONT_LEFT),
                    $(TOP_MIDDLE_RIGHT, TOP_FRONT_MIDDLE),
                    $(TOP_BACK_RIGHT, TOP_FRONT_RIGHT),
                    $(TOP_BACK_MIDDLE, TOP_MIDDLE_RIGHT),
                    $(TOP_BACK_LEFT, TOP_BACK_RIGHT),
                    $(TOP_MIDDLE_LEFT, TOP_BACK_MIDDLE)
            ),
            TOP_RIGHT, List.of(
                    $(FRONT_TOP_LEFT, RIGHT_FRONT_TOP),
                    $(FRONT_TOP_MIDDLE, RIGHT_MIDDLE_TOP),
                    $(FRONT_TOP_RIGHT, RIGHT_BACK_TOP),
                    $(RIGHT_FRONT_TOP, BACK_TOP_RIGHT),
                    $(RIGHT_MIDDLE_TOP, BACK_TOP_MIDDLE),
                    $(RIGHT_BACK_TOP, BACK_TOP_LEFT),
                    $(BACK_TOP_RIGHT, LEFT_BACK_TOP),
                    $(BACK_TOP_MIDDLE, LEFT_MIDDLE_TOP),
                    $(BACK_TOP_LEFT, LEFT_FRONT_TOP),
                    $(LEFT_BACK_TOP, FRONT_TOP_LEFT),
                    $(LEFT_MIDDLE_TOP, FRONT_TOP_MIDDLE),
                    $(LEFT_FRONT_TOP, FRONT_TOP_RIGHT),
                    $(TOP_FRONT_LEFT, TOP_FRONT_RIGHT),
                    $(TOP_FRONT_MIDDLE, TOP_MIDDLE_RIGHT),
                    $(TOP_FRONT_RIGHT, TOP_BACK_RIGHT),
                    $(TOP_MIDDLE_RIGHT, TOP_BACK_MIDDLE),
                    $(TOP_BACK_RIGHT, TOP_BACK_LEFT),
                    $(TOP_BACK_MIDDLE, TOP_MIDDLE_LEFT),
                    $(TOP_BACK_LEFT, TOP_FRONT_LEFT),
                    $(TOP_MIDDLE_LEFT, TOP_FRONT_MIDDLE)
            ),
            BOTTOM_LEFT, List.of(
                    $(FRONT_BOTTOM_LEFT, LEFT_BACK_BOTTOM),
                    $(FRONT_BOTTOM_MIDDLE, LEFT_MIDDLE_BOTTOM),
                    $(FRONT_BOTTOM_RIGHT, LEFT_FRONT_BOTTOM),
                    $(RIGHT_FRONT_BOTTOM, FRONT_BOTTOM_LEFT),
                    $(RIGHT_MIDDLE_BOTTOM, FRONT_BOTTOM_MIDDLE),
                    $(RIGHT_BACK_BOTTOM, FRONT_BOTTOM_RIGHT),
                    $(BACK_BOTTOM_RIGHT, RIGHT_FRONT_BOTTOM),
                    $(BACK_BOTTOM_MIDDLE, RIGHT_MIDDLE_BOTTOM),
                    $(BACK_BOTTOM_LEFT, RIGHT_BACK_BOTTOM),
                    $(LEFT_BACK_BOTTOM, BACK_BOTTOM_RIGHT),
                    $(LEFT_MIDDLE_BOTTOM, BACK_BOTTOM_MIDDLE),
                    $(LEFT_FRONT_BOTTOM, BACK_BOTTOM_LEFT),
                    $(BOTTOM_FRONT_LEFT, BOTTOM_BACK_LEFT),
                    $(BOTTOM_FRONT_MIDDLE, BOTTOM_MIDDLE_LEFT),
                    $(BOTTOM_FRONT_RIGHT, BOTTOM_FRONT_LEFT),
                    $(BOTTOM_MIDDLE_RIGHT, BOTTOM_FRONT_MIDDLE),
                    $(BOTTOM_BACK_RIGHT, BOTTOM_FRONT_RIGHT),
                    $(BOTTOM_BACK_MIDDLE, BOTTOM_MIDDLE_RIGHT),
                    $(BOTTOM_BACK_LEFT, BOTTOM_BACK_RIGHT),
                    $(BOTTOM_MIDDLE_LEFT, BOTTOM_BACK_MIDDLE),
                    $(BOTTOM_FRONT_LEFT, BOTTOM_BACK_LEFT)
            ),
            BOTTOM_RIGHT, List.of(
                    $(FRONT_BOTTOM_LEFT, RIGHT_FRONT_BOTTOM),
                    $(FRONT_BOTTOM_MIDDLE, RIGHT_MIDDLE_BOTTOM),
                    $(FRONT_BOTTOM_RIGHT, RIGHT_BACK_BOTTOM),
                    $(RIGHT_FRONT_BOTTOM, BACK_BOTTOM_RIGHT),
                    $(RIGHT_MIDDLE_BOTTOM, BACK_BOTTOM_MIDDLE),
                    $(RIGHT_BACK_BOTTOM, BACK_BOTTOM_LEFT),
                    $(BACK_BOTTOM_RIGHT, LEFT_BACK_BOTTOM),
                    $(BACK_BOTTOM_MIDDLE, LEFT_MIDDLE_BOTTOM),
                    $(BACK_BOTTOM_LEFT, LEFT_FRONT_BOTTOM),
                    $(LEFT_BACK_BOTTOM, FRONT_BOTTOM_LEFT),
                    $(LEFT_MIDDLE_BOTTOM, FRONT_BOTTOM_MIDDLE),
                    $(LEFT_FRONT_BOTTOM, FRONT_BOTTOM_RIGHT),
                    $(BOTTOM_FRONT_LEFT, BOTTOM_FRONT_RIGHT),
                    $(BOTTOM_FRONT_MIDDLE, BOTTOM_MIDDLE_RIGHT),
                    $(BOTTOM_FRONT_RIGHT, BOTTOM_BACK_RIGHT),
                    $(BOTTOM_MIDDLE_RIGHT, BOTTOM_BACK_MIDDLE),
                    $(BOTTOM_BACK_RIGHT, BOTTOM_BACK_LEFT),
                    $(BOTTOM_BACK_MIDDLE, BOTTOM_MIDDLE_LEFT),
                    $(BOTTOM_BACK_LEFT, BOTTOM_FRONT_LEFT),
                    $(BOTTOM_MIDDLE_LEFT, BOTTOM_FRONT_MIDDLE),
                    $(BOTTOM_FRONT_LEFT, BOTTOM_FRONT_RIGHT)
            ),
            FRONT_LEFT, List.of(
                    $(TOP_FRONT_LEFT, LEFT_FRONT_BOTTOM),
                    $(TOP_FRONT_MIDDLE, LEFT_FRONT_MIDDLE),
                    $(TOP_FRONT_RIGHT, LEFT_FRONT_TOP),
                    $(RIGHT_FRONT_TOP, TOP_FRONT_LEFT),
                    $(RIGHT_FRONT_MIDDLE, TOP_FRONT_MIDDLE),
                    $(RIGHT_FRONT_BOTTOM, TOP_FRONT_RIGHT),
                    $(BOTTOM_FRONT_RIGHT, RIGHT_FRONT_TOP),
                    $(BOTTOM_FRONT_MIDDLE, RIGHT_FRONT_MIDDLE),
                    $(BOTTOM_FRONT_LEFT, RIGHT_FRONT_BOTTOM),
                    $(LEFT_FRONT_BOTTOM, BOTTOM_FRONT_RIGHT),
                    $(LEFT_FRONT_MIDDLE, BOTTOM_FRONT_MIDDLE),
                    $(LEFT_FRONT_TOP, BOTTOM_FRONT_LEFT),
                    $(FRONT_TOP_LEFT, FRONT_BOTTOM_LEFT),
                    $(FRONT_TOP_MIDDLE, FRONT_MIDDLE_LEFT),
                    $(FRONT_TOP_RIGHT, FRONT_TOP_LEFT),
                    $(FRONT_MIDDLE_RIGHT, FRONT_TOP_MIDDLE),
                    $(FRONT_BOTTOM_RIGHT, FRONT_TOP_RIGHT),
                    $(FRONT_BOTTOM_MIDDLE, FRONT_MIDDLE_RIGHT),
                    $(FRONT_BOTTOM_LEFT, FRONT_BOTTOM_RIGHT),
                    $(FRONT_MIDDLE_LEFT, FRONT_BOTTOM_MIDDLE),
                    $(FRONT_TOP_LEFT, FRONT_BOTTOM_LEFT)
            ),
            FRONT_RIGHT, List.of(
                    $(TOP_FRONT_LEFT, RIGHT_FRONT_TOP),
                    $(TOP_FRONT_MIDDLE, RIGHT_FRONT_MIDDLE),
                    $(TOP_FRONT_RIGHT, RIGHT_FRONT_BOTTOM),
                    $(RIGHT_FRONT_TOP, BOTTOM_FRONT_RIGHT),
                    $(RIGHT_FRONT_MIDDLE, BOTTOM_FRONT_MIDDLE),
                    $(RIGHT_FRONT_BOTTOM, BOTTOM_FRONT_LEFT),
                    $(BOTTOM_FRONT_RIGHT, LEFT_FRONT_BOTTOM),
                    $(BOTTOM_FRONT_MIDDLE, LEFT_FRONT_MIDDLE),
                    $(BOTTOM_FRONT_LEFT, LEFT_FRONT_TOP),
                    $(LEFT_FRONT_BOTTOM, TOP_FRONT_LEFT),
                    $(LEFT_FRONT_MIDDLE, TOP_FRONT_MIDDLE),
                    $(LEFT_FRONT_TOP, TOP_FRONT_RIGHT),
                    $(FRONT_TOP_LEFT, FRONT_TOP_RIGHT),
                    $(FRONT_TOP_MIDDLE, FRONT_MIDDLE_RIGHT),
                    $(FRONT_TOP_RIGHT, FRONT_BOTTOM_RIGHT),
                    $(FRONT_MIDDLE_RIGHT, FRONT_BOTTOM_MIDDLE),
                    $(FRONT_BOTTOM_RIGHT, FRONT_BOTTOM_LEFT),
                    $(FRONT_BOTTOM_MIDDLE, FRONT_MIDDLE_LEFT),
                    $(FRONT_BOTTOM_LEFT, FRONT_TOP_LEFT),
                    $(FRONT_MIDDLE_LEFT, FRONT_TOP_MIDDLE),
                    $(FRONT_TOP_LEFT, FRONT_TOP_RIGHT)
            )
            //Max 10 key/value pairs
            /*
            BACK_LEFT, List.of(
            ),
            BACK_RIGHT, List.of(
            )
             */
    ));

    static {
        results.put(
                BACK_LEFT, List.of(
                        $(TOP_BACK_LEFT, LEFT_BACK_BOTTOM),
                        $(TOP_BACK_MIDDLE, LEFT_BACK_MIDDLE),
                        $(TOP_BACK_RIGHT, LEFT_BACK_TOP),
                        $(RIGHT_BACK_TOP, TOP_BACK_LEFT),
                        $(RIGHT_BACK_MIDDLE, TOP_BACK_MIDDLE),
                        $(RIGHT_BACK_BOTTOM, TOP_BACK_RIGHT),
                        $(BOTTOM_BACK_RIGHT, RIGHT_BACK_TOP),
                        $(BOTTOM_BACK_MIDDLE, RIGHT_BACK_MIDDLE),
                        $(BOTTOM_BACK_LEFT, RIGHT_BACK_BOTTOM),
                        $(LEFT_BACK_BOTTOM, BOTTOM_BACK_RIGHT),
                        $(LEFT_BACK_MIDDLE, BOTTOM_BACK_MIDDLE),
                        $(LEFT_BACK_TOP, BOTTOM_BACK_LEFT),
                        $(BACK_TOP_LEFT, BACK_BOTTOM_LEFT),
                        $(BACK_TOP_MIDDLE, BACK_MIDDLE_LEFT),
                        $(BACK_TOP_RIGHT, BACK_TOP_LEFT),
                        $(BACK_MIDDLE_RIGHT, BACK_TOP_MIDDLE),
                        $(BACK_BOTTOM_RIGHT, BACK_TOP_RIGHT),
                        $(BACK_BOTTOM_MIDDLE, BACK_MIDDLE_RIGHT),
                        $(BACK_BOTTOM_LEFT, BACK_BOTTOM_RIGHT),
                        $(BACK_MIDDLE_LEFT, BACK_BOTTOM_MIDDLE),
                        $(BACK_TOP_LEFT, BACK_BOTTOM_LEFT)
                )
        );
        results.put(
                BACK_RIGHT, List.of(
                        $(TOP_BACK_LEFT, RIGHT_BACK_TOP),
                        $(TOP_BACK_MIDDLE, RIGHT_BACK_MIDDLE),
                        $(TOP_BACK_RIGHT, RIGHT_BACK_BOTTOM),
                        $(RIGHT_BACK_TOP, BOTTOM_BACK_RIGHT),
                        $(RIGHT_BACK_MIDDLE, BOTTOM_BACK_MIDDLE),
                        $(RIGHT_BACK_BOTTOM, BOTTOM_BACK_LEFT),
                        $(BOTTOM_BACK_RIGHT, LEFT_BACK_BOTTOM),
                        $(BOTTOM_BACK_MIDDLE, LEFT_BACK_MIDDLE),
                        $(BOTTOM_BACK_LEFT, LEFT_BACK_TOP),
                        $(LEFT_BACK_BOTTOM, TOP_BACK_LEFT),
                        $(LEFT_BACK_MIDDLE, TOP_BACK_MIDDLE),
                        $(LEFT_BACK_TOP, TOP_BACK_RIGHT),
                        $(BACK_TOP_LEFT, BACK_TOP_RIGHT),
                        $(BACK_TOP_MIDDLE, BACK_MIDDLE_RIGHT),
                        $(BACK_TOP_RIGHT, BACK_BOTTOM_RIGHT),
                        $(BACK_MIDDLE_RIGHT, BACK_BOTTOM_MIDDLE),
                        $(BACK_BOTTOM_RIGHT, BACK_BOTTOM_LEFT),
                        $(BACK_BOTTOM_MIDDLE, BACK_MIDDLE_LEFT),
                        $(BACK_BOTTOM_LEFT, BACK_TOP_LEFT),
                        $(BACK_MIDDLE_LEFT, BACK_TOP_MIDDLE),
                        $(BACK_TOP_LEFT, BACK_TOP_RIGHT)
                )
        );
    }

    private static Result $(Position origin, Position destination) {
        return new Result(origin, destination);
    }

    record Result(Position origin, Position destination) {
    }

}
