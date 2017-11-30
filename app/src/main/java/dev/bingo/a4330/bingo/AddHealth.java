package dev.bingo.a4330.bingo;
//adds health entry to list
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddHealth extends Activity implements OnClickListener {

    private Button addHealth;
    private EditText nameEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText notesEditText;
    private healthDBManager hDBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Health Entry");

        setContentView(R.layout.activity_add_health);
        //text boxes for information
        nameEditText = (EditText) findViewById(R.id.name_edittext);
        dateEditText = (EditText) findViewById(R.id.date_edittext);
        timeEditText = (EditText) findViewById(R.id.time_edittext);
        notesEditText = (EditText) findViewById(R.id.notes_edittext);

        addHealth= (Button) findViewById(R.id.add_health_button);

        //opens health database
        hDBM = new healthDBManager(this);
        hDBM.open();

        //sets up add entry button
        addHealth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //if add button is clicked
            case R.id.add_health_button:

                //takes information in text boxes
                final String name = nameEditText.getText().toString();
                final String date = dateEditText.getText().toString();
                final String time = timeEditText.getText().toString();
                final String notes = notesEditText.getText().toString();

                //adds them to the databse
                hDBM.insert(name, date, time, notes);

                //returns to health list, adding the new item to it
                Intent main = new Intent(AddHealth.this, healthList.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}