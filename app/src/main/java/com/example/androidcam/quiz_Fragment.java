package com.example.androidcam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class quiz_Fragment extends Fragment  implements View.OnClickListener{


    TextView questionTextView;
    Button ansA,ansB,ansC,ansD;
    Button submitBt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_, container, false);
        questionTextView = v.findViewById(R.id.question);
        ansA = v.findViewById(R.id.ans_A);
        ansB = v.findViewById(R.id.ans_B);
        ansC = v.findViewById(R.id.ans_C);
        ansD = v.findViewById(R.id.ans_D);
        submitBt = v.findViewById(R.id.submit);

      ansA.setOnClickListener(this);
      ansB.setOnClickListener(this);
      ansC.setOnClickListener(this);
      ansD.setOnClickListener(this);
      submitBt.setOnClickListener(this);



        return v;


    }

    @Override
    public void onClick(View v) {

    }
}