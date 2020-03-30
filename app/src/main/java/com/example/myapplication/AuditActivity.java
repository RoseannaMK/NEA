package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

import util.AppAPI;

public class AuditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    private Button backtohome_button;
    public static int score = 0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int currentQuestionIndex = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    //public static final String KEY_SCORE = "score";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        Button naButton = findViewById(R.id.na_button);
        Button yesButton = findViewById(R.id.yes_button);
        Button noButton = findViewById(R.id.no_button);
        Button nextButton = findViewById(R.id.next_button);
        Button backtohome_button = findViewById(R.id.backtohome_button);
        questionTextview = findViewById(R.id.questions_textview);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        naButton.setOnClickListener(this);

        backtohome_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuditActivity.this,
                        HomePage.class));
            }
        });


    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.no_button:
                    checkAnswer(false);
                    break;

                case R.id.yes_button:
                    checkAnswer(true);
                    break;

                case R.id.next_button:
                    if (currentQuestionIndex == 14) {
                        displayScore();
                    }
                    else {
                        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.length; //when it gets to 8 % 8 the answer is zero so it would go back to the start instead of crashing
                        nextQuestion();
                    }

            }
    }
    public void checkAnswer (boolean userChoiceYes) {
        boolean answerIsTrue = questionList[currentQuestionIndex].isAnswerTrue();
        if (userChoiceYes == answerIsTrue) {
            score ++;
        }
        else {
            score = score;
        }
    }
    public void nextQuestion () {
        questionTextview.setText(questionList[currentQuestionIndex].getAnswerResId());
    }
    public void displayScore() {
        Toast.makeText(AuditActivity.this,
                "Finished - you have a score of "+ (score),
                Toast.LENGTH_LONG)
                .show();

        Intent intent = new Intent(getBaseContext(), ScoreActivity.class);
        intent.putExtra("scr", score);
        startActivity(intent);

        sendScore(score);
       // addScore(score);

    }
    public void sendScore (final int score) {
        Intent intent = new Intent(AuditActivity.this, ScoreActivity.class);
        intent.putExtra("scr", score);
        startActivity(intent);
    }

    //public void addScore(int score) {
      //  AppAPI appAPI = AppAPI.getInstance();



//                String thought = enterThought.getText().toString().trim();
//
//                Journal journal = new Journal();
//                journal.setTitle(title);
//                journal.setThought(thought);

//                Map<String, Object> data = new HashMap<>();
//                data.put(KEY_TITLE, title);
//                data.put(KEY_THOUGHT, thought);

//                journalRef.set(journal)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(MainActivity.this,
//                                        "Success", Toast.LENGTH_LONG)
//                                        .show();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "onFailure: " + e.toString());
//                            }
//                        });

    }
//}






