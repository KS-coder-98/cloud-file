package cloud.file.management.common;

import cloud.file.management.model.User;

import java.util.List;
import java.util.Random;

public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list) {
        super(login, list);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess requestForFileMessage "+ getList());
        for (var relativePath : getList()) {
//            Path absolutePath = Path.of(User.getPath() + "\\" + relativePath);

            System.out.println("sciezka wzgledna: " +relativePath);
            //generate id
            long id = new Random().nextLong();
            Message msg = new FileMessage(User.getLogin(), relativePath, getLogin(), id);
            System.out.println(msg.toString());
            User.getEchoClient().addMessage(msg);
        }
    }
}
