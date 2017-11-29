package dev.bingo.a4330.bingo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laila on 11/24/2017.
 */

public class Activity {

    public double miles=0;
    public String actName, time;
    public Date date;

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

    public double getMiles(){return miles;}
    public String getActName(){
        return actName;
    }
    public void setActName(String name){
        actName = name;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date d){
        date = d;
    }
    //function for getting a time from a function
    public String getTime(){
        return time;
    }

    //function for adding a time to an already existing activity
    public void setTime(String t){
        time = t;
    }
    //converting seconds to minutes string

    @Override
    public String toString() {
        if(miles==0) return String.format("%s for %s minutes",actName,time);
        return String.format("%s for %.2f miles in %s",actName,miles,time);
    }
}
