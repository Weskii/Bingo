package dev.bingo.a4330.bingo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;




public class dogList extends AppCompatActivity {

    private dogDBManager dogDBM;
    private SimpleCursorAdapter adapter;
    private ListView dogListView;
    final String[] from = new String[]{
            DogDatabaseHelper._ID, DogDatabaseHelper.NAME, DogDatabaseHelper.WEIGHT
    };
    final int[] to = new int[]{R.id.dogId, R.id.dogName, R.id.dogWeight};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_list);

        dogDBM = new dogDBManager(this);
        dogDBM.open();
        Cursor cursor = dogDBM.fetch();


        //find list view, tie to the dog adapter
        dogListView = (ListView) findViewById(R.id.list_view);
        dogListView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.layoutdog, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        dogListView.setAdapter(adapter);

        dogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView nameTextView = (TextView) view.findViewById(R.id.dogName);
                TextView weightTextView = (TextView) view.findViewById(R.id.dogWeight);
                TextView IDTextView = (TextView) view.findViewById(R.id.dogId);


                String name = nameTextView.getText().toString();
                String weight = weightTextView.getText().toString();
                String id = IDTextView.getText().toString();

                Intent dogIntent = new Intent(getApplicationContext(), editDogInfo.class);
                dogIntent.putExtra("name", name);
                dogIntent.putExtra("weight", weight);
                dogIntent.putExtra("id", id);

                startActivity(dogIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.d_menu, menu);
        return true;
    }

    // @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id== R.id.add_dog){
            Intent add_mem = new Intent(this, AddDog.class);
            startActivity(add_mem);
        }
        return super.onOptionsItemSelected(item);
    }
}
