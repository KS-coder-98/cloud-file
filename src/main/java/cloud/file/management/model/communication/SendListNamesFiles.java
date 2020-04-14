package cloud.file.management.model.communication;

import cloud.file.management.common.ListLocalFileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.HandlerResources;
import cloud.file.management.model.User;

public abstract class SendListNamesFiles {
    public static void send(){
        var listFile = HandlerResources.listNameFile(User.getPath());
        Message msg = new ListLocalFileMessage(User.getLogin(), listFile);
        User.getEchoClient().addMessage(msg);
    }
}
