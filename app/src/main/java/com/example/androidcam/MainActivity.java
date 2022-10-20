package com.example.androidcam;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.graphics.Matrix;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   private int REQUEST_CODE_PERMISSIONS = 101;
   private  String[] REQUIRED_PERMISSSION = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    TextureView textureView;
    StorageReference storageRef;
    public String algo = "CNN";
    File file1;
    long millis;
    String path;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        textureView=(TextureView) findViewById(R.id.view_Finder);

        if(allPermissionGranted()){

            startCamera();
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSSION, REQUEST_CODE_PERMISSIONS);
        }
   }

    private void startCamera() {
        CameraX.unbindAll();

        Rational aspectRatio = new Rational (textureView.getWidth(), textureView.getHeight());
        Size screen = new Size(textureView.getWidth(), textureView.getHeight());

        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

    preview.setOnPreviewOutputUpdateListener(
            new Preview.OnPreviewOutputUpdateListener() {
                @Override
                public void onUpdated(Preview.PreviewOutput output) {

                    ViewGroup parent = (ViewGroup) textureView.getParent();
                    parent.removeView(textureView);
                    parent.addView(textureView);

                    textureView.setSurfaceTexture(output.getSurfaceTexture());
                    updateTransform();
                }
            }
    );
        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);

        findViewById(R.id.imgCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millis = System.currentTimeMillis();
                path = "storage/emulated/0/"+ millis;
                 file1  = new File(path);
                imgCap.takePicture(file1, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Pic captured at " + file.getAbsolutePath();
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

                        //upload to firebase

                        if (algo == "FFNN") {

                            Map<String, Object> docData = new HashMap<>();
                            docData.put("stringExample", "Hello world!");
                            docData.put("booleanExample", true);
                            docData.put("numberExample", 3.14159265);
                            docData.put("dateExample", new Timestamp(new Date()));
                            docData.put("listExample", Arrays.asList(1, 2, 3));
                            docData.put("nullExample", null);

                            Map<String, Object> nestedData = new HashMap<>();
                            nestedData.put("a", 5);
                            nestedData.put("b", true);

                            docData.put("objectExample", nestedData);


                            db.collection("FFNN_download").document(String.valueOf(millis))
                                    .set(docData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                        }
                        if (algo == "CNN") {

                            Map<String, Object> docData = new HashMap<>();
                            docData.put("stringExample", "Hello world!");
                            docData.put("booleanExample", true);
                            docData.put("numberExample", 3.14159265);
                            docData.put("dateExample", new Timestamp(new Date()));
                            docData.put("listExample", Arrays.asList(1, 2, 3));
                            docData.put("nullExample", null);

                            Map<String, Object> nestedData = new HashMap<>();
                            nestedData.put("a", 5);
                            nestedData.put("b", true);

                            docData.put("objectExample", nestedData);


                            db.collection("CNN_download_log").document(String.valueOf(millis))
                                    .set(docData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                            Map<String, Object> entry = new HashMap<>();
                                            Uri file2 = Uri.fromFile(new File(path));
                                            entry.put("images",file2.getLastPathSegment());

                                            StorageReference storageRef = FirebaseStorage.getInstance().getReference(file2.getLastPathSegment());
                                           storageRef.putFile(file2);


                                            // upload
                                            //Uri.fromFile(new File("/sdcard/cats.jpg")

//                                            Log.d(TAG, path +  String.valueOf(millis));
//                                            StorageReference riversRef = storageRef.child(String.valueOf(millis));
//                                            UploadTask uploadTask = riversRef.putFile(file2);

//                                             Register observers to listen for when the download is done or if it fails


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });


                            // Add a new document with a generated ID


                            //wait for result
                            //set result to global variables
                            //open new activity and display
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Pic capture failed : " + message;
                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
                        if (cause != null) {
                            cause.printStackTrace();

                        }
                    }
                });
            }
        });
        CameraX.bindToLifecycle(this, preview,imgCap);
   }

    private void updateTransform() {
       Matrix mx = new Matrix();
       float w = textureView.getMeasuredWidth();
       float h = textureView.getMeasuredHeight();

       float cX = w / 2f;
       float cY = h / 2f;

       int rotationDgr;
       int rotation = (int)textureView.getRotation();

        switch (rotation){
            case Surface.ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }
        mx.postRotate((float)rotationDgr, cX, cY);
        textureView.setTransform(mx);
    }

    private boolean allPermissionGranted() {
       for(String permission: REQUIRED_PERMISSSION){
           if(ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED){
               return false;
           }
       }
       return true;
    }
}