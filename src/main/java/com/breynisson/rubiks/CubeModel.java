package com.breynisson.rubiks;

import java.util.*;
import java.util.stream.Collectors;

import static com.breynisson.rubiks.Color.*;
import static com.breynisson.rubiks.Position.*;
import static com.breynisson.rubiks.Side.*;

public class CubeModel {

    private final Map<Position, BrickState> brickStates = new HashMap<>();
    private final Map<Integer, BrickState> brickStatesByOrdinal = new HashMap<>();

    public static final List<BrickState> TARGET_STATE = List.of(
            $(FRONT_TOP_LEFT, WHITE),
            $(FRONT_TOP_MIDDLE, WHITE),
            $(FRONT_TOP_RIGHT, WHITE),
            $(FRONT_MIDDLE_LEFT, WHITE),
            $(FRONT_MIDDLE_MIDDLE, WHITE),
            $(FRONT_MIDDLE_RIGHT, WHITE),
            $(FRONT_BOTTOM_LEFT, WHITE),
            $(FRONT_BOTTOM_MIDDLE, WHITE),
            $(FRONT_BOTTOM_RIGHT, WHITE),
            $(LEFT_FRONT_TOP, ORANGE),
            $(LEFT_FRONT_MIDDLE, ORANGE),
            $(LEFT_FRONT_BOTTOM, ORANGE),
            $(LEFT_MIDDLE_TOP, ORANGE),
            $(LEFT_MIDDLE_MIDDLE, ORANGE),
            $(LEFT_MIDDLE_BOTTOM, ORANGE),
            $(LEFT_BACK_TOP, ORANGE),
            $(LEFT_BACK_MIDDLE, ORANGE),
            $(LEFT_BACK_BOTTOM, ORANGE),
            $(TOP_FRONT_LEFT, BLUE),
            $(TOP_FRONT_MIDDLE, BLUE),
            $(TOP_FRONT_RIGHT, BLUE),
            $(TOP_MIDDLE_LEFT, BLUE),
            $(TOP_MIDDLE_MIDDLE, BLUE),
            $(TOP_MIDDLE_RIGHT, BLUE),
            $(TOP_BACK_LEFT, BLUE),
            $(TOP_BACK_MIDDLE, BLUE),
            $(TOP_BACK_RIGHT, BLUE),
            $(RIGHT_FRONT_TOP, RED),
            $(RIGHT_FRONT_MIDDLE, RED),
            $(RIGHT_FRONT_BOTTOM, RED),
            $(RIGHT_MIDDLE_TOP, RED),
            $(RIGHT_MIDDLE_MIDDLE, RED),
            $(RIGHT_MIDDLE_BOTTOM, RED),
            $(RIGHT_BACK_TOP, RED),
            $(RIGHT_BACK_MIDDLE, RED),
            $(RIGHT_BACK_BOTTOM, RED),
            $(BOTTOM_FRONT_LEFT, GREEN),
            $(BOTTOM_FRONT_MIDDLE, GREEN),
            $(BOTTOM_FRONT_RIGHT, GREEN),
            $(BOTTOM_MIDDLE_LEFT, GREEN),
            $(BOTTOM_MIDDLE_MIDDLE, GREEN),
            $(BOTTOM_MIDDLE_RIGHT, GREEN),
            $(BOTTOM_BACK_LEFT, GREEN),
            $(BOTTOM_BACK_MIDDLE, GREEN),
            $(BOTTOM_BACK_RIGHT, GREEN),
            $(BACK_TOP_LEFT, YELLOW),
            $(BACK_TOP_MIDDLE, YELLOW),
            $(BACK_TOP_RIGHT, YELLOW),
            $(BACK_MIDDLE_LEFT, YELLOW),
            $(BACK_MIDDLE_MIDDLE, YELLOW),
            $(BACK_MIDDLE_RIGHT, YELLOW),
            $(BACK_BOTTOM_LEFT, YELLOW),
            $(BACK_BOTTOM_MIDDLE, YELLOW),
            $(BACK_BOTTOM_RIGHT, YELLOW)
            );

