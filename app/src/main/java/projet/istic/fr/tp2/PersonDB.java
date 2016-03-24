package projet.istic.fr.tp2;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PersonDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_FIRSTNAME = "firstname";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_DATE = "date";
    public static final String KEY_CITY = "city";

    private static final String LOG_TAG = "PersonDB";
    public static final String SQLITE_TABLE = "Person";

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_FIRSTNAME + "," +
                    KEY_LASTNAME + "," +
                    KEY_DATE + "," +
                    KEY_CITY + "," +
                    " UNIQUE (" + KEY_ROWID +"));";

    public static void onCreate(SQLiteDatabase db) {
        Log.w(LOG_TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }

}

