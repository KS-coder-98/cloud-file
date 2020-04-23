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

    public SendFile(OutputStream out){
        this.out = out;
    }

    public void sendFile(Path path, long id){
        try{
            //send id
            byte[] idBytes = Convert.longToBytes(id);
            out.write(idBytes);

//            File file = new File(pathStr);

            FileChannel fileChannel = FileChannel.open(Path.of( User.getPath()+"\\"+ path));

            System.out.println("wyslalo id wysyla rozmiar");
            //send size
            long size = fileChannel.size();
            byte[] sizeFile = Convert.longToBytes(size);
            out.write(sizeFile);
            System.out.println("rozmiar pliku: "+size);
            //buffer
            byte[] buffer = new byte[1024];

            //send file
            System.out.println("zaczelo wyslyac plik: "+path);

            //         Allocate a ByteBuffer
            int count;
            long sizeBuffer = 1024;
            if ( size < 1024 ){
                sizeBuffer = size;
            }
            ByteBuffer buffer1 = ByteBuffer.allocate((int)sizeBuffer);

            int sendBytes=0;
            while(  (count=fileChannel.read(buffer1) ) > 0) {

                buffer1.flip();
                out.write(buffer1.array());
                buffer1.clear();
                sendBytes+=count;
                if ( (size - sendBytes) < 1024 ){
                    buffer1 = ByteBuffer.allocate((int)size - sendBytes);
                }
            }

            System.out.println("wysłana plik sendFile rozmiar wysłany: "+sendBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
