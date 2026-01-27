package tp5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp5.Main");

        Label title = new Label("Création d'utilisateur");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nom :");
        Label emailLabel = new Label("Email :");
        Label ageLabel = new Label("Âge :");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();

        Button addButton = new Button("Ajouter");

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        // Titre sur 2 colonnes
        grid.add(title, 0, 0, 2, 1);

        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(ageLabel, 0, 3);
        grid.add(ageField, 1, 3);

        grid.add(addButton, 1, 4);

        Scene scene = new Scene(grid, 420, 300);
        stage.setTitle("JavaFX - Formulaire structuré (TP5)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
