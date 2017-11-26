package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void activityButton(View view) {
        Intent activityLog = new Intent(this, activityLog.class);
        startActivity(activityLog);
    }

    public void cameraButton(View view) {
        Intent gallery = new Intent(this, GalleryHomepage.class);
        startActivity(gallery);
    }


}
