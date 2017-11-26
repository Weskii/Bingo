package dev.bingo.a4330.bingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class newActive extends AppCompatActivity {

    public String aName = null;
    public static String aDate = null;
    public static String aTime = null;


    EditText inName = findViewById(R.id.activityName);
    EditText inDate = findViewById(R.id.activityDate);
    EditText inTime = findViewById(R.id.activityTime);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_active);
    }

    public void addNewActivity(View view) {
        //makes sure activity name and a date are input
        aName = inName.getText().toString();
        aDate = inDate.getText().toString();
        aTime = inTime.getText().toString();
        Activity.setActName(aName);
        Activity.setDate(aDate);
        Activity.setTime(aTime);


        if (aName != null && aDate != null) {
            Activity act = new Activity(aName, aTime, aDate);
        }
    }
}