package cloud.file.management.common;

import java.io.Serializable;
import java.util.List;

public abstract class Message implements Serializable {
    private String login;
    private String path;
    private String pathDst;
    private byte[] fileInByte;
    private List<String> list;

    public Message(String login, String path, String pathDst, byte[] fileInByte) {
        this.login = login;
        this.path = path;
        this.pathDst = pathDst;
        this.fileInByte = fileInByte;
    }

    public Message(String login, List<String> list){
        this.login = login;
        this.list = list;
    }

    public Message(String login) {
        this.login = login;
        this.path = null;
        this.fileInByte = null;
    }

    public Message() {

    }

    public String getPathDst() {
        return pathDst;
    }

    public void setPathDst(String pathDst) {
        this.pathDst = pathDst;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
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

    public abstract void preprocess();
}