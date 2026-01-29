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
    private final ProgressIndicator loading = new ProgressIndicator();

    // Menu items (branchés par le controller)
    private final MenuItem logoutItem = new MenuItem("Déconnexion");
    private final MenuItem exitItem = new MenuItem("Quitter");

    public Scene createScene() {
        // MenuBar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Fichier");
        fileMenu.getItems().addAll(logoutItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        Label title = new Label("Gestion des rendez-vous");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Loader
        loading.setVisible(false);
        loading.setMaxSize(22, 22);

        // Table
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

        VBox root = new VBox(12, menuBar, title, loading, table);
        root.setPadding(new Insets(20));

        return new Scene(root, 900, 560);
    }

    // --- API pour controller ---
    public ObservableList<Appointment> getAppointments() { return appointments; }
    public TableView<Appointment> getTable() { return table; }

    public MenuItem getLogoutItem() { return logoutItem; }
    public MenuItem getExitItem() { return exitItem; }

    public void setBusy(boolean busy) {
        table.setDisable(busy);
        loading.setVisible(busy);
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
