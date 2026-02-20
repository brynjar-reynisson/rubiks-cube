package com.breynisson.rubiks.javafx;

import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Scrambler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CubeApplication extends Application {

    private final CubeModel cubeModel = new CubeModel();

    @Override
    public void start(Stage primaryStage) throws Exception {
        cubeModel.setState(CubeModel.TARGET_STATE);
        cubeModel.applyActions(Scrambler.createRandomActionList(20, 1));
        Canvas canvas = new Canvas(800, 600); // Create a Canvas with specific dimensions
        // Add the Canvas to a layout container (e.g., Group, StackPane)
        Group root = new Group(canvas);
        Scene scene = new Scene(root, 800, 600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        paint(gc);

        primaryStage.setTitle("Rubik's Cube");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void paint(GraphicsContext gc) {
        new CubeDrawer(cubeModel, gc);
/*
        // Clear the canvas
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Get dimensions of the canvas
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();

        // The two cubes should take up 50% of the height and 50% of the width
        double cubeSize = Math.min(width, height) * 0.5;
        double cube1Left = cubeSize * 0.25;
        double cube1Top = height * 0.25;


        double cube2X = width * 0.75 - cubeSize * 0.75; // Center the second cube in the right half
        double cube2Y = height * 0.75 - cubeSize * 0.75; // Center the second cube in the lower half

        gc.setStroke(Color.BLACK);
        gc.strokeLine(cube1Left, cube1Top + cubeSize/6, cube1Left + cubeSize/2, cube1Top); // Draw the first cube's diagonal
        gc.strokeLine(cube1Left + cubeSize/2, cube1Top, cube1Left + cubeSize, cube1Top + cubeSize/6); // Draw the first cube's diagonal
        gc.strokeLine(cube1Left, cube1Top + cubeSize/6, cube1Left, cube1Top + cubeSize/1.33); // Draw the first cube's left vertical line
        gc.strokeLine(cube1Left, cube1Top + cubeSize/1.33, cube1Left + cubeSize/2, cube1Top + cubeSize/1.33 + cubeSize/6); // Draw the first cube's down sloping right line
        gc.strokeLine(cube1Left + cubeSize/2, cube1Top + cubeSize/1.33 + cubeSize/6, cube1Left + cubeSize, cube1Top + cubeSize/1.33); // Draw the first cube's down right sloping up line
        gc.strokeLine(cube1Left + cubeSize, cube1Top + cubeSize/1.33, cube1Left + cubeSize, cube1Top + cubeSize/6); // Draw the first cube's right vertical line

        gc.strokeLine(cube1Left, cube1Top + cubeSize/6, cube1Left + cubeSize/2, cube1Top + cubeSize/3);
        gc.strokeLine(cube1Left + cubeSize/2, cube1Top + cubeSize/3, cube1Left + cubeSize, cube1Top + cubeSize/6);
        gc.strokeLine(cube1Left + cubeSize/2, cube1Top + cubeSize/3,cube1Left + cubeSize/2, cube1Top + cubeSize/1.33 + cubeSize/6);


        //gc.setFill(Color.BLACK);
        //gc.fillRect(cube1Left, cube1Top, cubeSize, cubeSize); // Draw the first cube
        //gc.fillRect(cube2X, cube2Y, cubeSize, cubeSize);
 */
    }
}
