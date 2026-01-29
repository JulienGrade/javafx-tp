package tp_javafx.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Se connecter");

    public Scene createScene(Runnable onLoginClicked) {
        Label title = new Label("Connexion à RDV-java");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(220);

        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(220);

        loginButton.setDefaultButton(true);
        loginButton.setOnAction(e -> onLoginClicked.run());

        VBox root = new VBox(12, title, usernameField, passwordField, loginButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        return new Scene(root, 380, 260);
    }

    public TextField getUsernameField() { return usernameField; }
    public PasswordField getPasswordField() { return passwordField; }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
