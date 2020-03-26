package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button score_button = findViewById(R.id.score_button);
        Button tips_button = findViewById(R.id.tips_button);
        Button logout_button = findViewById(R.id.logout_button);

        Button audit_button = findViewById(R.id.audit_button);


        audit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AuditActivity.class));
            }
        });
        tips_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, TipsActivity.class));
            }
        });
    }


}




