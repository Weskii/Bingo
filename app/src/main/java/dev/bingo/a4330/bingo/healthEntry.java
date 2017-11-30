package dev.bingo.a4330.bingo;
//health entry object class
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class healthEntry {


    public String appName, date, notes, time;

    public healthEntry(String name, String d, String n, String t){
        appName = name;
        date = d;
        notes = n;
        time = t;
    }


    public void setTime(String t){
        time = t;
    }

    public String getTime(){
        return time;
    }

    public void setAppName(String name){
        appName = name;
    }
    public String getAppointment(){
        return appName;
    }

    public void setDate(String d){
        date = d;
    }
    public String getDate(){
        return date;
    }

    public void setNotes(String n){
        notes = n;
    }
    public String getNotes(){
        return notes;
    }
}
