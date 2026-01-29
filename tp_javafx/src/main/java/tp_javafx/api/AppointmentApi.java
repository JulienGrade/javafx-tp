package tp_javafx.api;

import tp_javafx.model.Appointment;

import java.util.List;

public interface AppointmentApi {

    String login(String username, String password);

    List<Appointment> fetchAppointments(String token);

    Appointment createAppointment(String token, Appointment appointment);
}
