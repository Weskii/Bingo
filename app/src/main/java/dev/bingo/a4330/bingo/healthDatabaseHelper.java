package dev.bingo.a4330.bingo;
//creates health entry database
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//dog database helper, handles creating the database
public class healthDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "HEALTH_ENTRIES";

    //columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NOTES = "notes";
    //database info
    static final String DB_NAME = "BINGO_HEALTH.DB";
    static final int DB_VERSION = 1;

    //creates table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + DATE + " TEXT NOT NULL, "+ TIME + " TEXT, " + NOTES + " TEXT);";

    //constructor for helper
    public healthDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    //if there's no database and one is needed, points to a newly made database
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    //compares the older version of the database and the newer.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
