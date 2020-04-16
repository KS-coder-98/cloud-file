package cloud.file.management.common;

import cloud.file.management.model.UserTask;

import java.util.List;

public class ListLocalFileMessage extends Message{

    public ListLocalFileMessage(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        System.out.println("preprocess ListLocalFileMessage");
        UserTask.makeRequestForFile(getList(), getLogin());
    }
}
