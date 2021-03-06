package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.AppAPI;


public class SignUpPage extends AppCompatActivity {

    private EditText username_text, password_text, email_text;
    private Button register_button, back_button;
    private String scoreVal;

    //connection to firebase collection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();

        register_button = findViewById(R.id.register_button);
        back_button = findViewById(R.id.back_button);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);
        email_text = findViewById(R.id.email_text);

        authStateListener = new FirebaseAuth.AuthStateListener() { //listens for change in authentication
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) { //will listen for changes in authentication
                currentUser = firebaseAuth.getCurrentUser(); //check current user is still the same
                if (currentUser != null) {
                    startActivity(new Intent(SignUpPage.this,
                            Options.class));
                    finish();
                }
            }
        };

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email_text.getText().toString()) //checks that user has filled in field
                        && !TextUtils.isEmpty(password_text.getText().toString())
                        && !TextUtils.isEmpty(username_text.getText().toString())) {

                    String email = email_text.getText().toString().trim();
                    String password = password_text.getText().toString().trim();
                    String username = username_text.getText().toString().trim();

                    register_account(email, password, username,scoreVal);
                } else {
                    Toast.makeText(SignUpPage.this,
                            "Please Fill In All Fields",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( SignUpPage.this,
                        LoginPage.class));

            }
        });
    }
    private void register_account(String email, final String password, final String username, final String scoreVal) {
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TEST","creatinguser test");
                            if (task.isSuccessful()) {
                                Log.d("TEST","createUserWithEmail:success");
                                //add user to database as well as authentication
                                currentUser = firebaseAuth.getCurrentUser();

                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                //Create a user Hashmap to create a user in the User collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);
                                userObj.put("password",password);
                                userObj.put("score",scoreVal);

                                //save to our firestore  database
                                FirebaseFirestore.getInstance().collection("Users")
                                        .add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("Success", "DocumentSnapshot added with ID" + documentReference.getId() + documentReference.toString());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Failure","Error adding document",e);
                                            }
                                        });
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {

                                                                    String name = task.getResult()
                                                                            .getString("username");
                                                                    String password = task.getResult()
                                                                            .getString("password");
                                                                    String scoreVal = task.getResult()
                                                                            .getString("score");

                                                                    AppAPI appAPI = AppAPI.getInstance();

                                                                    appAPI.setUserId(currentUserId);
                                                                    appAPI.setUsername(name);
                                                                    appAPI.setPassword(password);
                                                                    appAPI.setScore(scoreVal);

                                                                    Intent intent = new Intent(SignUpPage.this, HomePage.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("userId", currentUserId);
                                                                    intent.putExtra("password", password);
                                                                    intent.putExtra("score",scoreVal);
                                                                    startActivity(intent);

                                                                }else {
                                                                    Toast.makeText(SignUpPage.this, "Authentication failed.",
                                                                            Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("TEST","LINE FAILED!!!");

                                            }
                                        });
                            }else {
                                Toast.makeText(SignUpPage.this, "Authentication failed, 172",
                                       Toast.LENGTH_SHORT).show();

                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }else {
            Log.d("TEST","LINE 175");

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TEST","LINE 194");
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    }


}




