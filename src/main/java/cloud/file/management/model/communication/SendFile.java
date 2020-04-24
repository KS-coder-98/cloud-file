package cloud.file.management.model.communication;

import cloud.file.management.model.Convert;
import cloud.file.management.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class SendFile {
    private OutputStream out;

    public SendFile(OutputStream out) {
        this.out = out;
    }

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
            while ((count = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                out.write(buffer.array());
                buffer.clear();
                sendBytes += count;
                System.out.println("send Byte" + sendBytes);
                if ((size - sendBytes) < defaultSizeBuffer) {
                    buffer = ByteBuffer.allocate(Math.toIntExact(size - sendBytes));
                }
            }
            System.out.println("koniec wysyłania id: " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
