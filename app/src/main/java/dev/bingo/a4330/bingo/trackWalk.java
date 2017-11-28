package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;

public class trackWalk extends AppCompatActivity {
    GPSTracker gps=new GPSTracker(this);
    Gson gson=new Gson();
    Dog curDog;
    ImageView pauseButton=(ImageView) findViewById(R.id.pauseButton);
    ArrayList<Activity> actList;
    TextView pauseText=(TextView) findViewById(R.id.pauseText);
    boolean paused=false;
    double distance=0;
    int timeSec=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_walk);
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        actList=curDog.getActList();
    }

    public void pauseWalkButton(){
        if(!paused) {
            pauseButton.setImageResource(R.drawable.green_run);
            pauseText.setText("Resume");
            paused=true;
        }
        else{
            pauseButton.setImageResource(R.drawable.red_run);
            pauseText.setText("Pause");
            paused=false;
        }
    }

    public void stopWalkButton(){
        //turn off the gps tracking
        gps.stopUsingGPS();
        //record new activity in dog's list
        actList.add(new Activity("Walk",String.valueOf(timeSec),new Date(),distance));
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
