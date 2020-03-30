package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import javax.annotation.Nullable;

import util.AppAPI;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continue_button = findViewById(R.id.continueButton);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    currentUser = firebaseAuth.getCurrentUser();
                    final String currentUserId = currentUser.getUid();

                    collectionReference
                            .whereEqualTo("userId", currentUserId)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        return;
                                    }

                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            AppAPI appAPI = AppAPI.getInstance();
                                            appAPI.setUserId(snapshot.getString("userId"));
                                            appAPI.setUsername(snapshot.getString("username"));
                                            appAPI.setPassword(snapshot.getString("password"));
                                            appAPI.setScore(snapshot.getString("score"));

                                            finish();

                                        }
                                    }
                                }


                            });
                } else {
                }
            }
        };
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        LoginPage.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
