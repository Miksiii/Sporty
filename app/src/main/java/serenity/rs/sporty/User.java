package serenity.rs.sporty;

/**
 * Created by Milan on 8/16/2016.
 */
public class User {

    private static User userInstance = new User();
    private int id;
    private String username, password, link;

    public User() {

    }

    public User(int id, String username, String password, String link) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.link = link;
    }

    public static User getUserInstance() {
        if (userInstance == null) {
            userInstance = new User();
        }

        return userInstance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLink() {
        return link;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
