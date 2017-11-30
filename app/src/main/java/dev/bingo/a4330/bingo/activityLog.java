package dev.bingo.a4330.bingo;

import android.Manifest;
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
import java.util.Date;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class activityLog extends AppCompatActivity {
    //The Gson library is how I pass dog objects between activities
    Gson gson=new Gson();
    Dog curDog;
    //The totalDistance is what is displayed in the text view underneath the activity buttons
    float totalDistance=0;

    //The onCreate method runs when the activity starts.
    //I have it cull activities older than 7 days, populate the list view with those activities,
    //and display the summation of distances from all logged walks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        //Getting a handle to the total distance text view
        final TextView txtDistance =findViewById(R.id.totalDistance);
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        //Routine to remove entries older than 7 days
        for(Activity act:curDog.actList){
            if(SystemClock.elapsedRealtime()-act.getDate().getTime()>604800000) curDog.actList.remove(act);
        }
        //Calculating and setting the total distance
        for(Activity act:curDog.actList){totalDistance+=act.getMiles();}
        txtDistance.setText(String.format("%.2fmi",totalDistance));
        //And array adapter to populate the list view with activities
        ArrayAdapter<Activity> actListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,curDog.actList);
        ListView actListView=findViewById(R.id.actLog);
        actListView.setAdapter(actListAdapter);
    }

    //This method simply passes the current dog object to the trackWalk activity and starts it
    public void trackWalkButton(){
        Intent trackWalk = new Intent(this, trackWalk.class);
        String jDog = gson.toJson(curDog);
        trackWalk.putExtra("jDog", jDog);
        startActivity(trackWalk);
    }

    //The addActivity method is for adding non-walking activities. It builds and creates an Android
    //alert dialog that prompts the user for what they did and how long they did it for. Both pieces
    //of data are then constructed into a new Activity and added to the current dog's list.
    //When the dialog is dismissed, onCreate is run again, and the list rebuilds itself there.
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
        //I give the user a button to cancel the adding of an alternative activity
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {/*the cancel button simply does nothing*/}
        });
        builder.create();
        builder.show();
    }

    //=================
    //Permissions Block
    //=================
    //I check for the permissions I need before I allow the user to touch the trackWalk page
    //This entire subroutine was designed and originally implemented by Megan for her gallery

    //This is the method called when the "Track a walk" button is pressed. It starts by checking
    //all of my required permissions to see if they pass. If any fail, it runs the request permissions,
    //subroutine. Otherwise, the app simply calls the trackWalkButton method and begins the trackWalk activity.
    public void trackWalkButtonPermissions(View view){
        if(!checkAllPermission()) requestPermission();
        else trackWalkButton();
    }

    //I check for the required permissions and a return a simple boolean to tell me if any fail
    //I require access to ACCESS_FINE_LOCATION for GPS-enabled tracking and INTERNET for Google's
    //low-power location tracking based on WiFi networks
    public boolean checkAllPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
    //This method requests the required permissions using Android's pre-built permissions manager
    //Note, the requested permissions have to be declared in the manifest file before I can check them
    private void requestPermission() {
        String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
        ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
    }

    //After the permissions are requested, this method checks to see if they were accepted; a user can
    //deny the requested permissions, but they will never be allowed into the trackWalk activity if they do.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            //Check to see if each permission was granted individually
            boolean fineLocationPerm = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean internetPerm = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            //Check to see if all permissions were accepted. If so, run trackWalk normally.
            // If not, display a message to the user
            if (fineLocationPerm && internetPerm) trackWalkButton();
            else Toast.makeText(this, "You must accept all permissions to track walks.", Toast.LENGTH_SHORT).show();
        }
    }

    //Overrides the back button to return the user to the home screen instead of cycling through
    //previously-called instances of the trackWalk activity
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