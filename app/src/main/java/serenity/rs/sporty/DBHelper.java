package serenity.rs.sporty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Milan on 8/15/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    // database and tables
    public static final String DB_NAME    = "sporty.db";
    public static final String DB_TABLE_USERS   = "users";
    public static final int    DB_VERSION = 1;

    // user's table attributes
    public static final String USERS_ID       = "ID";
    public static final String USERS_USERNAME = "USERNAME";
    public static final String USERS_PASSWORD = "PASSWORD";
    public static final String USERS_LINK     = "LINK";

    // tables creations sql
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + DB_TABLE_USERS
            + "("
            + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USERS_USERNAME + " VARCHAR,"
            + USERS_PASSWORD + " VARCHAR,"
            + USERS_LINK + " VARCHAR)";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USERS + ";");
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

    public Cursor getUserWithCredentials(String username, String password)
    {
        String query = "select * from " + DB_TABLE_USERS + " where USERNAME = " + username + " and PASSWORD = "
                     + password;
        return db.rawQuery(query, null);
    }

}
