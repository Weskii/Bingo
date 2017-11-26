package dev.bingo.a4330.bingo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;



public class GalleryHomepage extends AppCompatActivity implements ImageAdapter.Callback
{
    private Button addPhotosButton;
    private int GALLERY = 1,
            CAMERAINTENT = 0;
    final int RequestPermissionCode = 0;
    private String galleryLocation = "BingoAppGallery";
    public File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    public File BingoGalleryFolder = new File(storageDirectory,galleryLocation);
    private RecyclerView recycleView;
    String currentPath ="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryhomepage);

        //identifies button to add photos and sets the button to check that the user has granted..
        // permission to use camera and access photos when clicked, before calling the camera or accessing photos.
        addPhotosButton = findViewById(R.id.addButton);
        addPhotosButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkPermissions();
            }
        });


        //sets up the recyclerview grid and calls the adapter so the images in BingoAppGallery are read
        recycleView = findViewById(R.id.galleryRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recycleView.setLayoutManager(layoutManager);
        RecyclerView.Adapter imageAdapter = new ImageAdapter(BingoGalleryFolder,this);
        recycleView.setAdapter(imageAdapter);
    }

    private void showDialog()
    {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    /*Checks to see if the user has accepted camera and read/write permissions, if so the dialog window prompting users to choose a photo or take a photo is
      shown, if not then the app requests permissions */
    private void checkPermissions()
    {
        if (!checkAllPermission())
            requestPermission();
        else
            showDialog();

    }

    // requests read,write, and camera permissions from the user.
    private void requestPermission()
    {
        String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, PERMISSIONS, RequestPermissionCode);

    }

    /* after permissions are requested, this method checks to see if they were accepted. If so the dialog window prompting users to choose or take a photo is shown,
    otherwise, a message is displayed. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /*switch that reads the request code and responds providing it is equal to the requestpermissioncode, if there has been an error and the requestpermission code is incorrect
        the switch exits */
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0)
                {
                    //checks to see if each permission was granted individually
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadWriteStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;



                    //checks to see if all permissions were accepted. If so, show dialog. If not, display a message.
                    if (CameraPermission && ReadExternalStatePermission && ReadWriteStatePermission)
                        showDialog();
                    else
                        Toast.makeText(this, "You must accept all permissions to add photos to the gallery.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //used in the default check permissions call, returns true if all permissions are already accepted when the user clicks "Add Image".
    public boolean checkAllPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED && ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    //handles the case in the dialog switch in which the user opts to import a photo from the gallery. An intent is created, and the gallery requestcode is sent to startActivityForResult.
    public void choosePhotoFromGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    //handles the case in the dialog switch in which the user opts to take a photo.
    private void takePhotoFromCamera()
    {
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String authority = getApplicationContext().getPackageName()+ ".fileprovider";
        Uri imageUri = FileProvider.getUriForFile(this,authority,photoFile);
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        MediaScannerConnection.scanFile(this, new String[] { photoFile.getPath() }, new String[] { "image/jpeg" }, null);

        startActivityForResult(callCameraApplicationIntent, CAMERAINTENT);

    }

    //if the user imported a photo from the gallery finds bitmap from an imported picture and calls the method to save gallery photos. A message is displayed if the method was successful or failed.
    //if a photo was taken from the camera, a message is displayed saying the image was saved.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //if the user hits back or does not accept the photo preview before taking a photo, the blank photo that was saved after taking the photo is deleted
        if (resultCode == this.RESULT_CANCELED)
        {
            if(requestCode==CAMERAINTENT)
            {
                File fdelete = new File(currentPath);
                if (fdelete.exists())
                {
                    fdelete.delete();
                }
                MediaScannerConnection.scanFile(this, new String[] { fdelete.getPath() }, new String[] { "image/jpeg" }, null);
            }
            //if the user cancels before importing a photo, the method exits without doing anything.
            else
                return;
            return;
        }
        //if the user tried to import a photo from the gallery, the image is saved and the user is notified whether or not the save was successful
        if (requestCode == GALLERY) {
            if (data != null) {
                //bitmap is read from the Uri and passed to the saveGalleryImage method. The IOException in case of failure is handled for.
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveGalleryImage(bitmap);
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }

            }
            //the user is notified the image was saved.
        } else if (requestCode == CAMERAINTENT && resultCode == RESULT_OK)
        {

            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();

        }

        //recyclerview updates to include the new photo.
        RecyclerView.Adapter newImageAdapter = new ImageAdapter(BingoGalleryFolder, this);
        recycleView.swapAdapter(newImageAdapter, false);
    }


    //creates image files when they are taken using a camera.
    File createImageFile() throws IOException {

        //creates an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //checks to see if gallery directory exists, creates it if null
        BingoGalleryFolder = new File(storageDirectory,galleryLocation);
        if(!BingoGalleryFolder.exists())
            BingoGalleryFolder.mkdirs();

        File cameraImage = File.createTempFile(imageFileName,".jpg", BingoGalleryFolder);
        currentPath = cameraImage.getAbsolutePath();
        return cameraImage;
    }

    public String saveGalleryImage(Bitmap myBitmap)
    {

        //compresses the image so it is not as large, in case the user has a very high resolution photo stored on their phone
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        //checks to see if gallery directory exists, creates it if null
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        BingoGalleryFolder = new File(storageDirectory,galleryLocation);
        if(!BingoGalleryFolder.exists())
            BingoGalleryFolder.mkdirs();

        try {
            //new file is named, created and stored in the directory.
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "IMAGE_" + timeStamp + "_";
            File f = new File(BingoGalleryFolder, imageFileName + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            //Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            fo.close();
            return f.getAbsolutePath();
        }
        //handles for failure to save image
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //if failure, there is no filepath to return so a an empty string is returned
        return "";
    }

    //Starts a new activity to display a larger version of an image clicked on in the recyclerview.
    @Override
    public void onImageClicked(String imagePath)
    {
        Intent viewPictureLarger = new Intent(this,ViewPhotoActivity.class);
        viewPictureLarger.putExtra("filepath",imagePath);
        viewPictureLarger.setFlags(viewPictureLarger.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(viewPictureLarger);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        //needed to override this method to implement the Callback class, but onPointerCaptureChanged is not used, so it was left as is.

    }
    //overrides the back button in order to return the user to the homescreen
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
        Intent backToHomescreen = new Intent(this,HomeScreen.class);
        this.startActivity(backToHomescreen);
    }
}
