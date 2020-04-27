package cloud.file.management.model.communication;

import cloud.file.management.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class implements receiving metadata messages. This class extends by Thread class.
 */
public class Receive extends Thread {
    ObjectInputStream in;

    public Receive(ObjectInputStream in) {
        this.in = in;
    }

    /**
     * This function is responsible for receiving message and running precess this message
     */
    public void run() {
        try {
            Message inputLine;
            while ((inputLine = (Message) in.readObject()) != null) {
                inputLine.preprocess();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
