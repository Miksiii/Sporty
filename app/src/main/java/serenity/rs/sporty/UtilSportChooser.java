package serenity.rs.sporty;

/**
 * Created by Milan on 8/18/2016.
 */
public class UtilSportChooser
{
    private static UtilSportChooser utilSportChooserInstance = new UtilSportChooser();
    private String currentlyActiveSport;

    public UtilSportChooser()
    {

    }

    public UtilSportChooser(String currentlyActiveSport)
    {
        this.currentlyActiveSport = currentlyActiveSport;
    }

    public static UtilSportChooser getUtilSportChooserInstance() {
        if (utilSportChooserInstance == null) {
            utilSportChooserInstance = new UtilSportChooser();
        }

        return utilSportChooserInstance;
    }

    public String getCurrentlyActiveSport() {
        return currentlyActiveSport;
    }

    public void setCurrentlyActiveSport(String currentlyActiveSport) {
        this.currentlyActiveSport = currentlyActiveSport;
    }

    @Override
    public String toString() {
        return "UtilSportChooser{" +
                "currentlyActiveSport='" + currentlyActiveSport + '\'' +
                '}';
    }
}
