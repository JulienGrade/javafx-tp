package tp7;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tp7.model.User;

public class Main extends Application {

    // ObservableList = mise à jour automatique du tableau
    private final ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp7.Main");

        // --- Formulaire (GridPane) ---
        Label title = new Label("Création d'utilisateur");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nom :");
        Label emailLabel = new Label("Email :");
        Label ageLabel = new Label("Âge :");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();

        Button addButton = new Button("Ajouter");

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER_LEFT);
        form.setHgap(10);
        form.setVgap(12);
        form.setPadding(new Insets(20));

        form.add(title, 0, 0, 2, 1);

        form.add(nameLabel, 0, 1);
        form.add(nameField, 1, 1);

        form.add(emailLabel, 0, 2);
        form.add(emailField, 1, 2);

        form.add(ageLabel, 0, 3);
        form.add(ageField, 1, 3);

        form.add(addButton, 1, 4);

        // --- TableView ---
        TableView<User> table = new TableView<>();
        table.setItems(users);

        TableColumn<User, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(160);

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(220);

        TableColumn<User, Integer> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setPrefWidth(80);

        table.getColumns().addAll(nameCol, emailCol, ageCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox root = new VBox(10, form, table);
        root.setPadding(new Insets(10));

        // --- Event (ajout => tableau se met à jour) ---
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String ageText = ageField.getText();

            if (name.isBlank() || email.isBlank() || ageText.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText.trim());
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'âge doit être un nombre entier.");
                return;
            }

            if (age <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'âge doit être supérieur à 0.");
                return;
            }

            users.add(new User(name.trim(), email.trim(), age));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté.");

            nameField.clear();
            emailField.clear();
            ageField.clear();
            nameField.requestFocus();
        });

        Scene scene = new Scene(root, 700, 520);
        stage.setTitle("JavaFX - TableView (TP7)");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
