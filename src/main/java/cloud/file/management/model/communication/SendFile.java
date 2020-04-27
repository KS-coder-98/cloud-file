package cloud.file.management.model.communication;

import cloud.file.management.model.Convert;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * Class implements sending file. This class is based on OutputStream.
 */
public class SendFile {
    private OutputStream out;

    /**
     * Create object with bind specified OutputStream
     *
     * @param out stream is responsible for sending file
     */
    public SendFile(OutputStream out) {
        this.out = out;
    }

    /**
     * Function sends file to client.
     * First send 8 bytes are assigned to id. Next send 8 bytes is size file. After that send all file data. For reading
     * file's bytes is used FileChanel class
     *
     * @param path path where is located file to send
     * @param id id file is added to recognize file
     */
    public void sendFile(Path path, long id) {
        try {
            //send id
            System.out.println("start wysyłania id: " + id);
            byte[] idBytes = Convert.longToBytes(id);
            out.write(idBytes);

            //send size
            FileChannel fileChannel = FileChannel.open(Path.of(User.getPath() + "\\" + path));
            long size = fileChannel.size();

            byte[] sizeFile = Convert.longToBytes(size);
            out.write(sizeFile);


            //   Allocate a ByteBuffer
            long defaultSizeBuffer = 1024;
            long sizeBuffer = Math.min(size, defaultSizeBuffer);
            ByteBuffer buffer = ByteBuffer.allocate((Math.toIntExact(sizeBuffer)));

            long sendBytes = 0, count;
            User.setEventName("send file: "+path);
            while ((count = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                out.write(buffer.array());
                buffer.clear();
                sendBytes += count;
                if ((size - sendBytes) < defaultSizeBuffer) {
                    buffer = ByteBuffer.allocate(Math.toIntExact(size - sendBytes));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
