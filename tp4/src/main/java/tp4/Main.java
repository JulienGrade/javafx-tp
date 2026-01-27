package tp4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int MAX_ATTEMPTS = 3;
    private int attemptsLeft = MAX_ATTEMPTS;

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp4.Main");

        // --- UI ---
        Label title = new Label("Connexion");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label attemptsLabel = new Label(getAttemptsText());
        attemptsLabel.setStyle("-fx-opacity: 0.85;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(220);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(220);

        Button loginButton = new Button("Se connecter");
        loginButton.setDefaultButton(true);

        VBox root = new VBox(15, title, attemptsLabel, usernameField, passwordField, loginButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // --- Event ---
        loginButton.setOnAction(e -> {
            if (attemptsLeft <= 0) {
                // Sécurité UX : ne rien faire si bloqué
                return;
            }

            String username = usernameField.getText();
            String password = passwordField.getText();

            // 1) Validation basique
            if (username.isBlank() || password.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            // 2) Simulation d'auth (toujours invalide dans ce TP)
            // Bonne pratique : message non précis
            attemptsLeft--;
            attemptsLabel.setText(getAttemptsText());

            if (attemptsLeft > 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Identifiants invalides.");
            } else {
                lockForm(usernameField, passwordField, loginButton);
                showAlert(Alert.AlertType.ERROR, "Compte bloqué",
                        "Trop de tentatives. Veuillez réessayer plus tard.");
            }
        });

        Platform.runLater(usernameField::requestFocus);

        // --- Stage ---
        Scene scene = new Scene(root, 380, 300);
        stage.setTitle("JavaFX - Connexion sécurisée (TP4)");
        stage.setScene(scene);
        stage.show();
    }

    private String getAttemptsText() {
        return "Tentatives restantes : " + attemptsLeft + " / " + MAX_ATTEMPTS;
    }

    private void lockForm(TextField usernameField, PasswordField passwordField, Button loginButton) {
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        loginButton.setDisable(true);
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
