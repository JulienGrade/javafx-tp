package tp_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp_javafx.api.AppointmentApi;
import tp_javafx.api.FakeAppointmentApi;
import tp_javafx.controller.LoginController;
import tp_javafx.view.AppView;
import tp_javafx.view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp_javafx.Main");
        stage.setTitle("TP RDV - Étape 5 (LoginController + API)");

        // API simulée
        AppointmentApi api = new FakeAppointmentApi();

        // Views
        LoginView loginView = new LoginView();
        Scene loginScene = loginView.createScene();

        AppView appView = new AppView();
        final Scene[] appScene = new Scene[1];

        // Affiche login
        stage.setScene(loginScene);
        stage.show();


        LoginController loginController = new LoginController(loginView, api, token -> {
            appScene[0] = appView.createScene(() -> stage.setScene(loginScene));
            stage.setScene(appScene[0]);
        });

        loginController.init();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
