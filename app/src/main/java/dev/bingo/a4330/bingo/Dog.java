package dev.bingo.a4330.bingo;
import java.util.ArrayList;

/**
 * Created by Laila on 11/24/2017.
 */

public class Dog {

    static String dogName, dogWeight;
    Boolean active;
    public String profPic;
    ArrayList<Activity> actList=new ArrayList<>();

    public Dog(String name, String weight){
        dogName = name;
        dogWeight = weight;
        active = true;
    }

    public static void setName(String name){
        dogName = name;
    }
    public String getName(){return dogName;}
    public static void setWeight(String weight){dogWeight = weight;}
    public String getWeight(){return dogWeight;}
    public Boolean getActive(){return active;}
    public String getProfPic(){return profPic;}
    public ArrayList<Activity> getActList(){return actList;}
}
