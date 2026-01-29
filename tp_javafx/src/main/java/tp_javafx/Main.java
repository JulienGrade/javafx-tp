package tp_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp_javafx.view.AppView;
import tp_javafx.view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp_javafx.Main");
        stage.setTitle("TP RDV - Étape 2 (Login -> App)");

        LoginView loginView = new LoginView();
        AppView appView = new AppView();

        // On prépare les 2 scènes (réutilisables)
        final Scene[] loginScene = new Scene[1];
        final Scene[] appScene = new Scene[1];

        loginScene[0] = loginView.createScene(() -> {
            String username = loginView.getUsernameField().getText();
            String password = loginView.getPasswordField().getText();

            if (username.isBlank() || password.isBlank()) {
                loginView.showError("Les champs ne doivent pas être vides.");
                return;
            }

            // Auth hardcodée (pour commit 2)
            if (username.equals("agent") && password.equals("support")) {
                stage.setScene(appScene[0]);
            } else {
                loginView.showError("Identifiants invalides. help :(agent / support)");
            }
        });
        appScene[0] = appView.createScene(() -> stage.setScene(loginScene[0]));

        stage.setScene(loginScene[0]);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
