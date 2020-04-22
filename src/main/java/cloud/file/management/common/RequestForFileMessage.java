package cloud.file.management.common;

import cloud.file.management.model.User;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list) {
        super(login, list);
    }


    @Override
    public void preprocess() {
        System.out.println("preprocess requestForFileMessage "+ getList());
        for (var relativePath : getList()) {
            Path absolutePath = Path.of(User.getPath() + "\\" + relativePath);
            String login = User.getLogin();
            //todo generate id
            System.out.println(relativePath);
            //generate id
            long id = new Random().nextLong();
            Message msg = new FileMessage(login, relativePath, login, id);
            System.out.println(msg.toString());
            User.getEchoClient().addMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //todo send file
            if (Objects.isNull(User.getEchoClient().getSendFile()))
                System.out.println("sciezka jest nulem");
            User.getEchoClient().getSendFile().sendFile(absolutePath, id);
            System.out.println("odpali send file z "+absolutePath+ " "+id);
            System.err.println(User.getEchoClient().getMsgList().toString());
        }
    }
}
