package tp12.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import tp12.api.UserApi;
import tp12.view.LoginView;

import java.util.function.Consumer;

public class LoginController {

    private final LoginView view;
    private final UserApi api;
    private final Consumer<String> onLoginSuccess; // reçoit le token

    public LoginController(LoginView view, UserApi api, Consumer<String> onLoginSuccess) {
        this.view = view;
        this.api = api;
        this.onLoginSuccess = onLoginSuccess;
    }

    public void init() {
        view.getLoginButton().setOnAction(e -> login());
    }

    private void login() {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        if (username.isBlank() || password.isBlank()) {
            view.showAlert(Alert.AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }

        view.setBusy(true);

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                return api.login(username.trim(), password.trim());
            }
        };

        task.setOnSucceeded(ev -> {
            view.setBusy(false);
            String token = task.getValue();
            onLoginSuccess.accept(token);
        });

        task.setOnFailed(ev -> {
            view.setBusy(false);
            String msg = task.getException() != null ? task.getException().getMessage() : "Erreur inconnue.";
            view.showAlert(Alert.AlertType.ERROR, "Erreur", msg);
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
