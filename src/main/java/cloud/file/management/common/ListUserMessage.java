package cloud.file.management.common;


import cloud.file.management.controller.MainViewController;

public class ListUserMessage extends Message {
    public ListUserMessage(String login, String path, byte[] fileInByte) {
        super(login, path, null, fileInByte);
    }

    public ListUserMessage() {
        //nothing to do
    }


    @Override
    public void preprocess() {
        System.out.println("receive msg with list user " + this.getList().toString());
        MainViewController.setList(this.getList());
    }
}
