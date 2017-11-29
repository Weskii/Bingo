package dev.bingo.a4330.bingo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.security.auth.callback.Callback;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
{


    private static File imagesFile;
    //an interface used to create the onclick listener for the recyclerview
    public interface Callback
    {
        void onImageClicked(String imagePath);
    }
    //sets the initial values of the imagesfile and callback for use in this class when an adapter is called
    Callback myCallback;
    public ImageAdapter(File folderFile, Callback callback)
    {
        imagesFile = folderFile;
        myCallback = callback;

    }

    //dictates what layout should be used for each item in the recyclerview, creates new ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewimages,parent,false);
        return new ViewHolder(view);

    }

    //sets the image view from bitmap
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //gets image in the directory according to it's position in the recyclerview and sets the image to display
        final File imageFile = imagesFile.listFiles()[position];

        //reduces the size of the images so the app does not run out of memory displaying multiple images
        BitmapFactory.Options options = new BitmapFactory.Options();
        FileInputStream is = null;
        try
        {
            is = new FileInputStream(imageFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        options.inSampleSize = 8;

       Bitmap imageBitmap = BitmapFactory.decodeStream(is, null, options);

       //calls an exif interface to display the correct orientation of the image based on Exif data
        try {
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
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
            holder.getImageView().setImageBitmap(rotatedBitmap);




        } catch (IOException e)
        {
            e.printStackTrace();
        }







        //a listener that returns the image's filepath provided the callback is not null
        holder.getImageView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (myCallback!=null)
                {
                    myCallback.onImageClicked(imageFile.getAbsolutePath());
                }
            }
        });


    }




    //method for rotating bitmap, returning the rotated bitmap.

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }



    //returns the amount of items in the file if the file is not null.
    @Override
    public int getItemCount()
    {
        try
        {
            return imagesFile.listFiles().length;
        }
        catch (NullPointerException e)
        {
            return 0;
        }

    }

    //A class that helps to handle views
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;

        //sets the imageview to the view contained in the recyclerviewimages.xml
        public ViewHolder(View view)
        {
            super(view);
            imageView = view.findViewById(R.id.imageGalleryView);

        }
        //returns the imageview for use in the onBindViewHolder
        public ImageView getImageView()
        {
            return imageView;
        }
    }



}