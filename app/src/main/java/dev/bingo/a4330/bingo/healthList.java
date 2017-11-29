package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



public class healthList extends AppCompatActivity {

    private healthDBManager hDBM;
    private SimpleCursorAdapter adapter;
    private ListView hListView;
    final String[] from = new String[]{
            healthDatabaseHelper._ID, healthDatabaseHelper.NAME, healthDatabaseHelper.DATE, healthDatabaseHelper.TIME, healthDatabaseHelper.NOTES
    };
    final int[] to = new int[]{R.id.healthId, R.id.healthName, R.id.healthDate, R.id.healthTime, R.id.healthNotes};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_list);

        hDBM = new healthDBManager(this);
        hDBM.open();
        Cursor cursor = hDBM.fetch();


        //find list view, tie to the dog adapter
        hListView = (ListView) findViewById(R.id.list_view);
        hListView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.healthlayout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        hListView.setAdapter(adapter);

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView nameTextView = (TextView) view.findViewById(R.id.healthName);
                TextView dateTextView = (TextView) view.findViewById(R.id.healthDate);
                TextView timeTextView = (TextView) view.findViewById(R.id.healthTime);
                TextView notesTextView = (TextView) view.findViewById(R.id.healthNotes);
                TextView IDTextView = (TextView) view.findViewById(R.id.healthId);


                String name = nameTextView.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();
                String notes = notesTextView.getText().toString();
                String id = IDTextView.getText().toString();

                Intent healthIntent = new Intent(getApplicationContext(), editHealthInfo.class);
                healthIntent.putExtra("name", name);
                healthIntent.putExtra("date", date);
                healthIntent.putExtra("time", time);
                healthIntent.putExtra("notes", notes);
                healthIntent.putExtra("id", id);

                startActivity(healthIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.h_menu, menu);
        return true;
    }

   // @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id== R.id.add_health){
            Intent add_mem = new Intent(this, AddHealth.class);
            startActivity(add_mem);
        }
        return super.onOptionsItemSelected(item);
    }
}
