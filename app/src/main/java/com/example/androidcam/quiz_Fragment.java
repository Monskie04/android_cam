package com.example.androidcam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Random;


public class quiz_Fragment extends Fragment  implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView,questt;
    Button ansA, ansB, ansC, ansD;
    String txtAns_A, txtAns_B, txtAns_C, txtAns_D;
    int ans_key;
    Button submitBt;
    int score = 0;
    int totalQuestion = QuestionAns.question.length;
    int currentQuestionIndex = 0;
    int a,b,c,d,ans;
    String selectedAnswer = "", id,result,path;
    String[] shapes = {"square","star","triangle","pentagon","rectangle","heart","hexagon","circle","crescent","cross"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_, container, false);
        ansA = v.findViewById(R.id.ans_A);
        ansB = v.findViewById(R.id.ans_B);
        ansC = v.findViewById(R.id.ans_C);
        ansD = v.findViewById(R.id.ans_D);
        questt = v.findViewById(R.id.question);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);

        llooding activity = (llooding) getActivity();
        id = activity.getQuizID();
        path = activity.getFilepath();
        result = activity.getResult();

        File imgFile = new File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = v.findViewById(R.id.imageView3);

            myImage.setImageBitmap(myBitmap);

        }
        ans= new Random().nextInt(4);
        do {
            a= new Random().nextInt(10);
            ansA.setText(shapes[a]);
        } while( a == ans);
        do {
            b= new Random().nextInt(10);;
            ansB.setText(shapes[b]);
        } while(b == a || b == ans);
        do {
            c= new Random().nextInt(10);;
            ansC.setText(shapes[c]);
        } while(c==a || c ==b|| c == ans);
        do {
            d= new Random().nextInt(10);;
            ansD.setText(shapes[d]);
        } while(d==c || d==b || d ==a|| d == ans);


        if(ans == 0) {
            ansA.setText(result);
        }
        if(ans == 1) {
            ansB.setText(result);
        }
        if(ans == 2) {
            ansC.setText(result);
        }
        if(ans == 3) {
            ansD.setText(result);
        }

        return v;

    }


    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;
        if (clickedButton.getId() == R.id.ans_A) {

          if(ans == 0){

              questt.setText("Correct! Good Job!");
              launch_new();
          }
          else {

              questt.setText("Wrong! Try again.");
              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                  public void run() {

                      questt.setText("What shape is this?");
                  }
              }, 500);
          }





        }

        if (clickedButton.getId() == R.id.ans_B) {


            if(ans == 1){

                questt.setText("Correct! Good Job!");
                launch_new();
            }
            else {

                questt.setText("Wrong! Try again.");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        questt.setText("What shape is this?");
                    }
                }, 500);
            }

        }

        if (clickedButton.getId() == R.id.ans_C) {


            if(ans == 2){

                questt.setText("Correct! Good Job!");
                launch_new();
            }
            else {

                questt.setText("Wrong! Try again.");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        questt.setText("What shape is this?");
                    }
                }, 500);
            }

        }

        if (clickedButton.getId() == R.id.ans_D) {


            if(ans == 3){

                questt.setText("Correct! Good Job!");
                launch_new();
            }
            else {

                questt.setText("Wrong! Try again.");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        questt.setText("What shape is this?");
                    }
                }, 700);
            }

        }



    }

void launch_new() {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        public void run() {

            Intent intent2 = new Intent(getActivity(), MainActivity.class);
            intent2.putExtra("activeAlgo","quiz" );
            startActivity(intent2);
        }
    }, 1500);

}





}