package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FirstRun extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        SharedPreferences firstRun=getSharedPreferences("bingo.dsp",0);
        if (firstRun.contains("isNotFirstRun")) {
            Intent homeScreenIntent = new Intent(this, HomeScreen.class);
            startActivity(homeScreenIntent);
            finish();
        }
    }
    public void newDogButton(View view) {
        SharedPreferences firstRun=getSharedPreferences("bingo.dsp",0);
        SharedPreferences.Editor spEdit=firstRun.edit();
        spEdit.putBoolean("isNotFirstRun",true);
        spEdit.commit();
        Intent newDog=new Intent(this,HomeScreen.class);
        startActivity(newDog);
    }
}


