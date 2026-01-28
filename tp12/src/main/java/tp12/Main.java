package tp12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp12.api.FakeUserApi;
import tp12.api.UserApi;
import tp12.controller.AppController;
import tp12.controller.LoginController;
import tp12.view.AppView;
import tp12.view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp12.Main");
        stage.setTitle("JavaFX - MVC + API simulée (TP12)");

        UserApi api = new FakeUserApi();

        // Login
        LoginView loginView = new LoginView();
        Scene loginScene = loginView.createScene();

        // On démarre sur login
        stage.setScene(loginScene);
        stage.show();

        // Controller login -> en cas de succès : on crée l'écran App avec token
        LoginController loginController = new LoginController(loginView, api, token -> {
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
