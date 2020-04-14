package cloud.file.management.common;

import java.util.List;

public class ListLocalFileMessage extends Message{

    public ListLocalFileMessage(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        //nothing to do
    }
}
