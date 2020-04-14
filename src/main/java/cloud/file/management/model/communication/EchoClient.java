package cloud.file.management.model.communication;

import cloud.file.management.common.JoinMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private List<Message> msgList;

    public void startConnection(String ip, int port) {
        msgList = Collections.synchronizedList(new ArrayList<>());
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println(User.getLogin());
            msgList.add(new JoinMessage(User.getLogin()));
            new Send(out, msgList).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            new Receive(in).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void sendMessage(Message msg) {
//        try {
//            out.writeObject(msg);
//            out.flush();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("eror with close connection");
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void addMessage(Message msg){
        msgList.add(msg);
    }

    public List<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList = msgList;
    }
}
