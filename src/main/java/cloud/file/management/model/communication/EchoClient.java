package cloud.file.management.model.communication;

import cloud.file.management.common.JoinMessage;
import cloud.file.management.common.Message;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoClient {
    private Socket clientSocket;
    private Socket clientSocketFile;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SendFile sendFile;
    private ReceiveFile receiveFile;
    private Send send;
    private List<Message> msgList;
    private List<Message> msgListReceive;

    private OutputStream outFile;

    public void startConnection(String ip, int port) {
        msgList = Collections.synchronizedList(new ArrayList<>());
        msgListReceive = Collections.synchronizedList(new ArrayList<>());
        try {
            clientSocket = new Socket(ip, port);
            clientSocketFile = new Socket(ip, port+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            msgList.add(new JoinMessage(User.getLogin()));
            send = new Send(out, msgList);
            send.start();

            outFile = clientSocketFile.getOutputStream();
            sendFile = new SendFile(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            receiveFile = new ReceiveFile(clientSocketFile.getInputStream(), msgListReceive);
            new Receive(in).start();
            receiveFile.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("error with close connection");
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

    public Socket getClientSocketFile() {
        return clientSocketFile;
    }

    public void setClientSocketFile(Socket clientSocketFile) {
        this.clientSocketFile = clientSocketFile;
    }

    public OutputStream getOutFile() {
        return outFile;
    }

    public void setOutFile(OutputStream outFile) {
        this.outFile = outFile;
    }

    public SendFile getSendFile() {
        return sendFile;
    }

    public void setSendFile(SendFile sendFile) {
        this.sendFile = sendFile;
    }

    public ReceiveFile getReceiveFile() {
        return receiveFile;
    }

    public void setReceiveFile(ReceiveFile receiveFile) {
        this.receiveFile = receiveFile;
    }

    public List<Message> getMsgListReceive() {
        return msgListReceive;
    }

    public void setMsgListReceive(List<Message> msgListReceive) {
        this.msgListReceive = msgListReceive;
    }

    public Send getSend() {
        return send;
    }

    public void setSend(Send send) {
        this.send = send;
    }
}
