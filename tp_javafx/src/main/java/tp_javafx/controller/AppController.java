package tp_javafx.controller;

import javafx.concurrent.Task;
import tp_javafx.api.AppointmentApi;
import tp_javafx.model.Appointment;
import tp_javafx.view.AppView;

import java.util.List;

public class AppController {

    private final AppView view;
    private final AppointmentApi api;
    private final String token;
    private final Runnable onLogout;
    private final Runnable onExit;

    public AppController(AppView view, AppointmentApi api, String token, Runnable onLogout, Runnable onExit) {
        this.view = view;
        this.api = api;
        this.token = token;
        this.onLogout = onLogout;
        this.onExit = onExit;
    }

    public void init() {
        // Menu actions
        view.getLogoutItem().setOnAction(e -> onLogout.run());
        view.getExitItem().setOnAction(e -> onExit.run());

        // Chargement initial
        loadAppointments();
    }

    private void loadAppointments() {
        view.setBusy(true);

        Task<List<Appointment>> task = new Task<>() {
            @Override
            protected List<Appointment> call() {
                return api.fetchAppointments(token);
            }
        };

        task.setOnSucceeded(e -> {
            view.setBusy(false);
            List<Appointment> result = task.getValue();
            view.getAppointments().setAll(result); // refresh TableView auto
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
