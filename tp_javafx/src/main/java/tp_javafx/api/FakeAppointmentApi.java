package tp_javafx.api;

import tp_javafx.model.Appointment;
import tp_javafx.model.AppointmentStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeAppointmentApi implements AppointmentApi {

    private final List<Appointment> storage = new ArrayList<>();
    private String validToken;

    public FakeAppointmentApi() {
        seed();
    }

    @Override
    public String login(String username, String password) {
        simulateNetworkDelay();

        if ("agent".equals(username) && "support".equals(password)) {
            validToken = UUID.randomUUID().toString();
            return validToken;
        }
        throw new IllegalArgumentException("Identifiants invalides.");
    }

    @Override
    public List<Appointment> fetchAppointments(String token) {
        simulateNetworkDelay();
        checkToken(token);

        return new ArrayList<>(storage);
    }

    @Override
    public Appointment createAppointment(String token, Appointment appointment) {
        simulateNetworkDelay();
        checkToken(token);

        storage.add(appointment);
        return appointment;
    }

    private void checkToken(String token) {
        if (token == null || validToken == null || !token.equals(validToken)) {
            throw new SecurityException("Non autorisé (token invalide).");
        }
    }

    private void simulateNetworkDelay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Appel interrompu", e);
        }
    }

    private void seed() {
        storage.add(new Appointment(
                "Nadia B.",
                "nadia@acme.local",
                LocalDate.now().plusDays(1),
                "09:00",
                "Incident VPN",
                AppointmentStatus.PREVU
        ));

        storage.add(new Appointment(
                "Paul M.",
                "paul@acme.local",
                LocalDate.now().plusDays(2),
                "14:30",
                "Demande accès JIRA",
                AppointmentStatus.PREVU
        ));

        storage.add(new Appointment(
                "Lea S.",
                "lea@acme.local",
                LocalDate.now().minusDays(1),
                "11:00",
                "PC lent - diagnostic",
                AppointmentStatus.TERMINE
        ));
    }
}
