package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Button btn = new Button("Hello JavaFX on Java 8");
        btn.setOnAction(e -> System.out.println("Button clicked!"));

        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JavaFX on Java 8");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
