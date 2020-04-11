package cloud.file.management.app;

import cloud.file.management.controller.MainViewController;
import cloud.file.management.model.User;
import cloud.file.management.model.communication.EchoClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        client.startConnection("127.0.0.1", 5555);
        new User("Krzysiek", Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"), client);

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        VBox mainView = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setPath(Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"));

        mainViewController.setLabelLogin("Login: "+User.getLogin());
        mainViewController.setLabelPath("Path "+ User.getPath());

        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.setTitle("CLOUD FILE MANAGEMENT");
        stage.show();
    }
}
