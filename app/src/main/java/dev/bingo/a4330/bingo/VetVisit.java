package dev.bingo.a4330.bingo;

/**
 * Created by Laila on 11/24/2017.
 */

public class VetVisit {


    public static String appName, date, notes, time;

    public VetVisit(String name, String d, String n, String t){
        appName = name;
        date = d;
        notes = n;
        time = t;
    }


    public static void setTime(String t){
        time = t;
    }

    public String getTime(){
        return time;
    }

    public static void setAppName(String name){
        appName = name;
    }
    public String getAppointment(){
        return appName;
    }

    public static void setDate(String d){
        date = d;
    }
    public String getDate(){
        return date;
    }

    public static void setNotes(String n){
        notes = n;
    }
    public String getNotes(){
        return notes;
    }
}
