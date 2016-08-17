package serenity.rs.sporty;

/**
 * Created by Milan on 8/17/2016.
 */
public class Event {

    private int id, requiredPeople, joinedPeople;
    private String title,
                   author,
                   type,
                   date,
                   time,
                   longitude,
                   latitude;

    public Event() {

    }

    public Event(int id, String title, String author, String type, String date, String time, int requiredPeople, int joinedPeople, String longitude, String latitude) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.type = type;
        this.date = date;
        this.time = time;
        this.requiredPeople = requiredPeople;
        this.joinedPeople = joinedPeople;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getJoinedPeople() {
        return joinedPeople;
    }

    public int getRequiredPeople() {
        return requiredPeople;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRequiredPeople(int requiredPeople) {
        this.requiredPeople = requiredPeople;
    }

    public void setJoinedPeople(int joinedPeople) {
        this.joinedPeople = joinedPeople;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", requiredPeople='" + requiredPeople + '\'' +
                ", joinedPeople='" + joinedPeople + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
