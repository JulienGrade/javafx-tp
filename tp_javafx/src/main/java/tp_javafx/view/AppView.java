package tp_javafx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tp_javafx.model.Appointment;
import tp_javafx.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppView {

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    // --- Filters (étape 8/9)
    private final TextField searchField = new TextField();
    private final ComboBox<String> statusFilterBox = new ComboBox<>();

    // --- Form fields
    private final TextField clientField = new TextField();
    private final TextField emailField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final ComboBox<String> timeBox = new ComboBox<>();
    private final TextArea reasonArea = new TextArea();
    private final ComboBox<AppointmentStatus> statusBox = new ComboBox<>();
    private final Button addButton = new Button("Ajouter");

    // --- UI
    private final TableView<Appointment> table = new TableView<>();
    private final ProgressIndicator loading = new ProgressIndicator();

    // --- Status bar (étape 9)
    private final Label statusBar = new Label();

    // --- Menu
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

        // --------- Filters bar
        searchField.setPromptText("Rechercher (client, email, motif)...");
        searchField.setPrefWidth(320);

        statusFilterBox.setItems(FXCollections.observableArrayList("Tous", "PREVU", "ANNULE", "TERMINE"));
        statusFilterBox.setValue("Tous");

        HBox filtersBar = new HBox(10,
                new Label("Recherche :"), searchField,
                new Label("Statut :"), statusFilterBox
        );
        filtersBar.setAlignment(Pos.CENTER_LEFT);
        filtersBar.setPadding(new Insets(8));
        filtersBar.setStyle("-fx-background-color: rgba(0,0,0,0.03); -fx-background-radius: 10;");

        // --------- Form setup
        clientField.setPromptText("Nom du client");
        emailField.setPromptText("Email");
        datePicker.setPromptText("Date");

        timeBox.setItems(FXCollections.observableArrayList(
                "09:00","09:30","10:00","10:30","11:00","11:30",
                "14:00","14:30","15:00","15:30","16:00","16:30"
        ));
        timeBox.setPromptText("Heure");

        reasonArea.setPromptText("Motif (Ctrl+Entrée pour ajouter)");
        reasonArea.setPrefRowCount(3);
        reasonArea.setWrapText(true);

        statusBox.setItems(FXCollections.observableArrayList(AppointmentStatus.values()));
        statusBox.setValue(AppointmentStatus.PREVU);

        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setDefaultButton(false); // on garde Ctrl+Enter dans le TextArea

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER_LEFT);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-background-color: rgba(0,0,0,0.03); -fx-background-radius: 10;");

        form.add(new Label("Client :"), 0, 0);
        form.add(clientField, 1, 0);

        form.add(new Label("Email :"), 0, 1);
        form.add(emailField, 1, 1);

        form.add(new Label("Date :"), 0, 2);
        form.add(datePicker, 1, 2);

        form.add(new Label("Heure :"), 0, 3);
        form.add(timeBox, 1, 3);

        form.add(new Label("Statut :"), 0, 4);
        form.add(statusBox, 1, 4);

        form.add(new Label("Motif :"), 0, 5);
        form.add(reasonArea, 1, 5);

        form.add(addButton, 1, 6);

        // --------- Table
        TableColumn<Appointment, String> clientCol = new TableColumn<>("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        TableColumn<Appointment, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cell -> {
            LocalDate date = cell.getValue().getDate();
            String formatted = date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
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

        // Status bar
        statusBar.setText("");
        statusBar.setStyle("-fx-text-fill: rgba(0,0,0,0.65);");
        statusBar.setPadding(new Insets(6, 2, 0, 2));

        VBox root = new VBox(12, menuBar, title, loading, filtersBar, form, table, statusBar);
        root.setPadding(new Insets(20));

        return new Scene(root, 980, 780);
    }

    // --- Getters pour controller ---
    public ObservableList<Appointment> getAppointments() { return appointments; }
    public TableView<Appointment> getTable() { return table; }

    public TextField getSearchField() { return searchField; }
    public ComboBox<String> getStatusFilterBox() { return statusFilterBox; }

    public TextField getClientField() { return clientField; }
    public TextField getEmailField() { return emailField; }
    public DatePicker getDatePicker() { return datePicker; }
    public ComboBox<String> getTimeBox() { return timeBox; }
    public TextArea getReasonArea() { return reasonArea; }
    public ComboBox<AppointmentStatus> getStatusBox() { return statusBox; }
    public Button getAddButton() { return addButton; }

    public MenuItem getLogoutItem() { return logoutItem; }
    public MenuItem getExitItem() { return exitItem; }

    public void setBusy(boolean busy) {
        searchField.setDisable(busy);
        statusFilterBox.setDisable(busy);

        clientField.setDisable(busy);
        emailField.setDisable(busy);
        datePicker.setDisable(busy);
        timeBox.setDisable(busy);
        reasonArea.setDisable(busy);
        statusBox.setDisable(busy);
        addButton.setDisable(busy);
        table.setDisable(busy);

        loading.setVisible(busy);
    }

    public void clearForm() {
        clientField.clear();
        emailField.clear();
        datePicker.setValue(null);
        timeBox.setValue(null);
        reasonArea.clear();
        statusBox.setValue(AppointmentStatus.PREVU);
        clientField.requestFocus();
    }

    public void setStatusMessage(String msg) {
        statusBar.setText(msg == null ? "" : msg);
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
