package dev.bingo.a4330.bingo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddDog extends android.app.Activity implements View.OnClickListener {

    private Button addPet;
    private EditText nameEditText;
    private EditText weightEditText;

    private dogDBManager dogDBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Dog");

        setContentView(R.layout.activity_add_dog);

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        weightEditText = (EditText) findViewById(R.id.weight_edittext);

        addPet= (Button) findViewById(R.id.add_dog_button);

        dogDBM = new dogDBManager(this);
        dogDBM.open();
        addPet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_dog_button:

                final String name = nameEditText.getText().toString();
                final String weight = weightEditText.getText().toString();

                dogDBM.insert(name, weight);

                Intent main = new Intent(AddDog.this, dogList.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}