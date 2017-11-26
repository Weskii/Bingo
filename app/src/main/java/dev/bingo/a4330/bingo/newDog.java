package dev.bingo.a4330.bingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class newDog extends AppCompatActivity {

    //instantiates each value for a dog as null
    public static String iName = null;
    public static String iWeight = null;

    //creates edit text views for each variable
    EditText inName = findViewById(R.id.petName);
    EditText inWeight = findViewById(R.id.petWeight);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog);
    }

    public void addNewPet(View view) {
        //takes input from fields. if nothing is input, should be null
        iName = inName.getText().toString();
        iWeight = inWeight.getText().toString();
        //sets each value for a new appointment as respective input
        Dog.setName(iName);
        Dog.setWeight(iWeight);
        //makes sure at least dog name is input
        if (iName != null) {
            Dog dog = new Dog(iName, iWeight);
        }
        //otherwise, throw an error to input a name
    }
}
