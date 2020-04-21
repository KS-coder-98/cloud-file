package cloud.file.management.common;

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
        System.out.println(getLogin() + " " + getPathDst()+ " " + getPath());
        //todo save file
//        FileAPI.saveFile(super.getFileInByte(), Path.of(getPath()));
    }
}
