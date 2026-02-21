package com.breynisson.rubiks.javafx;

import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Scrambler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class CubeApplication extends Application {

    private final CubeModel cubeModel = new CubeModel();

    @Override
    public void start(Stage primaryStage) throws Exception {
        cubeModel.setState(CubeModel.TARGET_STATE);
        //cubeModel.applyActions(Scrambler.createRandomActionList(20, 1));

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
    }
}
