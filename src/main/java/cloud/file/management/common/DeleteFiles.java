package cloud.file.management.common;

import java.util.List;

public class DeleteFiles extends Message {
    public DeleteFiles(String login, List<String> list){
        super(login, list);
    }

    @Override
    public void preprocess() {
        //nothing to do
    }
}
