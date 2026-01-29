package tp_javafx.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AppView {

    private final Button logoutButton = new Button("Déconnexion");

    public Scene createScene(Runnable onLogoutClicked) {
        Label title = new Label("Application");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        logoutButton.setOnAction(e -> onLogoutClicked.run());

        VBox root = new VBox(14, title, new Label("Login OK, bienvenue."), logoutButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        return new Scene(root, 700, 500);
    }
}
