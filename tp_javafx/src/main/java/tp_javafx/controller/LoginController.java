package tp_javafx.controller;

import javafx.concurrent.Task;
import tp_javafx.api.AppointmentApi;
import tp_javafx.view.LoginView;

import java.util.function.Consumer;

public class LoginController {

    private final LoginView view;
    private final AppointmentApi api;
    private final Consumer<String> onLoginSuccess;

    public LoginController(LoginView view, AppointmentApi api, Consumer<String> onLoginSuccess) {
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

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            view.showError("Les champs ne doivent pas être vides.");
            return;
        }

        view.setBusy(true);

        Task<String> task = new Task<>() {
            @Override
            protected String call() {
                return api.login(username.trim(), password);
            }
        };

        task.setOnSucceeded(e -> {
            view.setBusy(false);
            String token = task.getValue();
            onLoginSuccess.accept(token);
        });

        task.setOnFailed(e -> {
            view.setBusy(false);
            Throwable ex = task.getException();
            view.showError(ex == null ? "Erreur inconnue." : ex.getMessage());
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
