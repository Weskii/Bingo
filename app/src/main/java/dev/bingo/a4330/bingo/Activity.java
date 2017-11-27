package dev.bingo.a4330.bingo;

/**
 * Created by Laila on 11/24/2017.
 */

public class Activity {

    public static float miles;
    public static String actName, date, time;


    //new activity with length of time and date
    public Activity(String name, String t, String d){
        actName = name;
        time = t;
        date = d;
    }

    //new activity for the GPS walking function
    public Activity(String name, String t, String d, float m){
        actName = name;
        time = t;
        date = d;
        miles = m;
    }

    public float getMiles(){
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
    public String getDate(){
        return date;
    }
    public static void setDate(String d){
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

}
