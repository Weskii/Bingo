package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//All of these implements contribute to the Location API and let me control
//specific behaviors of the location tracking feature
public class trackWalk extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    //I need a lot of local variables initialized here so they work between functions
    //These are for the location tracking
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST=9000;
    Location prevLocation=null;
    private GoogleMap mMap;
    //This chronometer is my stopwatch
    Chronometer chrono;
    //The Gson library is how I pass dog objects between activities
    Gson gson=new Gson();
    Dog curDog;
    //These give me handles to my buttons and text so I can change them dynamically
    ImageView pauseButton;
    View stopButton;
    TextView pauseText;
    //Some basic numbers that I use in my functions
    boolean paused=false;
    double dMeters=0;
    double dMiles=0;
    long chronoStop=0;

    //The onCreate method runs when the activity starts.
    //I have it display the map, add an initial marker at the user's location,
    //and begin the location tracking and the stopwatch at the same time
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_walk);
        //Set up the map
        setUpMapIfNeeded();
        //Create a handle to the locations API
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // Create the LocationRequest object to provide location updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(20 * 1000) // 20 seconds between updates
                        .setFastestInterval(20 * 1000); //20 seconds between updates
        //Link the button and text handles made above to objects in my XML layout
        chrono=findViewById(R.id.chrono);
        pauseButton=findViewById(R.id.pauseButton);
        stopButton=findViewById(R.id.stopButton);
        pauseText=findViewById(R.id.pauseText);
        //Import the dog object passed from the activityLog
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        stopButton.setVisibility(View.GONE);
        //Start the stopwatch
        chrono.start();
    }

    //This method lets me define what happens when the pause button is pressed.
    //I use an if statement and a simple boolean to split the behavior in half
    public void pauseWalkButton(View view){
        //If the app is not currently paused, the timer is stopped and rebased so it
        //doesn't track incorrectly. I also change the button to green and the text
        //to "resume" to act as a resume button.
        if(!paused) {
            chronoStop=chrono.getBase()-SystemClock.elapsedRealtime();
            chrono.stop();
            pauseButton.setImageResource(R.drawable.green_run);
            pauseText.setText("Resume");
            stopButton.setVisibility(View.VISIBLE);
            paused=true;
        }
        //If the app is currently paused, pressing resume resets the text to "Pause" and the
        //button goes back to being red. The stopwatch is restarted, as well.
        else{
            chrono.setBase(SystemClock.elapsedRealtime()+chronoStop);
            chrono.start();
            pauseButton.setImageResource(R.drawable.red_run);
            pauseText.setText("Pause");
            stopButton.setVisibility(View.GONE);
            paused=false;
        }
        //I don't need to stop and restart the location tracking, because by default it has a
        //minimum distance of 10 meters to update the location. If the user is pausing a walk,
        //I assume they aren't moving.
    }

    //The stopWalk method is how I return to the log and record the walk as a new activity
    public void stopWalkButton(View view){
        //The location tracker records distances in meters, so I have to convert that to miles
        dMiles=metersToMiles(dMeters);
        //The Activity class takes the Java Date class in its constructor as well as a string
        //representation of that class. It was easier to format the string outside of the class
        //like this and just pass it into the constructor.
        Date today=new Date();
        DateFormat df=new SimpleDateFormat("MMM dd");
        String todayString=df.format(today);
        Activity newAct=new Activity("Walk",chrono.getText().toString(),today,todayString,dMiles);
        //The activity list is stored as an array list in the Dog class itself, which I made public for
        //easy access and adding
        curDog.actList.add(newAct);
        //Intent to return to the log and pass the dog object back again
        Intent returnToLog=new Intent(this,activityLog.class);
        String jDog=gson.toJson(curDog);
        returnToLog.putExtra("jDog", jDog);
        mGoogleApiClient.disconnect();
        startActivity(returnToLog);
    }

    //A helper method I wrote to convert meters into miles because the Location API defaults to meters
    public double metersToMiles(double dM){
        double feet=dM*3.2808399;
        return feet/5280;
    }

    //This is a helper method that forces the map fragment to render the correct Google map if it hasn't
    //already been linked. I call this in onCreate to force it to render when the tracking starts.
    private void setUpMapIfNeeded(){
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }
    }

    //The onConnected method is inherited from the GoogleApiClient.ConnectionCallbacks class
    //This overrides the default behavior to only begin updating the location with the
    //LocationRequest object I defined in the onCreate method
    @Override
    public void onConnected(Bundle bundle){
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch(SecurityException e){
            //ignore exceptions because we've already checked for location permissions in the activity log
        }
    }

    //The onConnectionSuspended method is inherited from the GoogleApiClient.ConnectionCallbacks class
    //This overrides the default behavior to do nothing since I require the screen to remain on while the
    //walk is tracked
    @Override
    public void onConnectionSuspended(int i){/*do nothing*/}

    //The onConnectionFailed method is inherited from the GoogleApiClient.OnConnectionFailedListener class
    //This overrides the default behavior to take the user to their GPS settings so they can enable them
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    //The onResume method is inherited from the fragment class that I use to display the map
    //This overrides the default behavior to force the map to redraw if needed and the location API
    //to reconnect, both of which happen if the activity is unexpectedly suspended and then resumed,
    //such as in the case of a phone call.
    @Override
    protected void onResume(){
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    //The onPause method is inherited from the fragment class that I use to display the map
    //Similar to above, this pauses location tracking when the app is unexpectedly suspended.
    //This saves on power consumption and eliminates unnecessary location checks.
    @Override
    protected void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    //The onMapReady method is inherited from the OnMapReadyCallback class
    //The only thing I need to do here is tie the Google map object I manipulate to the one passed
    //by the map fragment
    @Override
    public void onMapReady(GoogleMap googleMap){mMap = googleMap;}

    //The onLocationChanged method is inherited from the LocationListener class
    //This is where everything important happens. The listener updates according to the parameters I
    //passed on lines 75-78. This method is then called every time it updates, and it does a few things:
    //1. Keeps track of a current and previous position for distance calculation
    //2. Calculates that distance and adds it to the total distance
    //3. Creates a new Location object and adds a map marker based on its coordinates
    //4. Centers the newest marker in the map display
    @Override
    public void onLocationChanged(Location location){
        double currentLatitude=location.getLatitude();
        double currentLongitude=location.getLongitude();
        //Step 1
        if(prevLocation==null){
            prevLocation=location;
        }
        else {
            //Step 2
            dMeters = dMeters + location.distanceTo(prevLocation);
            prevLocation = location;
        }
        //Step 3
        LatLng latLng=new LatLng(currentLatitude,currentLongitude);
        MarkerOptions options=new MarkerOptions().position(latLng);
        mMap.addMarker(options);
        //Step 4
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17.0f));
    }

    //Overrides the hardware back button to do nothing, which would otherwise break my location tracker
    @Override
    public void onBackPressed(){return;}
}