    private static BrickState $(Position position, Color color) {
        return new BrickState(position, color);
    }

    public CubeModel createClone() {
        CubeModel theClone = new CubeModel();
        theClone.brickStates.clear();
        theClone.brickStatesByOrdinal.clear();
        theClone.brickStates.putAll(brickStates);
        theClone.brickStatesByOrdinal.putAll(brickStatesByOrdinal);
        return theClone;
    }

    public void setState(List<BrickState> brickStates) {
        this.brickStates.clear();
        this.brickStatesByOrdinal.clear();
        for (BrickState brickState : brickStates) {
            this.brickStates.put(brickState.position(), brickState);
            this.brickStatesByOrdinal.put(brickState.position().ordinal(), brickState);
        }
        if (this.brickStates.size() != 54) {
            throw new CubeException("State must contain exactly 54 brick states");
        }
    }

    public List<BrickState> getState() {
        return new ArrayList<>(brickStates.values());
    }

    public void applyStandardNotationActions(Collection<String> standardNotationActions) {
        List<Action> actions = new ArrayList<>();
        for (String standardNotationAction : standardNotationActions) {
            Action action = Action.actionByStandardNotation.get(standardNotationAction);
            if (action == null) {
                throw new CubeException("Unknown standard notation action: " + standardNotationAction);
            }
            actions.add(action);
        }
        applyActions(actions);
    }

    public void applyActions(Collection<Action> actions) {
        for (Action action : actions) {
            applyAction(action);
        }
    }

    public void applyAction(Action action) {
        List<Action.Result> results = Action.results.get(action);
        if (results == null) {
            throw new CubeException("Results not defined for action " + action);
        }
        //all destinations should have an origin in the list
        //all origins should have a destination in the list
        Set<Position> origins = results.stream().map(Action.Result::origin).collect(Collectors.toSet());
        Set<Position> destinations = results.stream().map(Action.Result::destination).collect(Collectors.toSet());
        for (Position origin : origins) {
            if (!destinations.contains(origin)) {
                throw new CubeException("Destinations for action " + action + " don't contain origin " + origin);
            }
        }
        for (Position destination : destinations) {
            if (!origins.contains(destination)) {
                throw new CubeException("Origins for action " + action + " don't contain destination " + destination);
            }
        }

        //get color from origin and apply to destination
        List<BrickState> updates = new ArrayList<>();
        for (Action.Result result : results) {
            Color originalColor = brickStates.get(result.origin()).color();
            updates.add(new BrickState(result.destination(), originalColor));
        }
        for (BrickState update : updates) {
            brickStates.put(update.position(), update);
            brickStatesByOrdinal.put(update.position().ordinal(), update);
        }
    }

