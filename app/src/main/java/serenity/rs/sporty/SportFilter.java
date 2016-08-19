package serenity.rs.sporty;

/**
 * Created by Milan on 8/18/2016.
 */
public class SportFilter
{
    private static SportFilter sportFilterInstance = new SportFilter();
    private String sportFilter;

    public SportFilter() {}

    public SportFilter(String sportFilter)
    {
        this.sportFilter = sportFilter;
    }

    public static SportFilter getSportFilterInstance()
    {
        if (sportFilterInstance == null)
            sportFilterInstance = new SportFilter();

        return sportFilterInstance;
    }

    public String getSportFilter() {
        return sportFilter;
    }

    public void setSportFilter(String sportFilter) {
        this.sportFilter = sportFilter;
    }

}
