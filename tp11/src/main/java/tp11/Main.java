package tp11;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println(">>> RUNNING: tp11.Main");
        stage.setTitle("JavaFX - DI (TP11)");

        // 1) Instanciation unique du service (source de données)
        UserService userService = new UserService();

        // 2) Instanciation des vues
        LoginView loginView = new LoginView();
        AppView appView = new AppView();

        // 3) Navigation via callbacks
        final Scene[] loginScene = new Scene[1];
        final Scene[] appScene = new Scene[1];

        loginScene[0] = loginView.createScene(() -> stage.setScene(appScene[0]));

        appScene[0] = appView.createScene(
                userService,
                () -> stage.setScene(loginScene[0]),
                stage::close
        );

        // écran initial
        stage.setScene(loginScene[0]);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
