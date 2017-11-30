package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//The firstRun activity only has one purpose: forcing the user to create an initial dog object
public class FirstRun extends AppCompatActivity {

    //The onCreate method runs when the activity starts.
    //I have it get a handle to my shared preferences file, which I only use for one thing:
    //a String that says "this is not the first run."
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        //Get a handle to my shared preferences file
        SharedPreferences firstRun=getSharedPreferences("bingo.dsp",0);
        //If "isNotFirstRun" exists in that file, immediately boot the user to the home screen
        if (firstRun.contains("isNotFirstRun")) {
            Intent homeScreenIntent = new Intent(this, HomeScreen.class);
            startActivity(homeScreenIntent);
            finish();
        }
    }

    //The only button displayed on the firstRun activity is Bingo's logo. Clicking it
    //boots you into the AddDog activity to populate the dog list with at least one pet
    //Again, this button and entire activity is only displayed if "isNotFirstRun" doesn't exist
    //in the shared preferences file.
    public void newDogButton(View view) {
        //If this button is being shown, clicking it puts isNotFirstRun in that file so the user
        //never sees the firstRun screen again, i.e., it's only seen on first run of the app
        SharedPreferences firstRun=getSharedPreferences("bingo.dsp",0);
        SharedPreferences.Editor spEdit=firstRun.edit();
        spEdit.putBoolean("isNotFirstRun",true);
        spEdit.commit();
        Intent newDog=new Intent(this,AddDog.class);
        startActivity(newDog);
    }
}


