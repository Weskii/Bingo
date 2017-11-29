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
import java.util.Date;

public class trackWalk extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST=9000;
    public static final String TAG=trackWalk.class.getSimpleName();
    Location prevLocation=null;
    private GoogleMap mMap;
    Chronometer chrono;
    Gson gson=new Gson();
    Dog curDog;
    ImageView pauseButton;
    View stopButton;
    TextView pauseText;
    boolean paused=false;
    double dMeters=0;
    double dMiles=0;
    long chronoStop=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_walk);
        setUpMapIfNeeded();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // Create the LocationRequest object
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(20 * 1000)        // 10 seconds, in milliseconds
                        .setFastestInterval(20 * 1000); // 1 second, in milliseconds
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
        //record new activity in dog's list
        dMiles=metersToMiles(dMeters);
        Activity newAct=new Activity("Walk",chrono.getText().toString(),new Date(),dMiles);
        curDog.actList.add(newAct);
        //return to log
        Intent returnToLog=new Intent(this,activityLog.class);
        String jDog=gson.toJson(curDog);
        returnToLog.putExtra("jDog", jDog);
        mGoogleApiClient.disconnect();
        startActivity(returnToLog);
    }

    @Override
    public void onConnected(Bundle bundle){
        Log.i(TAG, "Location services connected.");
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch(SecurityException e){
            //ignore exceptions because we've already checked for permissions
        }
    }

    private void handleNewLocation(Location location){
    }

    @Override
    public void onConnectionSuspended(int i){
        Log.i(TAG,"Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMapIfNeeded(){
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
    }

    //overrides the back button to do nothing
    @Override
    public void onBackPressed(){return;}

    @Override
    public void onLocationChanged(Location location){
        double currentLatitude=location.getLatitude();
        double currentLongitude=location.getLongitude();
        if(prevLocation==null){
            prevLocation=location;
        }
        else {
            dMeters = dMeters + location.distanceTo(prevLocation);
            prevLocation = location;
        }
        LatLng latLng=new LatLng(currentLatitude,currentLongitude);
        MarkerOptions options=new MarkerOptions().position(latLng);
        mMap.addMarker(options);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17.0f));
    }

    public double metersToMiles(double dM){
        double feet=dM*3.2808399;
        return feet/5280;
    }
}