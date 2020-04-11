package cloud.file.management.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public abstract class FileAPI {

    public static byte[] getStreamFile(Path path){
        try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(path))){
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in file fileApi !!!!!!!!!!!!!!!!!!!");
            return null;
        }
    }
}
