package tp_javafx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import tp_javafx.model.Appointment;

import java.time.format.DateTimeFormatter;

public class AppView {

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private final TableView<Appointment> table = new TableView<>();
    private final Button logoutButton = new Button("Déconnexion");

    public Scene createScene(Runnable onLogoutClicked) {
        Label title = new Label("Gestion des rendez-vous");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        logoutButton.setOnAction(e -> onLogoutClicked.run());

        table.setItems(appointments);

        TableColumn<Appointment, String> clientCol = new TableColumn<>("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        TableColumn<Appointment, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cell -> {
            var date = cell.getValue().getDate();
            var formatted = date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });

        TableColumn<Appointment, String> timeCol = new TableColumn<>("Heure");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getStatus() == null ? "" : cell.getValue().getStatus().name()
                )
        );

        table.getColumns().setAll(clientCol, emailCol, dateCol, timeCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox root = new VBox(12, title, logoutButton, table);
        root.setPadding(new Insets(20));

        return new Scene(root, 900, 560);
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public TableView<Appointment> getTable() {
        return table;
    }
}
