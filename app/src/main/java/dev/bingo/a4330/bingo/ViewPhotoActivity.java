package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        //compresses the bitmap before being shown so it
        BitmapFactory.Options options = new BitmapFactory.Options();
        FileInputStream is = null;
        File imageFile = new File(path);
        try
        {
            is = new FileInputStream(imageFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        options.inSampleSize = 2;

        // Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Bitmap imageBitmap = BitmapFactory.decodeStream(is, null, options);
        //Bitmap b = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(imageBitmap);



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

