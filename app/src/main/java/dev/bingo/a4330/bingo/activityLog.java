package dev.bingo.a4330.bingo;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class activityLog extends AppCompatActivity {
    Gson gson=new Gson();
    Dog curDog;
    float totalDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        final TextView txtDistance =findViewById(R.id.totalDistance);
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        totalDistance=0;
        //remove entries older than 7 days
        for(Activity act:curDog.actList){
            if(SystemClock.elapsedRealtime()-act.getDate().getTime()>604800000) curDog.actList.remove(act);
        }
        for(Activity act:curDog.actList){totalDistance+=act.getMiles();}
        txtDistance.setText(String.format("%.2fmi",totalDistance));
        ArrayAdapter<Activity> actListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,curDog.actList);
        ListView actListView=findViewById(R.id.actLog);
        actListView.setAdapter(actListAdapter);
    }

    public void trackWalkButtonPermissions(View view){
        //check for GPS and Internet permissions for location tracking
        if(!checkAllPermission()) checkPermissions();
        else trackWalkButton();
    }

    public void trackWalkButton(){
        Intent trackWalk = new Intent(this, trackWalk.class);
        String jDog = gson.toJson(curDog);
        trackWalk.putExtra("jDog", jDog);
        startActivity(trackWalk);
    }

    public void addActivity(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final LayoutInflater inflater=this.getLayoutInflater();
        final View inflateView=inflater.inflate(R.layout.other_activity_dialog,null);
        builder.setView(inflateView);
        builder.setTitle("Other Activity");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView actName=inflateView.findViewById(R.id.activityName);
                TextView actTime=inflateView.findViewById(R.id.activityTime);
                String actNameStr=actName.getText().toString();
                String actTimeStr=actTime.getText().toString();
                Date today=new Date();
                DateFormat df=new SimpleDateFormat("MMM dd");
                String todayString=df.format(today);
                curDog.actList.add(new Activity(actNameStr,actTimeStr,today,todayString));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.create();
        builder.show();
    }

    //=================
    //Permissions Block
    //=================
    //Checks to see if the user has accepted location permissions
    private void checkPermissions() {if (!checkAllPermission()) requestPermission();}
    //used in the default check permissions call, returns true if all permissions are already accepted
    public boolean checkAllPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
    //requests GPS and Internet permissions from the user for location tracking
    private void requestPermission() {
        String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
    }
    //after permissions are requested, this method checks to see if they were accepted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            //checks to see if each permission was granted individually
            boolean fineLocationPerm = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean internetPerm = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            //checks to see if all permissions were accepted. If so, show dialog. If not, display a message.
            if (fineLocationPerm && internetPerm) trackWalkButton();
            else Toast.makeText(this, "You must accept all permissions to track walks.", Toast.LENGTH_SHORT).show();
        }
    }

    //overrides the back button in order to return the user to the homescreen
    @Override
    public void onBackPressed()
    {moveTaskToBack(true);
    Intent backToHomescreen = new Intent(this,HomeScreen.class);
    String jDog=gson.toJson(curDog);
    backToHomescreen.putExtra("jDog", jDog);
    backToHomescreen.putExtra("sendingIntent","activityLog");
    this.startActivity(backToHomescreen);
    }
}
