package dev.bingo.a4330.bingo;


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

        dogDBM = new dogDBManager(this);
        dogDBM.open();

        nameText = (EditText) findViewById(R.id.name_edittext);
        weightText = (EditText) findViewById(R.id.weight_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String weight = intent.getStringExtra("weight");

        _id = Long.parseLong(id);

        nameText.setText(name);
        weightText.setText(weight);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String title = nameText.getText().toString();
                String desc = weightText.getText().toString();

                dogDBM.update(_id, title, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dogDBM.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), dogList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
