package dev.bingo.a4330.bingo;

/**
 * Created by Laila on 11/24/2017.
 */

public class Dog {

    static String dogName, dogWeight;
    Boolean active;
    public String profPic;

    public Dog(String name, String weight){
        dogName = name;
        dogWeight = weight;
        active = true;
    }

    public static void setName(String name){
        dogName = name;
    }
    public String getName(){
        return dogName;
    }
    public static void setWeight(String weight){
        dogWeight = weight;
    }

    public String getWeight(){
        return dogWeight;
    }
    public Boolean getActive(){
        return active;
    }

    public String getProfPic(){
        return profPic;
    }
}
