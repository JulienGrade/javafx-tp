package tp12.controller;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import tp12.api.UserApi;
import tp12.model.User;
import tp12.view.AppView;

import java.util.List;

public class AppController {

    private final AppView view;
    private final UserApi api;
    private final String token;

    private final Runnable onLogout;
    private final Runnable onExit;

    public AppController(AppView view, UserApi api, String token, Runnable onLogout, Runnable onExit) {
        this.view = view;
        this.api = api;
        this.token = token;
        this.onLogout = onLogout;
        this.onExit = onExit;
    }

    public void init() {
        view.getAddButton().setOnAction(e -> addUser());
        view.getLogoutItem().setOnAction(e -> onLogout.run());
        view.getExitItem().setOnAction(e -> onExit.run());

        loadUsers();
    }

    private void loadUsers() {
        view.setBusy(true);

        Task<List<User>> task = new Task<>() {
            @Override
            protected List<User> call() throws Exception {
                return api.fetchUsers(token);
            }
        };

        task.setOnSucceeded(ev -> {
            view.setBusy(false);
            view.getUsers().setAll(task.getValue()); // ✅ refresh UI
        });

        task.setOnFailed(ev -> {
            view.setBusy(false);
            String msg = task.getException() != null ? task.getException().getMessage() : "Erreur inconnue.";
            view.showAlert(Alert.AlertType.ERROR, "Erreur serveur simulée", msg);
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    private void addUser() {
        String name = view.getNameField().getText();
        String email = view.getEmailField().getText();
        String ageText = view.getAgeField().getText();

        if (name.isBlank() || email.isBlank() || ageText.isBlank()) {
            view.showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText.trim());
        } catch (NumberFormatException ex) {
            view.showAlert(Alert.AlertType.ERROR, "Erreur", "L'âge doit être un nombre entier.");
            return;
        }

        User user = new User(name.trim(), email.trim(), age);

        view.setBusy(true);

        Task<User> task = new Task<>() {
            @Override
            protected User call() throws Exception {
                return api.createUser(token, user);
            }
        };

        task.setOnSucceeded(ev -> {
            view.setBusy(false);
            view.getUsers().add(task.getValue()); // ✅ update UI
            view.showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté (API simulée).");
            view.clearForm();
        });

        task.setOnFailed(ev -> {
            view.setBusy(false);
            String msg = task.getException() != null ? task.getException().getMessage() : "Erreur inconnue.";
            view.showAlert(Alert.AlertType.ERROR, "Erreur serveur simulée", msg);
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
