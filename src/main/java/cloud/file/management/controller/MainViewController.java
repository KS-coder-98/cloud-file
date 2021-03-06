package cloud.file.management.controller;

import cloud.file.management.common.DeleteFiles;
import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.HandlerResources;
import cloud.file.management.model.LambdaExpression;
import cloud.file.management.model.User;
import cloud.file.management.model.communication.SendListNamesFiles;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.*;

public class MainViewController implements Initializable {

    private static Boolean controlFlag = true;
    private static FileTime lastUpdate;
    private static TreeItem<String> choseFile;
    private static String choseUser;
    private static List<String> list;
    private static List<String> filesList;

    private Path path;

    public static List<String> getList() {
        return list;
    }

    public static void setList(List<String> list) {
        MainViewController.list = list;
    }

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

    public void setLabelLog(String s){
        labelLog.setText(s);
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
        SendListNamesFiles.send();

        filesList = HandlerResources.listNameFile(User.getPath());


        Thread refreshListUser = new Thread(this::refreshUserList);
        refreshListUser.setDaemon(true);
        refreshListUser.start();

        Thread refreshLabelLog = new Thread(this::refreshStatusLog);
        refreshLabelLog.setDaemon(true);
        refreshLabelLog.start();


        treeViewListUser.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setLabelWhereFileSend(newValue);
                    choseUser = newValue;
                });

        treeViewListFiles.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    choseFile = newValue;
                    setLabelWhatFileSend(newValue.getValue());
                });

        buttonSend.setOnAction(actionEvent -> {
            try {
                Objects.requireNonNull(choseFile);
                Objects.requireNonNull(choseUser);
                Path absolutePath = Path.of(choseFile.getParent().getValue() + "\\" + choseFile.getValue());
                String relativePath = absolutePath.toString().substring(User.getPath().toString().length() + 1);
                //todo na razie przycisk wyłaczony
                FileMessage msg = new FileMessage(User.getLogin(), relativePath, choseUser, new Random().nextLong());
                User.getEchoClient().getSend().sendMessage(msg);
            } catch (NullPointerException e) {
                System.err.println("nie ustawiono pola file lub user w mainControler");
            }
        });
    }

    // loads some strings into the tree in the application UI.
    public void loadTreeItems() {
        try {
            TreeItem<String> root = HandlerResources.listDirectory(path);
            treeViewListFiles.setRoot(root);
            root.setExpanded(true);
        } catch (FileNotFoundException ex) {
            TreeItem<String> root = new TreeItem<>("INVALID PATH !!!");
            treeViewListFiles.setRoot(root);
        }
    }

    private void refreshTreeViewDirectory() {
        while (controlFlag) {
            Platform.runLater(() -> {

                Collections.sort(filesList);
                List<String> tempListFile = HandlerResources.listNameFile(User.getPath());
                Collections.sort(tempListFile);
                LambdaExpression.consumer(tempListFile, System.err::println);
                filesList.removeAll(tempListFile);
                if (filesList.size()>0){
                    LambdaExpression.consumer(filesList, System.err::println);
                    Message msg = new DeleteFiles(User.getLogin(), filesList);
                    User.getEchoClient().addMessage(msg);
                }
                else if (!lastUpdate.toString().equals(HandlerResources.date(path).toString())) {
                    setLastTime();
                    loadTreeItems();
                    SendListNamesFiles.send();
                    System.out.println("powinien odswieżyc");
                }
                filesList = HandlerResources.listNameFile(User.getPath());

            });
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshUserList() {
        while (controlFlag) {
            Platform.runLater(() -> {
                if (!Objects.isNull(list)) {
                    treeViewListUser.getItems().clear();
                    for (String nick : list) {
                        treeViewListUser.getItems().add(nick);
                    }
                } else
                    System.err.println("error list is null in MainViewController refreshUserList");
            });
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
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

    private void refreshStatusLog(){
        while (controlFlag){
            Platform.runLater(()->{
                setLabelLog(User.getEventName());
            });
            try {
                Thread.sleep(500);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
