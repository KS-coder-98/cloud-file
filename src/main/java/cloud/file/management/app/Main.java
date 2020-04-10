package cloud.file.management.app;

import cloud.file.management.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Path;

public class Main extends Application {
    public static void main(String[] args) {
        User user = new User("reszka chuj", Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"));
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox mainView = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.setTitle("CLOUD FILE MANAGEMENT");
        stage.show();
    }
}
