package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;

public class HomeScreen extends AppCompatActivity {
    Dog curDog;
    Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String jDog=getIntent().getStringExtra("jDog");
        curDog=gson.fromJson(jDog, Dog.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void activityButton(View view) {
        Intent activityLog = new Intent(this, activityLog.class);
        String jDog=gson.toJson(curDog);
        activityLog.putExtra("jDog", jDog);
        startActivity(activityLog);
    }

    public void cameraButton(View view) {
        Intent gallery = new Intent(this, GalleryHomepage.class);
        startActivity(gallery);
    }


}
