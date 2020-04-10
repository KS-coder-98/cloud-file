package cloud.file.management.model;

import java.nio.file.Path;

public class User {
    private String login;
    private static Path path;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public User(String login, Path path) {
        this.login = login;
        this.path = path;
    }
}
