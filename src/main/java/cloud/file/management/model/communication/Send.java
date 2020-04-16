package cloud.file.management.model.communication;

import cloud.file.management.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            if ( !msgList.isEmpty() ){
                var msg = msgList.get(0);
                sendMessage(msg);
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
