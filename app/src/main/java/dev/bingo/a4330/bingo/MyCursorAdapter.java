/*package dev.bingo.a4330.bingo;

//custom cursor class to add radio buttons to set active dog
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{

    public MyCursorAdapter(Context context, Cursor c){
        super(context, R.layout.layoutdog, c);
    }
    @Override
    public View newView(Context context, Cursor c, ViewGroup parent){
        LayoutInflater li = LayoutInflater.from(context);
        return li.inflate(R.layout.layoutdog, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor c){
        TextView name = (TextView)view.findViewById(R.id.dogName);
        TextView weight = (TextView)view.findViewById(R.id.dogWeight);
        TextView id = (TextView)view.findViewById(R.id.dogId);
        ImageView profPic = (ImageView)view.findViewById(R.id.profPic);
        final RadioButton active = (RadioButton)view.findViewById(R.id.activeButton);

        name.setText(c.getString(c.getColumnIndex(DogDatabaseHelper.NAME)));
        weight.setText(c.getString(c.getColumnIndex(DogDatabaseHelper.WEIGHT)));
        id.setText(c.getString(c.getColumnIndex(DogDatabaseHelper._ID)));
       // active.setOnClickListener();

        private View.OnClickListener activeClicked = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                active.setChecked(true);

            }
        };
    }
}*/
