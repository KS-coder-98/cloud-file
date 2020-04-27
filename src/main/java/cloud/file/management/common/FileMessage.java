package cloud.file.management.common;

import cloud.file.management.model.User;

public class FileMessage extends Message {
    public FileMessage() {
        super();
    }

    public FileMessage(String login, String path, String pathDst, long id) {
        super(login, path, pathDst, id);
    }

    @Override
    public void preprocess() {
        System.out.println("preproces fileMessage");
        System.out.println(getLogin() + " " + getPathDst() + " " + getPath() + " ###################################");
        //todo save file
        User.getEchoClient().getMsgListReceive().add(this);

    }
}
