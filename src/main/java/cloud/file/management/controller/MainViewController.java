package cloud.file.management.controller;

import cloud.file.management.common.Message;
import cloud.file.management.common.TypeMessage;
import cloud.file.management.model.FileAPI;
import cloud.file.management.model.HandlerResources;
import cloud.file.management.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static Boolean controlFlag = true;
    private static FileTime lastUpdate;
    private static TreeItem<String> choseFile;
    private Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

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

    @FXML
    private Label labelWhatFileSend;

    @FXML
    private Label labelWhereFileSend;

    public TreeView<String> getTreeViewListFiles() {
        return treeViewListFiles;
    }

    public ListView<String> getTreeViewListUser() {
        return treeViewListUser;
    }

    public void setLabelLogin(String s) {
        labelLogin.setText(s);
    }

    public void setLabelPath(String s) {
        labelPath.setText(s);
    }

    public void setLabelVersion(String s) {
        labelVersion.setText(s);
    }

    public void setLabelStatusConnection(String s) {
        labelStatusConnection.setText(s);
    }

    public void setLabelWhatFileSend(String s) {
        labelWhatFileSend.setText(s);
    }

    public void setLabelWhereFileSend(String s) {
        labelWhereFileSend.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Thread refreshDirectory = new Thread(this::refreshTreeViewDirectory);
        refreshDirectory.setDaemon(true);
        path = User.getPath();
        setLastTime();
        loadTreeItems();
        refreshDirectory.start();

        treeViewListFiles.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->{
                        choseFile = newValue;
                        setLabelWhatFileSend(newValue.getValue());
                });

        buttonSend.setOnAction(actionEvent -> {
            Path tempPath = Path.of(choseFile.getParent().getValue()+"\\"+choseFile.getValue());
            byte[] file = FileAPI.getStreamFile(tempPath);
            Message msg = new Message(TypeMessage.TRANSMISSION_FILE ,User.getLogin(), tempPath.toString(), file);
            User.getEchoClient().sendMessage(msg);
            System.out.println(tempPath);
        });
    }

    // loads some strings into the tree in the application UI.
    public void loadTreeItems(){
        try{
            TreeItem<String> root = HandlerResources.listDirectory(path);
            treeViewListFiles.setRoot(root);
            root.setExpanded(true);
            System.out.println("pierwszy if");
        }catch (FileNotFoundException ex) {
            TreeItem<String> root = new TreeItem<>("INVALID PATH !!!");
            treeViewListFiles.setRoot(root);
        }
    }

    private void refreshTreeViewDirectory(){
        while (controlFlag){
            Platform.runLater(()->{
                if ( !lastUpdate.toString().equals(HandlerResources.date(path).toString()) ){
                    setLastTime();
                    loadTreeItems();
                }
            });
            try {
                Thread.sleep(60000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private FileTime setLastTime() {
        try {
            lastUpdate = HandlerResources.date(path);
            return lastUpdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
