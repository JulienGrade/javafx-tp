package tp_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp_javafx.api.AppointmentApi;
import tp_javafx.api.FakeAppointmentApi;
import tp_javafx.controller.AppController;
import tp_javafx.controller.LoginController;
import tp_javafx.view.AppView;
import tp_javafx.view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp_javafx.Main");
        stage.setTitle("TP RDV - Étape 6 (Load RDV via API)");

        AppointmentApi api = new FakeAppointmentApi();

        // Login
        LoginView loginView = new LoginView();
        Scene loginScene = loginView.createScene();

        stage.setScene(loginScene);
        stage.show();

        LoginController loginController = new LoginController(loginView, api, token -> {
            // App
            AppView appView = new AppView();
            Scene appScene = appView.createScene();

            AppController appController = new AppController(
                    appView,
                    api,
                    token,
                    () -> stage.setScene(loginScene),
                    stage::close
            );

            appController.init();
            stage.setScene(appScene);
        });

        loginController.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
