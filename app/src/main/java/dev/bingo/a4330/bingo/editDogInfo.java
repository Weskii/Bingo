package dev.bingo.a4330.bingo;

//updates dog info


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class editDogInfo extends android.app.Activity implements View.OnClickListener {

    private EditText nameText;
    private Button updateBtn, deleteBtn;
    private EditText weightText;

    private long _id;

    private dogDBManager dogDBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Dog Info");

        setContentView(R.layout.activity_edit_dog);

        //opens database
        dogDBM = new dogDBManager(this);
        dogDBM.open();

        //sets up textboxes
        nameText = (EditText) findViewById(R.id.name_edittext);
        weightText = (EditText) findViewById(R.id.weight_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        //takes text in boxes, adds to intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String weight = intent.getStringExtra("weight");

        _id = Long.parseLong(id);

        nameText.setText(name);
        weightText.setText(weight);

        //sets up buttons
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if update button is pressed, updates information changed
            case R.id.btn_update:
                String title = nameText.getText().toString();
                String weight = weightText.getText().toString();

                dogDBM.update(_id, title, weight);
                this.returnHome();
                break;
            //if delete button, deletes the dog at that id #
            case R.id.btn_delete:
                dogDBM.delete(_id);
                this.returnHome();
                break;
        }
    }

    //goes back to dog list
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), dogList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
