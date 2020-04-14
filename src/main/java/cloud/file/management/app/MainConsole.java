package cloud.file.management.app;

import cloud.file.management.model.HandlerResources;
import cloud.file.management.model.User;

import java.nio.file.Path;

public class MainConsole {
    public static void main(String[] args) throws Exception {
        var user = new User("Krzysiek", Path.of("D:\\Polibuda\\semestr4\\metody numeryczne"), null);
        Path path = Path.of("D:\\Polibuda\\semestr4\\metody numeryczne");
        var p = HandlerResources.listNameFile(path);
        for ( var s : p ){
            System.out.println(s);
        }
    }
}
