package com.breynisson.rubiks.javafx;

import com.breynisson.rubiks.BrickState;
import com.breynisson.rubiks.CubeModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class CubeDrawer {


    private final CubeModel model;
    private final GraphicsContext gc;
    private final double width;
    private final double height;
    private final double cubeSize;
    private final double cube1LeftX;
    private final double cube1MiddleX;
    private final double cube1RightX;
    private final double cube1TopY;
    private final double cube1FrontTopLeftY;
    private final double cube1FrontBottomLeftY;
    private final double cube1FrontRightTopY;
    private final double cube1BottomY;

    private final double frontVerticalThird;
    private final double frontHorizontalThird;

    public CubeDrawer(CubeModel model, GraphicsContext gc) {
        this.model = model;
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        // The two cubes should take up 50% of the height and 50% of the width
        this.cubeSize = Math.min(width, height) * 0.5;
        cube1LeftX = cubeSize * 0.25;
        cube1MiddleX = cube1LeftX + cubeSize / 2;
        cube1RightX = cube1LeftX + cubeSize;

        cube1TopY = height * 0.25;
        cube1FrontTopLeftY = cube1TopY + cubeSize / 6;
        cube1FrontBottomLeftY = cube1TopY + cubeSize / 1.33;
        cube1FrontRightTopY = cube1TopY + cubeSize / 3;
        cube1BottomY = cube1FrontBottomLeftY + cubeSize / 6;

        frontVerticalThird = (cube1MiddleX - cube1LeftX) / 3;
        frontHorizontalThird = (cube1FrontBottomLeftY - cube1FrontTopLeftY) / 3;

        paint();
    }

    private void paint() {
        // Clear the canvas
        gc.clearRect(0, 0, width, height);

        gc.setStroke(Color.BLACK);
        gc.strokeLine(cube1LeftX, cube1FrontTopLeftY, cube1MiddleX, cube1TopY); // Draw the first cube's diagonal
        gc.strokeLine(cube1MiddleX, cube1TopY, cube1RightX, cube1FrontTopLeftY); // Draw the first cube's diagonal
        gc.strokeLine(cube1LeftX, cube1FrontTopLeftY, cube1LeftX, cube1FrontBottomLeftY); // Draw the first cube's left vertical line
        gc.strokeLine(cube1LeftX, cube1FrontBottomLeftY, cube1MiddleX, cube1BottomY); // Draw the first cube's down sloping right line
        gc.strokeLine(cube1MiddleX, cube1BottomY, cube1RightX, cube1FrontBottomLeftY); // Draw the first cube's down right sloping up line
        gc.strokeLine(cube1RightX, cube1FrontBottomLeftY, cube1RightX, cube1FrontTopLeftY); // Draw the first cube's right vertical line

        gc.strokeLine(cube1LeftX, cube1FrontTopLeftY, cube1MiddleX, cube1FrontRightTopY);
        gc.strokeLine(cube1MiddleX, cube1FrontRightTopY, cube1RightX, cube1FrontTopLeftY);
        gc.strokeLine(cube1MiddleX, cube1FrontRightTopY, cube1MiddleX, cube1BottomY);

        double mFront = (cube1FrontRightTopY-cube1FrontTopLeftY)/(cube1MiddleX-cube1LeftX);
        //solution for line slope is double y = m * (x-x0) + y0;

        double frontX1 = cube1LeftX + frontVerticalThird;
        double frontX2 = cube1LeftX + frontVerticalThird * 2;
        double y1 = mFront * frontVerticalThird + cube1FrontTopLeftY;
        double frontYOffset = y1 - cube1FrontTopLeftY;

        for (BrickState brickState : model.getState()) {
            CubePolygon cubePolygon = null;
            switch (brickState.position()) {
                case FRONT_TOP_LEFT -> {
                    double yRight = cube1FrontTopLeftY + frontYOffset;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{cube1LeftX, frontX1, frontX1, cube1LeftX, cube1LeftX},
                            new double[]{cube1FrontTopLeftY, yRight, yRight + frontHorizontalThird, cube1FrontTopLeftY + frontHorizontalThird, cube1FrontTopLeftY}
                    );
                }
                case FRONT_TOP_MIDDLE -> {
                    double yLeft = cube1FrontTopLeftY + frontYOffset;
                    double yRight = cube1FrontTopLeftY + frontYOffset * 2;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX1, frontX2, frontX2, frontX1, frontX1},
                            new double[]{yLeft, yRight, yRight + frontHorizontalThird, yLeft + frontHorizontalThird, yLeft}
                    );
                }
                case FRONT_TOP_RIGHT -> {
                    double yLeft = cube1FrontTopLeftY + frontYOffset * 2;
                    double yRight = cube1FrontTopLeftY + frontYOffset * 3;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX2, cube1LeftX + frontVerticalThird * 3, cube1LeftX + frontVerticalThird * 3, frontX2, frontX2},
                            new double[]{yLeft, yRight, yRight + frontHorizontalThird, yLeft + frontHorizontalThird, yLeft}
                    );
                }
                case FRONT_MIDDLE_LEFT -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{cube1LeftX, frontX1, frontX1, cube1LeftX, cube1LeftX},
                            new double[]{baseY, baseY + frontYOffset, baseY + frontYOffset + frontHorizontalThird, baseY + frontHorizontalThird, baseY}
                    );
                }
                case FRONT_MIDDLE_MIDDLE -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird;
                    double yLeft = cube1FrontTopLeftY + frontYOffset;
                    double yRight = cube1FrontTopLeftY + frontYOffset * 2;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX1, frontX2, frontX2, frontX1, frontX1},
                            new double[]{baseY + frontYOffset, baseY + frontYOffset * 2, baseY + frontYOffset * 2 + frontHorizontalThird, baseY + frontYOffset + frontHorizontalThird, baseY}
                    );
                }
                case FRONT_MIDDLE_RIGHT -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX2, cube1LeftX + frontVerticalThird * 3, cube1LeftX + frontVerticalThird * 3, frontX2, frontX2},
                            new double[]{baseY + frontYOffset * 2, baseY + frontYOffset * 3, baseY + frontYOffset * 3 + frontHorizontalThird, baseY + frontYOffset * 2 + frontHorizontalThird, baseY}
                    );
                }
                case FRONT_BOTTOM_LEFT -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird * 2;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{cube1LeftX, frontX1, frontX1, cube1LeftX, cube1LeftX},
                            new double[]{baseY, baseY + frontYOffset, baseY + frontYOffset + frontHorizontalThird, baseY + frontHorizontalThird, baseY}
                    );
                }
                case FRONT_BOTTOM_MIDDLE -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird * 2;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX1, frontX2, frontX2, frontX1, frontX1},
                            new double[]{baseY + frontYOffset, baseY + frontYOffset * 2, baseY + frontYOffset * 2 + frontHorizontalThird, baseY + frontYOffset + frontHorizontalThird, baseY}
                    );
                }
                case FRONT_BOTTOM_RIGHT -> {
                    double baseY = cube1FrontTopLeftY + frontHorizontalThird * 2;
                    cubePolygon = new CubePolygon(
                            getColorForBrickState(brickState),
                            new double[]{frontX2, cube1LeftX + frontVerticalThird * 3, cube1LeftX + frontVerticalThird * 3, frontX2, frontX2},
                            new double[]{baseY + frontYOffset * 2, baseY + frontYOffset * 3, baseY + frontYOffset * 3 + frontHorizontalThird, baseY + frontYOffset * 2 + frontHorizontalThird, baseY}
                    );
                }
                default -> {
                    // Not implemented yet
                }
            }
            if (cubePolygon != null) {
                gc.setFill(cubePolygon.color);
                gc.fillPolygon(cubePolygon.xPoints, cubePolygon.yPoints, cubePolygon.xPoints.length);
                for (int i=0; i < cubePolygon.xPoints.length; i++) {
                    gc.setStroke(Color.BLACK);
                    gc.strokeLine(cubePolygon.xPoints[i], cubePolygon.yPoints[i], cubePolygon.xPoints[(i+1) % cubePolygon.xPoints.length], cubePolygon.yPoints[(i+1) % cubePolygon.yPoints.length]);
                }
            }
        }
    }

    Color getColorForBrickState(BrickState brickState) {
        return switch (brickState.color()) {
            case WHITE -> Color.WHITE;
            case YELLOW -> Color.YELLOW;
            case RED -> Color.RED;
            case ORANGE -> Color.ORANGE;
            case BLUE -> Color.BLUE;
            case GREEN -> Color.GREEN;
        };
    }

    private final class CubePolygon {
        private Color color;
        private double[] xPoints;
        private double[] yPoints;

        private CubePolygon(Color color, double[] xPoints, double[] yPoints) {
            this.color = color;
            this.xPoints = xPoints;
            this.yPoints = yPoints;
        }
    }

}