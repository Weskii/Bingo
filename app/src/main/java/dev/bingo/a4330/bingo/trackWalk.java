package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;

public class trackWalk extends AppCompatActivity {
    GPSTracker gps=new GPSTracker(this);
    Chronometer chrono;
    Gson gson=new Gson();
    Dog curDog;
    ImageView pauseButton;
    View stopButton;
    TextView pauseText;
    boolean paused=false;
    double distance=0;
    long chronoStop=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_walk);
        chrono=findViewById(R.id.chrono);
        pauseButton=findViewById(R.id.pauseButton);
        stopButton=findViewById(R.id.stopButton);
        pauseText=findViewById(R.id.pauseText);
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        stopButton.setVisibility(View.GONE);
        chrono.start();
    }

    public void pauseWalkButton(View view){
        if(!paused) {
            chronoStop=chrono.getBase()-SystemClock.elapsedRealtime();
            chrono.stop();
            pauseButton.setImageResource(R.drawable.green_run);
            pauseText.setText("Resume");
            stopButton.setVisibility(View.VISIBLE);
            paused=true;
        }
        else{
            chrono.setBase(SystemClock.elapsedRealtime()+chronoStop);
            chrono.start();
            pauseButton.setImageResource(R.drawable.red_run);
            pauseText.setText("Pause");
            stopButton.setVisibility(View.GONE);
            paused=false;
        }
    }

    public void stopWalkButton(View view){
        //turn off the gps tracking
        gps.stopUsingGPS();
        //record new activity in dog's list
        Activity newAct=new Activity("Walk",chrono.getText().toString(),new Date(),0.5);
        curDog.actList.add(newAct);
        //return to log
        Intent returnToLog=new Intent(this,activityLog.class);
        String jDog=gson.toJson(curDog);
        returnToLog.putExtra("jDog", jDog);
        startActivity(returnToLog);
    }

    //overrides the back button to do nothing
    @Override
    public void onBackPressed(){return;}
}
