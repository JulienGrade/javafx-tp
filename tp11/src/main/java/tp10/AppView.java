package tp11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AppView {

    public Scene createScene(UserService userService, Runnable onLogout, Runnable onExit) {
        // --- MenuBar ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Fichier");
        MenuItem logoutItem = new MenuItem("Déconnexion");
        MenuItem exitItem = new MenuItem("Quitter");
        fileMenu.getItems().addAll(logoutItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        logoutItem.setOnAction(e -> onLogout.run());
        exitItem.setOnAction(e -> onExit.run());

        // --- UI ---
        Label title = new Label("Gestion des utilisateurs");
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
        form.setPadding(new Insets(10));

        form.add(new Label("Nom :"), 0, 0);
        form.add(nameField, 1, 0);

        form.add(new Label("Email :"), 0, 1);
        form.add(emailField, 1, 1);

        form.add(new Label("Âge :"), 0, 2);
        form.add(ageField, 1, 2);

        form.add(addButton, 1, 3);

        // --- TableView ---
        TableView<User> table = new TableView<>();
        table.setItems(userService.getUsers());

        TableColumn<User, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.getColumns().addAll(nameCol, emailCol, ageCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- Event ---
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

            userService.addUser(new User(name.trim(), email.trim(), age));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté.");

            nameField.clear();
            emailField.clear();
            ageField.clear();
            nameField.requestFocus();
        });

        VBox root = new VBox(10, menuBar, title, form, table);
        root.setPadding(new Insets(12));

        return new Scene(root, 780, 580);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
