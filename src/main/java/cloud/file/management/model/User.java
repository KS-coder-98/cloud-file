package cloud.file.management.model;

import cloud.file.management.model.communication.EchoClient;

import java.nio.file.Path;

public class User {
    private static String login;
    private static Path path;
    private static EchoClient echoClient;

    public static String getLogin() {
        return login;
    }


    public static Path getPath() {
        return path;
    }

    public static EchoClient getEchoClient() {
        return echoClient;
    }

    public static void setEchoClient(EchoClient echoClient) {
        User.echoClient = echoClient;
    }

    public User(String login, Path path, EchoClient echoClient) {
        User.login = login;
        User.path = path;
        User.echoClient = echoClient;
    }
}
