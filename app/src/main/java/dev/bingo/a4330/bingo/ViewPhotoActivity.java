package dev.bingo.a4330.bingo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViewPhotoActivity extends AppCompatActivity {
    private Button ChangeProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewphoto);

        //gets the image path passed in the intent bundle, extracts the bitmap and sets the image to display in the view
        Intent intent = getIntent();
        String path = intent.getStringExtra("filepath");
        final ImageView imageView = (ImageView) findViewById(R.id.ZoomView);
        //compresses the bitmap before being shown so it is not too large
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
        Bitmap imageBitmap = BitmapFactory.decodeStream(is, null, options);


         //rotates the image to the correct
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = ImageAdapter.rotateImage(imageBitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = ImageAdapter.rotateImage(imageBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = ImageAdapter.rotateImage(imageBitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = imageBitmap;

            }
            imageView.setImageBitmap(rotatedBitmap);


        } catch (IOException e)
        {
            e.printStackTrace();
        }




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

