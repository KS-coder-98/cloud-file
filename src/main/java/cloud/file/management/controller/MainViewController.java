package cloud.file.management.controller;

import cloud.file.management.model.HandlerResources;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class MainViewController {

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelPath;

    @FXML
    private Label labelVersion;

    @FXML
    private Label labelStatusConnection;

    @FXML
    private TreeView<String> treeViewListFiles;

    @FXML
    private ListView<String> treeViewListUser;

    @FXML
    private Label labelLog;

    @FXML
    private Button buttonSend;

    // the initialize method is automatically invoked by the FXMLLoader - it's magic
    public void initialize() {
        loadTreeItems(Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"));
    }

    // loads some strings into the tree in the application UI.
    public void loadTreeItems(Path path){
        try{
            var root = HandlerResources.listDirectory(path);
            treeViewListFiles.setRoot(root);
        }catch (FileNotFoundException ex){
            TreeItem<String> root = new TreeItem<>("INVALID PATH !!!");
            treeViewListFiles.setRoot(root);
        }
    }

    public void setLabelLogin(String s) {
        labelLogin.setText(s);
    }
}
