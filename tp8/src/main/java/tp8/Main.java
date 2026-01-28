package tp8;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp8.Main");

        // --- Formulaire ---
        Label title = new Label("Création d'utilisateur");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();

        nameField.setPromptText("Nom");
        emailField.setPromptText("Email");
        ageField.setPromptText("Âge");

        Button addButton = new Button("Ajouter");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER_LEFT);
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(20));

        form.add(title, 0, 0, 2, 1);
        form.add(new Label("Nom :"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Email :"), 0, 2);
        form.add(emailField, 1, 2);
        form.add(new Label("Âge :"), 0, 3);
        form.add(ageField, 1, 3);
        form.add(addButton, 1, 4);

        // --- Champ de recherche ---
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher (nom ou email)");

        // --- FilteredList ---
        FilteredList<User> filteredUsers = new FilteredList<>(users, u -> true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String query = newVal == null ? "" : newVal.trim().toLowerCase();

            filteredUsers.setPredicate(user -> {
                if (query.isEmpty()) return true;
                return user.getName().toLowerCase().contains(query)
                        || user.getEmail().toLowerCase().contains(query);
            });
        });
        // --- TableView ---
        TableView<User> table = new TableView<>();
        table.setItems(filteredUsers);

        TableColumn<User, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.getColumns().addAll(nameCol, emailCol, ageCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- Event ajout ---
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String ageText = ageField.getText();

            if (name.isBlank() || email.isBlank() || ageText.isBlank()) {
                showAlert("Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "L'âge doit être un nombre.");
                return;
            }

            users.add(new User(name.trim(), email.trim(), age));
            nameField.clear();
            emailField.clear();
            ageField.clear();
        });

        VBox root = new VBox(10, form, searchField, table);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 720, 560);
        stage.setTitle("JavaFX - Filtrage dynamique (TP8)");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
