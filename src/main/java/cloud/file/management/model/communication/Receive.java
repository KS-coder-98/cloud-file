package cloud.file.management.model.communication;

import cloud.file.management.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Receive extends Thread {
    ObjectInputStream in;

    public Receive(ObjectInputStream in) {
        this.in = in;
    }

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
