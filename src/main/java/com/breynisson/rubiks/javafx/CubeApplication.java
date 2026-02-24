package com.breynisson.rubiks.javafx;

import com.breynisson.rubiks.Action;
import com.breynisson.rubiks.CubeModel;
import com.breynisson.rubiks.Scrambler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CubeApplication extends Application {

    private final CubeModel cubeModel = new CubeModel();
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        cubeModel.setState(CubeModel.TARGET_STATE);
        //cubeModel.applyActions(Scrambler.createRandomActionList(3, 1));

        canvas = new Canvas(800, 600); // Create a Canvas with specific dimensions
        HBox buttonPane = new HBox(10);
        buttonPane.setLayoutY(610);
        buttonPane.setLayoutX(50);

        List<Button> buttons = new ArrayList<>();
        for (Action action : Action.values()) {
            buttons.add(createButton(action));
        }

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            cubeModel.setState(CubeModel.TARGET_STATE);
            paint(canvas.getGraphicsContext2D());
        });
        buttons.add(resetButton);

        Button scrambleButton = new Button("Scramble");
        scrambleButton.setOnAction(e -> {
                cubeModel.applyActions(Scrambler.createRandomActionList(20, new Random().nextInt()));
                paint(canvas.getGraphicsContext2D());
        });
        buttons.add(scrambleButton);

        for (Button button : buttons) {
            buttonPane.getChildren().add(button);
        }

        Group root = new Group(canvas, buttonPane);
        Scene scene = new Scene(root, 800, 650);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        paint(gc);

        primaryStage.setTitle("Rubik's Cube");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void paint(GraphicsContext gc) {
        new CubeDrawer(cubeModel, gc);
    }

    private Button createButton(Action action) {
        Button button = new Button(action.standardNotation);
        button.setOnAction(e -> {
            cubeModel.applyAction(action);
            paint(canvas.getGraphicsContext2D());
        });
        return button;
    }

     public static void main(String[] args) {
         launch(args);
     }
}
