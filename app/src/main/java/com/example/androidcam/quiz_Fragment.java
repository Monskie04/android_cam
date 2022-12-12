package com.example.androidcam;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class quiz_Fragment extends Fragment  implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBt;
    int score = 0;
    int totalQuestion = QuestionAns.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


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
        totalQuestionsTextView = v.findViewById(R.id.total_question);
        questionTextView = v.findViewById(R.id.question);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBt.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        loadNewQuestion();

        return v;


    }


    @Override
    public void onClick(View v) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) v;
        if (clickedButton.getId() == R.id.submit) {
            if (selectedAnswer.equals(QuestionAns.correctAns[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();

        }else{
            //choices button clicked
            selectedAnswer  = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.BLUE);

        }

    }

    void loadNewQuestion() {
        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAns.question[currentQuestionIndex]);
        ansA.setText(QuestionAns.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAns.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAns.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAns.choices[currentQuestionIndex][3]);
    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(getActivity())
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();


    }
    void restartQuiz(){
        score = 0;
        currentQuestionIndex =0;
        loadNewQuestion();
    }
}