package dev.bingo.a4330.bingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewAppointment extends AppCompatActivity {

    //instantiates each value for an appointment as null
    public static String vName = null;
    public static String vDate = null;
    public static String vTime = null;
    public static String vNotes = null;

    //creates edit text views for each variable
    EditText inName = findViewById(R.id.appointName);
    EditText inDate = findViewById(R.id.appointDate);
    EditText inTime = findViewById(R.id.appointTime);
    EditText inNotes = findViewById(R.id.appointNotes);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_active);
    }

    public void addNewAppointment(View view) {
        //takes input from fields. if nothing is input, should be null
        vName = inName.getText().toString();
        vName = inDate.getText().toString();
        vName = inTime.getText().toString();
        vName = inNotes.getText().toString();
        //sets each value for a new appointment as respective input
        VetVisit.setAppName(vName);
        VetVisit.setDate(vDate);
        VetVisit.setTime(vTime);
        VetVisit.setNotes(vNotes);

        //makes sure activity name and a date are input
        if (vName != null && vDate != null) {
            VetVisit app = new VetVisit(vName, vDate, vNotes, vTime);
        }
    }
}