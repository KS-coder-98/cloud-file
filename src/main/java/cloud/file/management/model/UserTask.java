package cloud.file.management.model;

import cloud.file.management.common.Message;
import cloud.file.management.common.RequestForFileMessage;

import java.util.List;

public abstract class UserTask {
    public static void makeRequestForFile(List<String> list, String login){
        System.out.println("makeRequestForFile");
        List<String> listFileInServer = HandlerResources.listNameFile(User.getPath());
        list.removeAll(listFileInServer);;
        Message msg = new RequestForFileMessage(login, list);
        User.getEchoClient().addMessage(msg);
    }
}
