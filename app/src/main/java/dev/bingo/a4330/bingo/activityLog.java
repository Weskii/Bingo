package dev.bingo.a4330.bingo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class activityLog extends AppCompatActivity {

    final int RequestPermissionCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    }

    public void trackWalkButton(View view){
        //check for GPS and Internet permissions for location tracking
        checkPermissions();
        Intent trackWalk = new Intent(this, activityLog.class);
        startActivity(trackWalk);
    }

    /*Checks to see if the user has accepted camera and read/write permissions, if so the dialog window prompting users to choose a photo or take a photo is
          shown, if not then the app requests permissions */
    private void checkPermissions() {
        if (!checkAllPermission()) requestPermission();
    }

    //used in the default check permissions call, returns true if all permissions are already accepted when the user clicks "Add Image".
    public boolean checkAllPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    // requests GPS and Internet permissions from the user for location tracking
    private void requestPermission() {
        String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
        ActivityCompat.requestPermissions(this, PERMISSIONS, RequestPermissionCode);

    }

    /* after permissions are requested, this method checks to see if they were accepted. If so the dialog window prompting users to choose or take a photo is shown,
    otherwise, a message is displayed. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0)
        {
            //checks to see if each permission was granted individually
            boolean fineLocationPerm = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean internetPerm = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            //checks to see if all permissions were accepted. If so, show dialog. If not, display a message.
            if (!fineLocationPerm && !internetPerm) Toast.makeText(this, "You must accept all permissions to track walks.", Toast.LENGTH_SHORT).show();
        }
    }
}
