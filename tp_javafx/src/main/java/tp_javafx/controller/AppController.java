package tp_javafx.controller;

import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import tp_javafx.api.AppointmentApi;
import tp_javafx.model.Appointment;
import tp_javafx.model.AppointmentStatus;
import tp_javafx.view.AppView;

import java.time.LocalDate;
import java.util.List;

public class AppController {

    private final AppView view;
    private final AppointmentApi api;
    private final String token;
    private final Runnable onLogout;
    private final Runnable onExit;

    private FilteredList<Appointment> filtered; // <- étape 8

    public AppController(AppView view, AppointmentApi api, String token, Runnable onLogout, Runnable onExit) {
        this.view = view;
        this.api = api;
        this.token = token;
        this.onLogout = onLogout;
        this.onExit = onExit;
    }

    public void init() {
        view.getLogoutItem().setOnAction(e -> onLogout.run());
        view.getExitItem().setOnAction(e -> onExit.run());

        view.getAddButton().setOnAction(e -> addAppointment());

        // Filtrage: brancher la table sur une FilteredList
        filtered = new FilteredList<>(view.getAppointments(), a -> true);
        view.getTable().setItems(filtered);

        // Listeners filtres (recalcul predicate à chaque changement)
        view.getSearchField().textProperty().addListener((obs, o, n) -> applyFilters());
        view.getStatusFilterBox().valueProperty().addListener((obs, o, n) -> applyFilters());

        loadAppointments();
    }

    private void applyFilters() {
        String query = view.getSearchField().getText();
        String statusChoice = view.getStatusFilterBox().getValue();

        final String q = (query == null) ? "" : query.trim().toLowerCase();
        final boolean allStatus = (statusChoice == null || statusChoice.equals("Tous"));

        filtered.setPredicate(appt -> {
            if (appt == null) return false;

            // filtre statut
            if (!allStatus) {
                AppointmentStatus s = appt.getStatus();
                if (s == null || !s.name().equals(statusChoice)) return false;
            }

            // recherche texte (client/email/motif)
            if (q.isEmpty()) return true;

            return contains(appt.getClientName(), q)
                    || contains(appt.getEmail(), q)
                    || contains(appt.getReason(), q);
        });
    }

    private boolean contains(String value, String query) {
        return value != null && value.toLowerCase().contains(query);
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
            view.getAppointments().setAll(task.getValue());
            applyFilters(); // important : recalcul après setAll
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

    private void addAppointment() {
        String client = view.getClientField().getText();
        String email = view.getEmailField().getText();
        LocalDate date = view.getDatePicker().getValue();
        String time = view.getTimeBox().getValue();
        String reason = view.getReasonArea().getText();
        AppointmentStatus status = view.getStatusBox().getValue();

        if (client == null || client.isBlank()
                || email == null || email.isBlank()
                || date == null
                || time == null || time.isBlank()
                || reason == null || reason.isBlank()
                || status == null) {
            view.showError("Tous les champs sont obligatoires.");
            return;
        }
        if (!email.contains("@")) {
            view.showError("Email invalide (doit contenir @).");
            return;
        }

        Appointment toCreate = new Appointment(
                client.trim(),
                email.trim(),
                date,
                time,
                reason.trim(),
                status
        );

        view.setBusy(true);

        Task<Appointment> task = new Task<>() {
            @Override
            protected Appointment call() {
                return api.createAppointment(token, toCreate);
            }
        };

        task.setOnSucceeded(e -> {
            view.setBusy(false);
            Appointment created = task.getValue();
            view.getAppointments().add(created); // la FilteredList réagit automatiquement
            view.clearForm();
            applyFilters(); // au cas où ton filtre cache l’item
            view.showInfo("Rendez-vous ajouté.");
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
