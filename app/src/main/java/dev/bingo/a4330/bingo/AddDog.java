package dev.bingo.a4330.bingo;
//add dog to list

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

        //opens database
        dogDBM = new dogDBManager(this);
        dogDBM.open();
        //creates button to add the pet to the database/list
        addPet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if the add dog button is pressed
            case R.id.add_dog_button:

                //take name and weight
                final String name = nameEditText.getText().toString();
                final String weight = weightEditText.getText().toString();

                //add them to the database
                dogDBM.insert(name, weight);

                //go back to dog list screen, show them in the list
                Intent main = new Intent(AddDog.this, dogList.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
        }
    }

}