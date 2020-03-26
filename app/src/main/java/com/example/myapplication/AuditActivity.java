package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class AuditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    public int score = 0;
    //private Button nextButton;

    public int currentQuestionIndex = 0;

    private Question[] questionList = new Question[]{
            new Question(R.string.question_vehicle, true),
            new Question(R.string.question_distance, true),
            new Question(R.string.question_roofrack, true),
            new Question(R.string.question_team, true),
            new Question(R.string.question_goals, true),
            new Question(R.string.question_electrics, true),
            new Question(R.string.question_renewable, true),
            new Question(R.string.question_plants, true),
            new Question(R.string.question_taps, true),
            new Question(R.string.question_flush, true),
            new Question(R.string.question_lights, true),
            new Question(R.string.question_bulbs, true),
            new Question(R.string.question_thermostats, true),
            new Question(R.string.question_draughts, true),
            new Question(R.string.question_glazing, true)
    };

    public int listLength = questionList.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        Button naButton = findViewById(R.id.na_button);
        Button yesButton = findViewById(R.id.yes_button);
        Button noButton = findViewById(R.id.no_button);
        Button nextButton = findViewById(R.id.next_button);
        questionTextview = findViewById(R.id.questions_textview);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        naButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        while (currentQuestionIndex < listLength) {

            switch (v.getId()) {

                case R.id.yes_button:

                    break;

                case R.id.next_button:
                    currentQuestionIndex = (currentQuestionIndex + 1) % questionList.length; // we are safe now!
                    questionTextview.setText(questionList[currentQuestionIndex].getAnswerResId());
                    break;
            }
            System.out.println(score);
        }
    }
}


       /* private void nextQuestion () {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.length; // we are safe now!
        questionTextview.setText(questionList[currentQuestionIndex].getAnswerResId());

        */




