package cloud.file.management.app;

import cloud.file.management.model.HandlerResources;

import java.nio.file.Path;

public class MainConsole {
    public static void main(String[] args) throws Exception {
        Path path = Path.of("D:\\Polibuda\\semestr4\\metody numeryczne");
        var p = HandlerResources.date(path);
        System.out.println(p);
    }
}