    int[] frontOrdinals = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    int[] leftOrdinals = {
            LEFT_BACK_TOP.ordinal(), LEFT_MIDDLE_TOP.ordinal(), LEFT_FRONT_TOP.ordinal(),
            LEFT_BACK_MIDDLE.ordinal(), LEFT_MIDDLE_MIDDLE.ordinal(), LEFT_FRONT_MIDDLE.ordinal(),
            LEFT_BACK_BOTTOM.ordinal(), LEFT_MIDDLE_BOTTOM.ordinal(), LEFT_FRONT_BOTTOM.ordinal()
    };
    int[] topOrdinals = {
            TOP_BACK_LEFT.ordinal(), TOP_BACK_MIDDLE.ordinal(), TOP_BACK_RIGHT.ordinal(),
            TOP_MIDDLE_LEFT.ordinal(), TOP_MIDDLE_MIDDLE.ordinal(), TOP_MIDDLE_RIGHT.ordinal(),
            TOP_FRONT_LEFT.ordinal(), TOP_FRONT_MIDDLE.ordinal(), TOP_FRONT_RIGHT.ordinal()
    };
    int[] rightOrdinals = {
            RIGHT_FRONT_TOP.ordinal(), RIGHT_MIDDLE_TOP.ordinal(), RIGHT_BACK_TOP.ordinal(),
            RIGHT_FRONT_MIDDLE.ordinal(), RIGHT_MIDDLE_MIDDLE.ordinal(), RIGHT_BACK_MIDDLE.ordinal(),
            RIGHT_FRONT_BOTTOM.ordinal(), RIGHT_MIDDLE_BOTTOM.ordinal(), RIGHT_BACK_BOTTOM.ordinal()
    };
    int[] bottomOrdinals = {
            BOTTOM_FRONT_LEFT.ordinal(), BOTTOM_FRONT_MIDDLE.ordinal(), BOTTOM_FRONT_RIGHT.ordinal(),
            BOTTOM_MIDDLE_LEFT.ordinal(), BOTTOM_MIDDLE_MIDDLE.ordinal(), BOTTOM_MIDDLE_RIGHT.ordinal(),
            BOTTOM_BACK_LEFT.ordinal(), BOTTOM_BACK_MIDDLE.ordinal(), BOTTOM_BACK_RIGHT.ordinal()
    };
    int[] backOrdinals = {
            //we display as if it was seen through the backside of it
            BACK_TOP_LEFT.ordinal(), BACK_TOP_MIDDLE.ordinal(), BACK_TOP_RIGHT.ordinal(),
            BACK_MIDDLE_LEFT.ordinal(), BACK_MIDDLE_MIDDLE.ordinal(), BACK_MIDDLE_RIGHT.ordinal(),
            BACK_BOTTOM_LEFT.ordinal(), BACK_BOTTOM_MIDDLE.ordinal(), BACK_BOTTOM_RIGHT.ordinal()
    };
    public String displaySide(Side side) {
        return switch (side) {
            case FRONT -> displayOrdinals(frontOrdinals);
            case LEFT -> displayOrdinals(leftOrdinals);
            case TOP -> displayOrdinals(topOrdinals);
            case RIGHT -> displayOrdinals(rightOrdinals);
            case BOTTOM -> displayOrdinals(bottomOrdinals);
            case BACK -> displayOrdinals(backOrdinals);
        };
    }

    public String displayCube() {
        return "Front:\n" + displaySide(FRONT) + "\n" +
                "Left:\n" + displaySide(LEFT) + "\n" +
                "Right:\n" + displaySide(RIGHT) + "\n" +
                "Top:\n" + displaySide(TOP) + "\n" +
                "Bottom:\n" + displaySide(BOTTOM) + "\n" +
                "Back see-through:\n" + displaySide(BACK) + "\n";
    }

    public boolean equalsTargetState() {
        for (BrickState brickState : TARGET_STATE) {
            BrickState modelBrickState = brickStatesByOrdinal.get(brickState.position().ordinal());
            if (!modelBrickState.color().equals(brickState.color())) {
                return false;
            }
        }
        return true;
    }

    public int differenceWithTargetState() {
        int differences = 0;
        for (BrickState brickState : TARGET_STATE) {
            BrickState modelBrickState = brickStatesByOrdinal.get(brickState.position().ordinal());
            if (!modelBrickState.color().equals(brickState.color())) {
                if (Position.isCornerPosition(brickState.position())) {
                    differences += 2;
                } else {
                    differences++;
                }
            }
        }
        return differences;
    }

    private String displayOrdinals(int[] ordinals) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            buf.append(shortDisplayString(brickStatesByOrdinal.get(ordinals[i])));
        }
        buf.append("\n");
        for (int i = 3; i < 6; i++) {
            buf.append(shortDisplayString(brickStatesByOrdinal.get(ordinals[i])));
        }
        buf.append("\n");
        for (int i = 6; i < 9; i++) {
            buf.append(shortDisplayString(brickStatesByOrdinal.get(ordinals[i])));
        }
        return buf.toString();
    }

    private String shortDisplayString(BrickState brickState) {
        return Color.shortDisplay(brickState.color());
    }
}
