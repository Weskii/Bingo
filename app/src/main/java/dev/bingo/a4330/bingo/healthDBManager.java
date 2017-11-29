package dev.bingo.a4330.bingo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Laila on 11/29/2017.
 */

public class healthDBManager {

    private healthDatabaseHelper dbHelp;
    private Context context;
    private SQLiteDatabase db;

    public healthDBManager(Context c){
        context = c;
    }

    public healthDBManager open() throws SQLException {
        dbHelp = new healthDatabaseHelper(context);
        db = dbHelp.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelp.close();
    }

    public void insert(String name, String date, String time, String notes){
        ContentValues cV = new ContentValues();
        cV.put(healthDatabaseHelper.NAME, name);
        cV.put(healthDatabaseHelper.DATE, date);
        cV.put(healthDatabaseHelper.TIME, time);
        cV.put(healthDatabaseHelper.NOTES, notes);
        db.insert(healthDatabaseHelper.TABLE_NAME, null, cV);
    }

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

    public int update(long _id, String name, String date, String time, String notes){
        ContentValues cV = new ContentValues();
        cV.put(healthDatabaseHelper.NAME, name);
        cV.put(healthDatabaseHelper.DATE, date);
        cV.put(healthDatabaseHelper.TIME, time);
        cV.put(healthDatabaseHelper.NOTES, notes);
        int i = db.update(healthDatabaseHelper.TABLE_NAME, cV, healthDatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete (long _id){
        db.delete(healthDatabaseHelper.TABLE_NAME, healthDatabaseHelper._ID + "=" + _id, null);
    }
}
