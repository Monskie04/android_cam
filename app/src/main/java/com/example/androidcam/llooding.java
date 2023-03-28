package com.example.androidcam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

public class llooding extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String type,algo,id,filepath,algoLoc,algofull,result;
    File imgFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llooding);

        type = String.valueOf(getIntent().getStringExtra("type"));

        if (type.equals("quiz")){

             id = String.valueOf(getIntent().getStringExtra("id"));
             algo = String.valueOf(getIntent().getStringExtra("algo"));
             type = String.valueOf(getIntent().getStringExtra("type"));
             filepath = String.valueOf(getIntent().getStringExtra("filepath"));

            FirebaseStorage storage = FirebaseStorage.getInstance();
            imgFile = new File(filepath);










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

                        result = snapshot.getString("Prediction Result");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                getSupportFragmentManager().beginTransaction().add(android.R.id.content, new quiz_Fragment()).commit();
                            }
                        }, 0);

















                    } else {
                        //  Log.d(TAG, "Current data: null");
                    }


                }
            });



        }



    }

    public String getQuizID() {

        return id;
    }

    public String getFilepath() {

        return filepath;
    }

    public String getResult() {

        return result;
    }

}