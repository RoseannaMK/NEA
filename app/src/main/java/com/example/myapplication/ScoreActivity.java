package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.myapplication.AuditActivity.score;

public class ScoreActivity extends AppCompatActivity {
    private TextView score_display;
    private Button backk_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score_display = findViewById(R.id.score_display);
        backk_button = findViewById(R.id.back_button);

        score_display.setText(""+ score);
        System.out.println(score);

        backk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( ScoreActivity.this,
                        HomePage.class));
            }
        });



    }
}
