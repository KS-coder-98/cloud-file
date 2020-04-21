package cloud.file.management.common;


import cloud.file.management.controller.MainViewController;

public class ListUserMessage extends Message {
    public ListUserMessage(String login, String path, long id) {
        super(login, path, null, id);
    }

    public ListUserMessage() {
        //nothing to do
    }


    @Override
    public void preprocess() {
        System.out.println("preprocess ListUserMessage " + this.getList().toString());
        MainViewController.setList(this.getList());
    }
}
