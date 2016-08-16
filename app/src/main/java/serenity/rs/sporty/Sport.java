package serenity.rs.sporty;

/**
 * Created by Milan on 8/16/2016.
 */
public class Sport {
    private int id;
    private String title, description;

    public Sport() {

    }

    public Sport(int id, String title, String description) {
         this.id = id;
         this.title = title;
         this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
