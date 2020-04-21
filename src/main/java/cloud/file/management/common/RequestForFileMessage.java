package cloud.file.management.common;

import cloud.file.management.model.User;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;

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
            //todo generate id
            System.out.println(relativePath);
            //generate id
            long id = new Random().nextLong();
            Message msg = new FileMessage(login, relativePath, login, id);
            User.getEchoClient().addMessage(msg);
            //todo send file

            System.err.println(User.getEchoClient().getMsgList().toString());
        }
    }
}
