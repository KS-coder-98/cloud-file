package cloud.file.management.common;

import java.io.Serializable;

public class Message implements Serializable {
    private TypeMessage typeMessage;
    private String login;
    private String path;
    private byte[] fileInByte;

    public Message(TypeMessage typeMessage, String login, String path, byte[] fileInByte) {
        this.typeMessage = typeMessage;
        this.login = login;
        this.path = path;
        this.fileInByte = fileInByte;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
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
