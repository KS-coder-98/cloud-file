package cloud.file.management.model.communication;

import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

public class Send extends Thread {
    private ObjectOutputStream out;
    List<Message> msgList;

    public Send(ObjectOutputStream out, List<Message> msgList) {
        this.out = out;
        this.msgList = msgList;
    }

    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            if ( msg instanceof FileMessage){
                Thread.sleep(1000);
                User.getEchoClient().getSendFile().sendFile(Path.of(msg.getPath()), msg.getId());
                System.err.println("wyslano file message");

            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            if ( !msgList.isEmpty() ){
                var msg = msgList.get(0);
                sendMessage(msg);
                if ( msg instanceof FileMessage )
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                msgList.remove(0);
            }else{
                try {
                    Thread.sleep(6000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }

        }
    }
}
