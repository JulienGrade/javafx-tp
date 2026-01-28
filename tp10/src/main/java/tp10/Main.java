package tp10;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        System.out.println(">>> RUNNING: tp10.Main");
        stage.setTitle("JavaFX - MenuBar (TP10)");

        stage.setScene(createLoginScene(stage));
        stage.show();
    }

    // -----------------------------
    // Scene 1 : Login
    // -----------------------------
    private Scene createLoginScene(Stage stage) {
        Label title = new Label("Connexion");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(220);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(220);

        Button loginButton = new Button("Se connecter");
        loginButton.setDefaultButton(true);

        VBox root = new VBox(12, title, usernameField, passwordField, loginButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isBlank() || password.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
                return;
            }

            if (username.equals("admin") && password.equals("admin")) {
                stage.setScene(createAppScene(stage));
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Identifiants invalides.");
            }
        });

        return new Scene(root, 380, 260);
    }

    // -----------------------------
    // Scene 2 : Application
    // -----------------------------
    private Scene createAppScene(Stage stage) {

        // --- MenuBar (TP10) ---
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("Fichier");
        MenuItem logoutItem = new MenuItem("Déconnexion");
        MenuItem exitItem = new MenuItem("Quitter");

        fileMenu.getItems().addAll(logoutItem, exitItem);
        menuBar.getMenus().add(fileMenu);

        // Actions du menu
        logoutItem.setOnAction(e -> stage.setScene(createLoginScene(stage)));
        exitItem.setOnAction(e -> stage.close());

        // --- UI principale ---
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

        TableView<User> table = new TableView<>();
        table.setItems(users);

        TableColumn<User, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Âge");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        table.getColumns().addAll(nameCol, emailCol, ageCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

            users.add(new User(name.trim(), email.trim(), age));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté.");

            nameField.clear();
            emailField.clear();
            ageField.clear();
            nameField.requestFocus();
        });

        // Layout : MenuBar en haut, puis contenu
        VBox root = new VBox(10, menuBar, title, form, table);
        root.setPadding(new Insets(12));

        Scene scene = new Scene(root, 780, 580);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        return scene;
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
