package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class EventManagementApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a TabPane
        TabPane tabPane = new TabPane();

        // 1) Events tab
        Tab tabEvents = new Tab("Events");
        tabEvents.setClosable(false);
        // We'll create a separate view class (EventsCRUDView)
        tabEvents.setContent(new EventsCRUDView().getRoot());

        // 2) Registration tab
        Tab tabRegistration = new Tab("Registration");
        tabRegistration.setClosable(false);
        tabRegistration.setContent(new RegistrationView().getRoot());

        // 3) Assign Instructors tab
        Tab tabAssign = new Tab("Assign Instructors");
        tabAssign.setClosable(false);
        tabAssign.setContent(new AssignInstructorsView().getRoot());

        // Add tabs to the TabPane
        tabPane.getTabs().addAll(tabEvents, tabRegistration, tabAssign);

        // Scene & Stage
        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("Event Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
