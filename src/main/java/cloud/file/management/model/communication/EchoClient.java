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

/**
 *This class stores data about client. In this class are implement socket for metadata based on Object Input/Output Stream and
 * socket for transfer file based on Input Input/Output stream
 */
public class EchoClient {
    private Socket clientSocket;
    private Socket clientSocketFile;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SendFile sendFile;
    private Send send;
    private List<Message> msgList;
    private List<Message> msgListReceive;

    /**
     * This method establishes connection with server.
     * Communication process is bound with specified socket for metadata and specified socket for the transfer file.
     * Port for socket transfer file is equal port plus 1. List 'msgList' is created in purpose stores metadata message
     * to send. List 'msgListReceive' is created in purpose stores received metadata message. Variable client socket
     * hold socket for metadata transfer file, Variable clientSocketFile hold socket to transfer file.
     *
     * @param ip ip address server
     * @param port port server to start connection
     */
    public void startConnection(String ip, int port) {
        msgList = Collections.synchronizedList(new ArrayList<>());
        msgListReceive = Collections.synchronizedList(new ArrayList<>());
        try {
            clientSocket = new Socket(ip, port);
            clientSocketFile = new Socket(ip, port + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            msgList.add(new JoinMessage(User.getLogin()));
            send = new Send(out, msgList);
            send.start();

            OutputStream outFile = clientSocketFile.getOutputStream();
            sendFile = new SendFile(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            ReceiveFile receiveFile = new ReceiveFile(clientSocketFile.getInputStream(), msgListReceive);
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


    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void addMessage(Message msg) {
        msgList.add(msg);
    }

    public SendFile getSendFile() {
        return sendFile;
    }

    public List<Message> getMsgListReceive() {
        return msgListReceive;
    }

    public Send getSend() {
        return send;
    }

}
