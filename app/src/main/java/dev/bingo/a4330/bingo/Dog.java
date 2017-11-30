package dev.bingo.a4330.bingo;
import java.util.ArrayList;

//dog class
public class Dog {

    static String dogName, dogWeight;
    Boolean isActive;
    public String profPic;
    //activity list attached to dog
    public ArrayList<Activity> actList=new ArrayList<>();

    public Dog(){
        super();
    }

    public Dog(String name, String weight){
        this.dogName = name;
        this.dogWeight = weight;
        this.isActive = false;
    }

    //used for getting information about active dog
    public void setName(String name){
        dogName = name;
    }
    public String getPetName(){return dogName;}
    public void setWeight(String weight){dogWeight = weight;}
    public String getPetWeight(){return dogWeight;}
    public Boolean getActive(){return isActive;}
    public void setActive(){

    }
    public String getProfPic(){return profPic;}
}
