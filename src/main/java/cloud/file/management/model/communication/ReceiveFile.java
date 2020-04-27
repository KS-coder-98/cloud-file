package cloud.file.management.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.model.Convert;
import cloud.file.management.model.FileAPI;
import cloud.file.management.model.LambdaExpression;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for receiving file message. Receiving message is implemented by using InputStream.
 * All messages start with 8 bytes and this id file and next 8 bytes is size file after that files byte begin.
 */
public class ReceiveFile extends Thread {
    private InputStream in;
    List<Message> msgList;

    /**
     * Create object ReceiveFile, with bind specified InputStream and list.
     *
     * @param in Object is responsible for receiving a file in bytes from the client
     * @param msgList This list consists of metadata message. From the list we can check if we should receive some files
     *                or not because list is empty. This means if the list is not empty we start receiving file
     */
    public ReceiveFile(InputStream in, List<Message> msgList) {
        this.in = in;
        this.msgList = msgList;
    }

    /**
     * This is main thread for class ReceiveFile. It is responsible for receiving message and must be started after that
     * we create ReceiveFile. For each this thread checks if list with FileMessage is empty or not. If list is empty,
     * thread is sleeping for 1k millis second. But the if the list has some messages, thread starts reading object from input stream
     * First reads first 8 bytes and this bytes are assigned as id file. Based on metadata message it creates fileChanel for file.
     * After that it reads next 8 bytes that are assigned this as size reading file. After that function has all necessary information
     * and starts reading file's bytes from InputStream and saves them to proper localisation. At the end it closes the FileChanel
     *
     */
    public void run() {
        while (true) {
            if (!msgList.isEmpty()) {
                try {
                    FileChannel fileChannel = null;

                    byte[] id = new byte[Long.BYTES];
                    in.read(id);
                    Message msg = findMsgMetaData(Convert.bytesToLong(id));

                    //create file
                    var path = FileAPI.createFoldersFromPath(Path.of(msg.getPath()), "");
                    System.out.println("sciezka + " + path);
                    fileChannel = FileChannel.open(Path.of(path),
                            EnumSet.of(StandardOpenOption.CREATE,
                                    StandardOpenOption.TRUNCATE_EXISTING,
                                    StandardOpenOption.WRITE)
                    );

                    //read file
                    byte[] sizeFile = new byte[Long.BYTES];
                    in.read(sizeFile);
                    long sizeL = Convert.bytesToLong(sizeFile);
                    System.out.println("rozmiar pliku: " + sizeL);

                    //set size for buffer
                    int bufferSize = 1024;
                    if (bufferSize > sizeL) {
                        bufferSize = Math.toIntExact(sizeL);
                    }
                    byte[] bufferFile = new byte[bufferSize];
                    User.setEventName("receive file "+ path);
                    readAndSaveFile(fileChannel, bufferSize, bufferFile, sizeL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * The function looks in list which consist of FileMessage. The function doesn't end until it doesn't find the correct message
     *
     * @param id This value represents files
     * @return Return the message which consists of metadata reading file
     */
    private Message findMsgMetaData(long id) {
        while (true) {

            var message = LambdaExpression.find(msgList, msg -> id == msg.getId());
            System.out.println("id szukane " + id);
            System.out.println("wiadomosci: " + msgList.toString());
            if (!Objects.isNull(message)) {
                msgList.remove(message);
                System.out.println("znalazlo metadane");
                return message;
            }
            //wait if msg no receive yet
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function reads and saves file.
     *
     * @param fileChannel object represents chanel for saving file
     * @param sizeBufferForFile this value sets the buffer size which is used to read bytes from the input stream
     * @param buffer array which we save data during receiving bytes
     * @param sizeL size of reading file
     * @throws IOException throw exception if something goes wrong with reading or saving the file
     */
    private void readAndSaveFile(FileChannel fileChannel, int sizeBufferForFile, byte[] buffer, long sizeL) throws IOException {
        int countReadBytes;
        while (true) {
            if (!((countReadBytes = in.read(buffer)) > 0))
                break;
            fileChannel.write(ByteBuffer.wrap(buffer));
            sizeL -= countReadBytes;
            if (sizeL < sizeBufferForFile)
                buffer = new byte[Math.toIntExact(sizeL)];
        }
        fileChannel.close();
    }
}
