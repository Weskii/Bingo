package dev.bingo.a4330.bingo;
//edits health entries
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class editHealthInfo extends Activity implements OnClickListener {

    private EditText nameText, dateText, timeText, notesText;
    private Button updateBtn, deleteBtn;

    private long _id;

    private healthDBManager hDBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Health Entry");

        setContentView(R.layout.activity_edit_health);

        //opens database
        hDBM = new healthDBManager(this);
        hDBM.open();

        //sets up textboxes
        nameText = (EditText) findViewById(R.id.name_edittext);
        dateText = (EditText) findViewById(R.id.date_edittext);
        timeText = (EditText) findViewById(R.id.time_edittext);
        notesText = (EditText) findViewById(R.id.notes_edittext);

        //sets up buttons
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        //creates intent from text in boxes
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String notes = intent.getStringExtra("notes");

        _id = Long.parseLong(id);

        nameText.setText(name);
        dateText.setText(date);
        timeText.setText(time);
        notesText.setText(notes);

        //sets up buttons
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if update button, adds any changes made
            case R.id.btn_update:
                String name = nameText.getText().toString();
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();
                String notes = notesText.getText().toString();

                hDBM.update(_id, name, date, time, notes);
                this.returnHome();
                break;
            //if delete button, gets rid of entry at that ID
            case R.id.btn_delete:
                hDBM.delete(_id);
                this.returnHome();
                break;
        }
    }

    //goes back to home list
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(),healthList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
