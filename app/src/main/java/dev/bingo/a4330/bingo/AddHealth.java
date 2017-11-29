package dev.bingo.a4330.bingo;

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

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        dateEditText = (EditText) findViewById(R.id.date_edittext);
        timeEditText = (EditText) findViewById(R.id.time_edittext);
        notesEditText = (EditText) findViewById(R.id.notes_edittext);

        addHealth= (Button) findViewById(R.id.add_health_button);

        hDBM = new healthDBManager(this);
        hDBM.open();
        addHealth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_health_button:

                final String name = nameEditText.getText().toString();
                final String date = dateEditText.getText().toString();
                final String time = timeEditText.getText().toString();
                final String notes = notesEditText.getText().toString();

                hDBM.insert(name, date, time, notes);

                Intent main = new Intent(AddHealth.this, healthList.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}