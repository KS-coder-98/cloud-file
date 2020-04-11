package cloud.file.management.common;

import java.io.Serializable;

public class Message implements Serializable {
    private String login;
    private String path;
    private byte[] fileInByte;

    public Message(String login, String path, byte[] fileInByte) {
        this.login = login;
        this.path = path;
        this.fileInByte = fileInByte;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getFileInByte() {
        return fileInByte;
    }

    public void setFileInByte(byte[] fileInByte) {
        this.fileInByte = fileInByte;
    }
}
