package cloud.file.management.app;

import cloud.file.management.model.HandlerResources;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class MainConsole {
    public static void main(String[] args) throws FileNotFoundException {
        Path path = Path.of("D:\\Polibuda\\semestr4\\systemy wbudowane");
        HandlerResources.listDirectory(path);
    }
}
