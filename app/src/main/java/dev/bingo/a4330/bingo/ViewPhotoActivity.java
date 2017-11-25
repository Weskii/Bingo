package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

public class ViewPhotoActivity extends AppCompatActivity {
    private Button ChangeProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewphoto);

        //gets the imagepath passed in the intent bundle, extracts the bitmap and sets the image to display in the view
        Intent intent = getIntent();
        String path = intent.getStringExtra("filepath");
        final ImageView imageView = (ImageView) findViewById(R.id.ZoomView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));


        ChangeProfileButton = findViewById(R.id.profileButton);
        ChangeProfileButton.setOnClickListener(new View.OnClickListener() {
            //Changes the user's profile photo when the button is clicked, and returns the user to the gallery homescreen
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent(v.getContext(),GalleryHomepage.class);
                v.getContext().startActivity(returnIntent);

            }
        });

    }


}

