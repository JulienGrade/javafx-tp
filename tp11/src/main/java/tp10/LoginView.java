package tp11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    public Scene createScene(Runnable onLoginSuccess) {
        Label title = new Label("Connexion");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(220);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(220);

        Button loginButton = new Button("Se connecter");
        loginButton.setDefaultButton(true);

        VBox root = new VBox(12, title, usernameField, passwordField, loginButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isBlank() || password.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
                return;
            }

            // Auth pédagogique
            if (username.equals("admin") && password.equals("admin")) {
                onLoginSuccess.run();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Identifiants invalides.");
            }
        });

        return new Scene(root, 380, 260);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
