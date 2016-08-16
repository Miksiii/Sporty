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
    public static final int    DB_VERSION = 1;

    // user's table attributes
    public static final String USERS_ID       = "ID";
    public static final String USERS_USERNAME = "USERNAME";
    public static final String USERS_PASSWORD = "PASSWORD";
    public static final String USERS_LINK     = "LINK";

    // sport's table attributes
    public static final String SPORTS_ID      = "ID";
    public static final String SPORTS_TITLE   = "TITLE";
    public static final String SPORTS_DESCRIPTION = "DESCRIPTION";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USERS + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SPORTS + ";");
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

}
