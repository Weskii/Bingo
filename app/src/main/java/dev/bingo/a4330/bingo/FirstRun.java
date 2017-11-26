package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstRun extends AppCompatActivity {
    public static SharedPreferences firstRun;
    public static SharedPreferences.Editor spEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        firstRun = PreferenceManager.getDefaultSharedPreferences(this);
        if (firstRun.getBoolean("isFirstRun", true)) {
            Intent homeScreenIntent = new Intent(this, HomeScreen.class);
            startActivity(homeScreenIntent);
            finish();
        }
    }
    public void newDogButton(View view) {
        spEdit.putBoolean("isFirstRun",false);
        //Intent newDog=new Intent(this,newDog.class);
        //startActivity(newDog);
    }
}


