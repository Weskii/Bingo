package dev.bingo.a4330.bingo;

import java.util.Date;

/**
 * Created by Laila on 11/24/2017.
 */

public class Activity {

    public static double miles=0;
    public static String actName, time;
    public static Date date;

    //new activity with length of time and date
    public Activity(String name, String t, Date d){
        actName = name;
        time = t;
        date = d;
    }

    //new activity for the GPS walking function
    public Activity(String name, String t, Date d, double m){
        actName = name;
        time = t;
        date = d;
        miles = m;
    }

    public double getMiles(){
        return miles;
    }
    public static void setMiles(float m){
        miles = m;
    }
    public String getActName(){
        return actName;
    }
    public static void setActName(String name){
        actName = name;
    }
    public Date getDate(){
        return date;
    }
    public static void setDate(Date d){
        date = d;
    }
    //function for getting a time from a function
    public String getTime(){
        return time;
    }

    //function for adding a time to an already existing activity
    public static void setTime(String t){
        time = t;
    }
    //converting seconds to minutes string
    public static String secToMin(String t){
        int timeMin=Integer.parseInt(time);
        int timeSecs=Integer.parseInt(time);
        timeMin=timeSecs%60;
        timeSecs=timeSecs-(timeMin*60);
        return String.valueOf(timeMin)+":"+String.valueOf(timeSecs);
    }

    @Override
    public String toString() {
        if(miles==0) return String.format("%s%n%for %s",actName,secToMin(time));
        return String.format("%s%n%.2f in %s",actName,miles,secToMin(time));
    }
}
