package cloud.file.management.common;

import cloud.file.management.model.FileAPI;
import cloud.file.management.model.User;

import java.nio.file.Path;
import java.util.List;

public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list) {
        super(login, list);
    }


    @Override
    public void preprocess() {
        System.out.println("preprocess requestForFileMessage");
        for (var relativePath : getList()) {
            Path absolutePath = Path.of(User.getPath() + "\\" + relativePath);
            String login = User.getLogin();
            Message msg = new FileMessage(login, relativePath, login, FileAPI.getStreamFile(absolutePath));
            User.getEchoClient().addMessage(msg);
            System.err.println(User.getEchoClient().getMsgList().toString());
        }
    }
}
