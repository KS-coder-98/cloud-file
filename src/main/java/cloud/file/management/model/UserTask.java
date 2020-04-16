package cloud.file.management.model;

import cloud.file.management.common.Message;
import cloud.file.management.common.RequestForFileMessage;

import java.util.List;

public abstract class UserTask {
    public static void makeRequestForFile(List<String> list, String login){
        System.out.println("zrobi zapytanie o pliki");
        List<String> listFileInServer = HandlerResources.listNameFile(User.getPath());
        list.removeAll(listFileInServer);;
        for ( var s : list ){
            System.out.println(s);
        }
        Message msg = new RequestForFileMessage(login, list);
        System.out.println("te pliki chce+ "+list);
        User.getEchoClient().addMessage(msg);
    }
}
