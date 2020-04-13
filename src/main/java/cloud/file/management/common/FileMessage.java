package cloud.file.management.common;

public class FileMessage extends Message {
    public FileMessage() {
        super();
    }

    public FileMessage(String login, String path, String pathDst, byte[] fileInByte) {
        super(login, path, pathDst, fileInByte);
    }

    @Override
    public void preprocess() {
        System.out.println(getLogin() + " " + getPathDst());
    }
}
