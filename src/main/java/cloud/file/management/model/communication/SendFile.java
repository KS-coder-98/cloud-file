package cloud.file.management.model.communication;

import cloud.file.management.model.Convert;
import cloud.file.management.model.User;

import java.io.*;
import java.nio.file.Path;

public class SendFile {
    private OutputStream out;

    public SendFile(OutputStream out){
        this.out = out;
    }

    public void sendFile(Path path, long id){
        try{
            String pathStr = User.getPath()+""+path;
            System.out.println("sciezka :" +User.getPath());
            System.out.println("wysyla id");
            //send id
            byte[] idBytes = Convert.longToBytes(id);
            out.write(idBytes);

            File file = new File(pathStr);
            System.out.println("wyslalo id wysyla rozmiar");
            //send size
            long size = file.length();
            byte[] sizeFile = Convert.longToBytes(size);
            out.write(sizeFile);

            //buffer
            byte[] buffer = new byte[1024];

            //send file
            System.out.println("zaczelo wyslyac plik: "+path);
            InputStream in = new FileInputStream(pathStr);
            int count;
            while ((count=in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            System.out.println("wysłana plik sendFile");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
