package dev.bingo.a4330.bingo;

/**
 * Created by Laila on 11/26/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class dogList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_list);

        ListView doglist = (ListView) findViewById(R.id.dogListView);

        Dog[] doggos = {
                new Dog("spot", "82"),
                new Dog("dogger", "12"),
                new Dog("qit", "43"),
                new Dog("elton", "11"),
                new Dog("zeus", "1"),
        };
        ArrayAdapter<Dog> dogArrayAdapter = new ArrayAdapter<Dog>(this, android.R.layout.simple_list_item_1, doggos);
        doglist.setAdapter(dogArrayAdapter);
    }
}
