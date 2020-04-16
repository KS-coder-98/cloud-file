package cloud.file.management.common;

public class JoinMessage extends Message {
    public JoinMessage(String login) {
        super(login);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess JoinMessage");
        //nothing to do
    }
}
