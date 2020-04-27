package cloud.file.management.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileAPI {

    public static String createFoldersFromPath(Path path, String dst) {
        String[] splitPath = path.toString().split("\\\\");
        String partOfPart = User.getPath().toString();
        if (dst.length() != 0)
            partOfPart = partOfPart + "\\" + dst;
        if (splitPath.length > 1) {
            for (int i = 0; i < splitPath.length - 1; i++) {
                partOfPart = partOfPart + "\\" + splitPath[i];
                if (!Files.exists(Path.of(partOfPart))) {
                    try {
                        Files.createDirectories(Path.of(partOfPart));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return partOfPart + "\\" + splitPath[splitPath.length - 1];
        } else {
            if (dst.length() != 0)
                return User.getPath() + "\\" + dst + "\\" + path;
            else
                return User.getPath() + "\\" + path;
        }
    }
}
