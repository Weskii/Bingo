package dev.bingo.a4330.bingo;

/**
 * Created by Laila on 11/24/2017.
 */

public class VetVisit {


    public String appName, date, notes, time;

    public VetVisit(String name, String d, String n, String t){
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
