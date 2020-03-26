package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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


public class LoginPage extends AppCompatActivity {

    private EditText email_text,password_text, username_text;
    private Button login_button;
    private Button sign_up_button;
    //private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //progressBar = findViewById(R.id.login_progress);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginPage.this, HomePage.class));
            finish();
        }

        setContentView(R.layout.activity_login_page);

        email_text = findViewById(R.id.email_text);
        password_text = findViewById(R.id.password_text);
        username_text = findViewById(R.id.username_text);
        login_button = findViewById(R.id.login_button);
        sign_up_button = findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, SignUpPage.class));
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_text.getText().toString();
                final String password = password_text.getText().toString();

                loginUser(email, password);

            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

    }
    private void loginUser(String email, String password) {

        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String currentUserId = user.getUid();
                            collectionReference
                                    .whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                            @Nullable FirebaseFirestoreException e) {

                                            if (e != null) {

                                            }
                                            assert queryDocumentSnapshots != null;
                                            if (!queryDocumentSnapshots.isEmpty()) {

                                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                    AppAPI appAPI = AppAPI.getInstance();
                                                    appAPI.setUsername(snapshot.getString("username"));
                                                    appAPI.setUserId(snapshot.getString("userId"));

                                                    //Go to home page
                                                    startActivity(new Intent(LoginPage.this,
                                                            HomePage.class));
                                                }
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressBar.setVisibility(View.INVISIBLE);

                        }
                    });

        }else {

            //progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginPage.this,
                    "Please enter email and password",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}