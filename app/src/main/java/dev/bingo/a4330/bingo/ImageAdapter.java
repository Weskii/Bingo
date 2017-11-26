package dev.bingo.a4330.bingo;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import javax.security.auth.callback.Callback;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
{

    private static File imagesFile;
    View ImageView;
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

        // Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
       Bitmap imageBitmap = BitmapFactory.decodeStream(is, null, options);
       holder.getImageView().setImageBitmap(imageBitmap);

        //a listener that returns the image's filepath provided the callback is not null
        holder.getImageView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (myCallback!=null)
                {
                    myCallback.onImageClicked(imageFile.getAbsolutePath());
                }
            }
        });


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