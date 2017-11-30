package dev.bingo.a4330.bingo;
//handles any changes made to dog database
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class dogDBManager {

    private DogDatabaseHelper dbHelp;
    private Context context;
    private SQLiteDatabase db;

    public dogDBManager(Context c){
        context = c;
    }

    //opens database
    public dogDBManager open() throws SQLException {
        dbHelp = new DogDatabaseHelper(context);
        db = dbHelp.getWritableDatabase();
        return this;
    }

    //closes database
    public void close(){
        dbHelp.close();
    }

    public void insert(String name, String weight){
        ContentValues cV = new ContentValues();
        cV.put(DogDatabaseHelper.NAME, name);
        cV.put(DogDatabaseHelper.WEIGHT, weight);
        db.insert(DogDatabaseHelper.TABLE_NAME, null, cV);
    }

    //gets information from database
    public Cursor fetch(){
        String[] columns = new String[] {
                DogDatabaseHelper._ID, DogDatabaseHelper.NAME, DogDatabaseHelper.WEIGHT
        };
        Cursor cursor = db.query(DogDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //updates any changes made to entries in database
    public int update(long _id, String name, String weight){
        ContentValues cV = new ContentValues();
        cV.put(DogDatabaseHelper.NAME, name);
        cV.put(DogDatabaseHelper.WEIGHT, weight);
        int i = db.update(DogDatabaseHelper.TABLE_NAME, cV, DogDatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    //deletes selected dog from database
    public void delete (long _id){
        db.delete(DogDatabaseHelper.TABLE_NAME, DogDatabaseHelper._ID + "=" + _id, null);
    }
}
