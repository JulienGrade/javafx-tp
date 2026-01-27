package tp6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tp6.model.User;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    // Liste en attribut (pas dans start)
    private final List<User> users = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp6.Main");

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
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));

        grid.add(title, 0, 0, 2, 1);

        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(ageLabel, 0, 3);
        grid.add(ageField, 1, 3);

        grid.add(addButton, 1, 4);

        // --- Event (TP6) ---
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String ageText = ageField.getText();

            // 1) Validation minimale
            if (name.isBlank() || email.isBlank() || ageText.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText.trim());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'âge doit être un nombre entier.");
                return;
            }

            if (age <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'âge doit être supérieur à 0.");
                return;
            }

            // 2) Mapping -> objet métier
            User user = new User(name.trim(), email.trim(), age);

            // 3) Stockage en mémoire
            users.add(user);

            System.out.println("User ajouté: " + user);
            System.out.println("Total users: " + users.size());

            // 4) Feedback utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté avec succès.");

            // Bonus UX : reset champs
            nameField.clear();
            emailField.clear();
            ageField.clear();
            nameField.requestFocus();
        });

        Scene scene = new Scene(grid, 420, 300);
        stage.setTitle("JavaFX - Mapping vers User (TP6)");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
