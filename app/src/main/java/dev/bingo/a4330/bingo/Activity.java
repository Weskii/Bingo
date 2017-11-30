package dev.bingo.a4330.bingo;

import java.util.Date;

public class Activity {
    //These are some variables I need to initialize an store in each activity
    //I store the date as a Date object and as a date string for easy manipulation and display
    public double miles=0;
    public String actName, time, dateString;
    public Date date;

    //A constructor for non-walking activities, keeping miles set at 0
    public Activity(String name, String t, Date d, String dS){
        actName = name;
        time = t;
        date = d;
        dateString=dS;
    }

    //A constructor for a walking activity, setting miles to a value
    public Activity(String name, String t, Date d, String dS, double m){
        actName = name;
        time = t;
        date = d;
        dateString=dS;
        miles = m;
    }

    //I need two accessors: getMiles, as it is used in the calculation of total distance
    //stored in the activity log
    public double getMiles(){return miles;}
    //getDate, as it is used in the removal of entries older than 7 days from the log
    public Date getDate(){return date;}

    //I override the toString method to easily adapt the array list of activites in a Dog object
    //to the list view in the activity log
    @Override
    public String toString() {
        if(miles==0) return String.format("%s%n%s for %s minutes",dateString,actName,time);
        return String.format("%s%n%s for %.2f miles in %s",dateString,actName,miles,time);
    }
}
