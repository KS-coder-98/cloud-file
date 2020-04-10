package cloud.file.management.app;

import cloud.file.management.controller.MainViewController;
import cloud.file.management.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Path;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        User user = new User("Krzysiek", Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"));


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        VBox mainView = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setPath(Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"));

        mainViewController.setLabelLogin("Login: "+user.getLogin());
        mainViewController.setLabelPath("Path "+ user.getPath());

//        mainViewController.refreshTreeViewDirectory();
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.setTitle("CLOUD FILE MANAGEMENT");
        stage.show();
    }
}
