package dev.bingo.a4330.bingo;
//handles any changes made to health database
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class healthDBManager {

    private healthDatabaseHelper dbHelp;
    private Context context;
    private SQLiteDatabase db;

    public healthDBManager(Context c){
        context = c;
    }

    //opens database
    public healthDBManager open() throws SQLException {
        dbHelp = new healthDatabaseHelper(context);
        db = dbHelp.getWritableDatabase();
        return this;
    }

    //closes database
    public void close(){
        dbHelp.close();
    }

    //adds new entry to database
    public void insert(String name, String date, String time, String notes){
        ContentValues cV = new ContentValues();
        cV.put(healthDatabaseHelper.NAME, name);
        cV.put(healthDatabaseHelper.DATE, date);
        cV.put(healthDatabaseHelper.TIME, time);
        cV.put(healthDatabaseHelper.NOTES, notes);
        db.insert(healthDatabaseHelper.TABLE_NAME, null, cV);
    }

    //gets information from database
    public Cursor fetch(){
        String[] columns = new String[] {
                healthDatabaseHelper._ID, healthDatabaseHelper.NAME, healthDatabaseHelper.DATE, healthDatabaseHelper.TIME, healthDatabaseHelper.NOTES
        };
        Cursor cursor = db.query(healthDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //updates any changes made to entries in database
    public int update(long _id, String name, String date, String time, String notes){
        ContentValues cV = new ContentValues();
        cV.put(healthDatabaseHelper.NAME, name);
        cV.put(healthDatabaseHelper.DATE, date);
        cV.put(healthDatabaseHelper.TIME, time);
        cV.put(healthDatabaseHelper.NOTES, notes);
        int i = db.update(healthDatabaseHelper.TABLE_NAME, cV, healthDatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    //deletes from database
    public void delete (long _id){
        db.delete(healthDatabaseHelper.TABLE_NAME, healthDatabaseHelper._ID + "=" + _id, null);
    }
}
