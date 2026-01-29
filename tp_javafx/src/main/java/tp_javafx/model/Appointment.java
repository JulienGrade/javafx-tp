package tp_javafx.model;

import java.time.LocalDate;

public class Appointment {

    private final String clientName;
    private final String email;
    private final LocalDate date;
    private final String time; // ex: "09:00"
    private final String reason;
    private final AppointmentStatus status;

    public Appointment(String clientName, String email, LocalDate date, String time, String reason, AppointmentStatus status) {
        this.clientName = clientName;
        this.email = email;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.status = status;
    }

    public String getClientName() { return clientName; }
    public String getEmail() { return email; }
    public LocalDate getDate() { return date; }
    public String getTime() { return time; }
    public String getReason() { return reason; }
    public AppointmentStatus getStatus() { return status; }
}
