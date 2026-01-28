package tp12.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tp12.model.User;

public class AppView {

    private final ObservableList<User> users = FXCollections.observableArrayList();

    private final TextField nameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField ageField = new TextField();
    private final Button addButton = new Button("Ajouter");

    private final ProgressIndicator loading = new ProgressIndicator();
    private final TableView<User> table = new TableView<>();

    private final MenuItem logoutItem = new MenuItem("Déconnexion");
    private final MenuItem exitItem = new MenuItem("Quitter");

    public Scene createScene() {
        // Menu
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Fichier");
        fileMenu.getItems().addAll(logoutItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        Label title = new Label("Gestion des utilisateurs");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Form
        nameField.setPromptText("Nom");
        emailField.setPromptText("Email");
        ageField.setPromptText("Âge");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER_LEFT);
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(10));

        form.add(new Label("Nom :"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(new Label("Email :"), 0, 1);
        form.add(emailField, 1, 1);

        form.add(new Label("Âge :"), 0, 2);
        form.add(ageField, 1, 2);

        form.add(addButton, 1, 3);

        // Loading
        loading.setVisible(false);
        loading.setMaxSize(22, 22);

        // Table
        table.setItems(users);

        TableColumn<User, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.getColumns().addAll(nameCol, emailCol, ageCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox root = new VBox(10, menuBar, title, loading, form, table);
        root.setPadding(new Insets(12));

        return new Scene(root, 800, 600);
    }

    // --- Data / UI API ---
    public ObservableList<User> getUsers() { return users; }

    public TextField getNameField() { return nameField; }
    public TextField getEmailField() { return emailField; }
    public TextField getAgeField() { return ageField; }
    public Button getAddButton() { return addButton; }

    public MenuItem getLogoutItem() { return logoutItem; }
    public MenuItem getExitItem() { return exitItem; }

    public void setBusy(boolean busy) {
        nameField.setDisable(busy);
        emailField.setDisable(busy);
        ageField.setDisable(busy);
        addButton.setDisable(busy);
        loading.setVisible(busy);
    }

    public void clearForm() {
        nameField.clear();
        emailField.clear();
        ageField.clear();
        nameField.requestFocus();
    }

    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
