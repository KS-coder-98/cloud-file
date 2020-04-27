package cloud.file.management.model.communication;

import cloud.file.management.common.FileMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Class implements sending metadata message. This class extends interface Thread. Communication based on ObjectOutStream
 */
public class Send extends Thread {
    private ObjectOutputStream out;
    List<Message> msgList;

    /**
     * Create object with capabilities to sending object
     *
     * @param out object represents stream to sending serialisation object
     * @param msgList list is implemented as queue with message to send.
     *
     */
    public Send(ObjectOutputStream out, List<Message> msgList) {
        this.out = out;
        this.msgList = msgList;
    }

    /**
     * Function send message to the right client
     *
     * @param msg message with metadata which is sending
     */
    public void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
            if ( msg instanceof FileMessage){
                User.getEchoClient().getSendFile().sendFile(Path.of(msg.getPath()), msg.getId());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The main thread in function send. This function reads message from list and sends to client.
     * After sending message the message is being deleted from the list
     */
    public void run(){
        while (true){
            if ( !msgList.isEmpty() ){
                var msg = msgList.get(0);
                sendMessage(msg);
                msgList.remove(0);
            }else{
                User.setEventName("");
                try {
                    Thread.sleep(6000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }

        }
    }
}
