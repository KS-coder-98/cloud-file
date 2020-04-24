package cloud.file.management.model.communication;

import cloud.file.management.common.Message;
import cloud.file.management.model.Convert;
import cloud.file.management.model.FileAPI;
import cloud.file.management.model.LambdaExpression;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class ReceiveFile extends Thread{
    private InputStream in;
    List<Message> msgList;

    public ReceiveFile(InputStream in, List<Message>msgList){
        this.in = in;
        this.msgList = msgList;
    }

    public void run() {
        while (true) {
            if (!msgList.isEmpty()) {
                System.out.println("lista plikow "+ msgList.size());
                try {
                    FileChannel fileChannel = null;

                    byte[] id = new byte[Long.BYTES];
                    System.err.println("czytane id!!!!!!!!!!!!!!!");
                    in.read(id);
                    System.out.println("odczytane id= " + Convert.bytesToLong(id));
                    Message msg = findMsgMetaData(Convert.bytesToLong(id));

                    //create file
                    if (Objects.isNull(msg.getPath()))
                        System.out.println("sciezka jest nulemmmmmmmm!!!!!!!!!!!!!!!!!");
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
                        bufferSize = Math.toIntExact( sizeL);
                    }
                    byte[] bufferFile = new byte[bufferSize];
                    readAndSaveFile(fileChannel, bufferSize, bufferFile, sizeL);
                    System.out.println("save complit");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Message findMsgMetaData(long id) {
        while (true) {

            var message = LambdaExpression.find(msgList, msg -> id == msg.getId());
            System.out.println("id szukane "+id);
            System.out.println("wiadomosci: "+ msgList.toString());
            if (!Objects.isNull(message)){
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
