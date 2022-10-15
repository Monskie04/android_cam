package com.example.androidcam;

import android.app.Activity;
import android.content.pm.PackageManager;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

   private int REQUEST_CODE_PERMISSIONS = 101;
   private  String[] REQUIRED_PERMISSSION = new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

    TextureView textureView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

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
                File file  = new File("sdcard/photos/DCIM(0)/CameraX"+ System.currentTimeMillis());
                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Pic captured at " + file.getAbsolutePath();
                        Toast.makeText(getBaseContext(), msg,Toast.LENGTH_LONG).show();
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