package com.example.androidcam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidcam.databinding.ActivityResultBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityResultBinding binding;
    String path,algo,id,algoLoc,algofull;
    File localFile = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*Intent intent = new Intent(getBaseContext(), MainActivity.class);
          intent.putExtra("activeAlgo", activeAlgo);
          intent.putExtra("keyText", keyText);
          startActivity(intent); */

        FirebaseStorage storage = FirebaseStorage.getInstance();
        path = getIntent().getStringExtra("filepath");
        algo = getIntent().getStringExtra("algo");
        id = getIntent().getStringExtra("id");
        File imgFile = new File(path);


        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (algo.equals("CNN")) {
            algoLoc = "CNN_upload_log";
            algofull = "Convolutional Neural Network";
        }
        else if (algo.equals("FFNN")) {
            algoLoc = "FFNN_upload_log";
            algofull = "Feed Forward Neural Network";
        }


        final DocumentReference docRef = db.collection(algoLoc).document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                  //  Log.d(TAG, "Current data: " + snapshot.getData());

                    String s1 = snapshot.getString("Confidence Value");
                    String s2 = snapshot.getString("Prediction Result");
                   // String s3 = snapshot.getString("Shapes");
                  //  String s4 = snapshot.getString("username");
                  //  String s5 = snapshot.getString("username")
                  //  String s6 = snapshot.getString("username");

                    String text = "Algorithm: \n" + algofull + "\nQuery ID: "+id+"\nPrediction Result: " + s2 + "\nConfidence Value: " + s1 + "\nPossible Results: Square, Star, Triangle, \nPentagon, Rectangle, Heart, Hexagon, \nCircle, Crescent, Cross";
                    TextView tv1 = (TextView)findViewById(R.id.txtField);
                    tv1.setText(text);

                    StorageReference storageRef = storage.getReference();



                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            // Reference to an image file in Cloud Storage
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference(id);

                            ImageView imageView1 = findViewById(R.id.imgv_result_2);
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.get().load(uri).into(imageView1);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });


                        }
                    }, 0);
























                } else {
                  //  Log.d(TAG, "Current data: null");
                }


            }
        });




        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imgv_result_1);

            myImage.setImageBitmap(myBitmap);

        }



    }

}