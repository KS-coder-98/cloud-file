package cloud.file.management.model;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class HandlerResources {

    public static TreeItem<String> listDirectory(Path path) throws FileNotFoundException {
        if ( !Files.exists(path) )
            throw new FileNotFoundException("wrong path");
        String dirPath = path.toString();
        File dir = new File(dirPath);
        File[] firstLevelFiles = dir.listFiles();
        TreeItem<String> root = new TreeItem<>(dirPath);
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                if (aFile.isDirectory()) {
                    root.getChildren().add(listDirectory(Path.of(aFile.getPath())));
                } else {
                    root.getChildren().add(new TreeItem<>(aFile.getName()));
                }
            }
        }
        return root;
    }
}
