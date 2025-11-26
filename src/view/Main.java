package view;

import controller.SupermarketSimulatorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SupermarketView view = new SupermarketView();
        new SupermarketSimulatorController(view); // attach controller

        Scene scene = new Scene(view.getRoot());
        stage.setScene(scene);
        stage.setTitle("Supermarket Simulator");
        stage.show();

        // Important: request focus so WASD keys work
        view.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
