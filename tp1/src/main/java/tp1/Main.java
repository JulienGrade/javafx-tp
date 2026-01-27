package tp1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Root vide (mais réel) pour la Scene
        StackPane root = new StackPane();

        // Scene + dimensions
        Scene scene = new Scene(root, 600, 400);

        // Configuration fenêtre
        stage.setTitle("TP1 - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
