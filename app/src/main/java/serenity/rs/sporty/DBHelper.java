package serenity.rs.sporty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Milan on 8/15/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    // database and tables
    public static final String DB_NAME    = "sporty.db";
    public static final String DB_TABLE_USERS   = "users";
    public static final String DB_TABLE_SPORTS  = "sports";
    public static final String DB_TABLE_EVENTS  = "events";
    public static final int    DB_VERSION = 1;

    // user's table attributes
    public static final String USERS_ID               = "ID";
    public static final String USERS_USERNAME         = "USERNAME";
    public static final String USERS_PASSWORD         = "PASSWORD";
    public static final String USERS_LINK             = "LINK";

    // event's table attributes
    public static final String EVENTS_ID              = "ID";
    public static final String EVENTS_TITLE           = "TITLE";
    public static final String EVENTS_AUTHOR          = "USERS_USERNAME";
    public static final String EVENTS_TYPE            = "SPORTS_TYPE";
    public static final String EVENTS_DATE            = "DATE";
    public static final String EVENTS_TIME            = "TIME";
    public static final String EVENTS_REQUIRED_PEOPLE = "REQUIRED_PEOPLE";
    public static final String EVENTS_JOINED_PEOPLE   = "JOINED_PEOPLE";
    public static final String EVENTS_LONGITUDE       = "LONGITUDE";
    public static final String EVENTS_LATITUDE        = "LATITUDE";

    // sport's table attributes
    public static final String SPORTS_ID              = "ID";
    public static final String SPORTS_TITLE           = "TITLE";
    public static final String SPORTS_DESCRIPTION     = "DESCRIPTION";

    // tables creations sql
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + DB_TABLE_USERS
            + "("
            + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USERS_USERNAME + " VARCHAR,"
            + USERS_PASSWORD + " VARCHAR,"
            + USERS_LINK + " VARCHAR)";

    private static final String CREATE_TABLE_SPORTS = "CREATE TABLE " + DB_TABLE_SPORTS
            + "("
            + SPORTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SPORTS_TITLE + " TEXT,"
            + SPORTS_DESCRIPTION + " TEXT)";

    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + DB_TABLE_EVENTS
            + "("
            + EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EVENTS_TITLE + " VARCHAR,"
            + EVENTS_AUTHOR + " VARCHAR,"
            + EVENTS_TYPE + " TEXT,"
            + EVENTS_DATE + " VARCHAR,"
            + EVENTS_TIME + " VARCHAR,"
            + EVENTS_REQUIRED_PEOPLE + " INTEGER,"
            + EVENTS_JOINED_PEOPLE + " INTEGER,"
            + EVENTS_LONGITUDE + " VARCHAR, "
            + EVENTS_LATITUDE + " VARCHAR)";

    // database helper
    SQLiteDatabase db;

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_TABLE_SPORTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USERS + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SPORTS + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_EVENTS+ ";");
        onCreate(sqLiteDatabase);
    }

    /**
     * Verbs for the database methods:
     * - create:object
     * - read:object
     * - update:object
     * - destroy:object
     */

    public boolean createUser(String username, String password, String link)
    {
        ContentValues userContent = new ContentValues();

        userContent.put(USERS_USERNAME, username);
        userContent.put(USERS_PASSWORD, password);
        userContent.put(USERS_LINK, link);

        long query = db.insert(DB_TABLE_USERS, null, userContent);

        return (query == -1) ? false : true;
    }

    public User getUserWithCredentials(String username, String password)
    {
        Cursor cursor = null;
        User user = null;
        try{

            cursor = db.rawQuery("SELECT * FROM  users where username='"+username+"' and password='"+password+"'" , null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            }

            return user;

        }finally {

            cursor.close();
        }
    }

    public boolean updateUser(int id, String username, String password, String link)
    {
        ContentValues userContent = new ContentValues();
        userContent.put(USERS_USERNAME, username);
        userContent.put(USERS_PASSWORD, password);
        userContent.put(USERS_LINK, link);

        long query = db.update(DB_TABLE_USERS, userContent, "ID = ?", new String[] { String.valueOf(id) });

        // singleton update
        User.getUserInstance().setUsername(username);
        User.getUserInstance().setPassword(password);
        User.getUserInstance().setLink(link);

        return true;
    }

    public boolean createSport(String title, String description)
    {
        ContentValues sportContent = new ContentValues();

        sportContent.put(SPORTS_TITLE, title);
        sportContent.put(SPORTS_DESCRIPTION, description);

        long query = db.insert(DB_TABLE_SPORTS, null, sportContent);

        return (query == -1) ? false : true;
    }

    public ArrayList<Sport> getListOfSports() {
        ArrayList<Sport> sportsList = new ArrayList<Sport>();

        Cursor cursor = db.rawQuery("select * from " + DB_TABLE_SPORTS, null);

        while (cursor.moveToNext()) {
            sportsList.add(new Sport(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2)));
        }

        return sportsList;
    }

    public boolean createEvent(String title, String author, String type, String date, String time, int requiredPeople, int joinedPeople, String longitude, String latitude)
    {
        ContentValues eventContent = new ContentValues();

        eventContent.put(EVENTS_TITLE, title);
        eventContent.put(EVENTS_AUTHOR, author);
        eventContent.put(EVENTS_TYPE, type);
        eventContent.put(EVENTS_DATE, date);
        eventContent.put(EVENTS_TIME, time);
        eventContent.put(EVENTS_REQUIRED_PEOPLE, requiredPeople);
        eventContent.put(EVENTS_JOINED_PEOPLE, joinedPeople);
        eventContent.put(EVENTS_LONGITUDE, longitude);
        eventContent.put(EVENTS_LATITUDE, latitude);

        long query = db.insert(DB_TABLE_EVENTS, null, eventContent);

        return (query == -1) ? false : true;
    }

    public ArrayList<Event> getListOfEvents(String sportType) {
        ArrayList<Event> eventsList = new ArrayList<Event>();

        Cursor cursor = db.rawQuery("select * from " + DB_TABLE_EVENTS + " where SPORTS_TYPE='"+sportType+"' order by id desc", null);

        while (cursor.moveToNext()) {
            eventsList.add(new Event(
                    Integer.parseInt(cursor.getString(0)), // id
                    cursor.getString(1),                   // title
                    cursor.getString(2),                   // author
                    cursor.getString(3),                   //type
                    cursor.getString(4),                   // date
                    cursor.getString(5),                   // time
                    Integer.parseInt(cursor.getString(6)), // required people
                    Integer.parseInt(cursor.getString(7)), // joined people
                    cursor.getString(8),                   // longitude
                    cursor.getString(9)                    // latitude
            ));
        }

        return eventsList;
    }

    public ArrayList<Event> getListOfEventsCreatedBy(String username, String sportType) {
        ArrayList<Event> eventsList = new ArrayList<Event>();

        Cursor cursor = db.rawQuery("SELECT * FROM events where users_username='"+username+"' and sports_type='"+sportType+"'" , null);

        while (cursor.moveToNext()) {
            eventsList.add(new Event(
                    Integer.parseInt(cursor.getString(0)), // id
                    cursor.getString(1),                   // title
                    cursor.getString(2),                   // author
                    cursor.getString(3),                   //type
                    cursor.getString(4),                   // date
                    cursor.getString(5),                   // time
                    Integer.parseInt(cursor.getString(6)), // required people
                    Integer.parseInt(cursor.getString(7)), // joined people
                    cursor.getString(8),                   // longitude
                    cursor.getString(9)                    // latitude
            ));
        }

        return eventsList;
    }

    public int destroyEventWithId(String id) {
        // returns the number of deleted rows
        return db.delete(DB_TABLE_EVENTS, "ID = ?",new String[] {id});
    }

}
