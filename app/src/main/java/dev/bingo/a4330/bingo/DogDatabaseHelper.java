package dev.bingo.a4330.bingo;
//dog database

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//dog database helper, handles creating the database
public class DogDatabaseHelper extends SQLiteOpenHelper {
    //creates table
    public static final String TABLE_NAME = "DOGS";

    //columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String WEIGHT = "weight";

    //database info
    static final String DB_NAME = "BINGO_DOGS.DB";
    static final int DB_VERSION = 2;

    //creates table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + WEIGHT + " TEXT);";

    //constructor for helper
    public DogDatabaseHelper(Context context){
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
