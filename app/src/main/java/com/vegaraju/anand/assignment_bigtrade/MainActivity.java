package com.vegaraju.anand.assignment_bigtrade;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String email, password;
    private EditText emailtext, passwordtext;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent gotobusinesslist = new Intent(MainActivity.this, BusinessListActivity.class);
                    startActivity(gotobusinesslist);
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "You are signed out",Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        emailtext = (EditText)findViewById(R.id.emailid);
        passwordtext = (EditText)findViewById(R.id.passwordid);
        login = (Button)findViewById(R.id.loginbuttonid);
        register = (Button)findViewById(R.id.registerbuttonid);

        // on login button click

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        email = emailtext.getText().toString();
                        password = passwordtext.getText().toString();
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        Intent gotobusinesslist = new Intent(MainActivity.this, BusinessListActivity.class);
                                        startActivity(gotobusinesslist);


                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {

                                            Toast.makeText(MainActivity.this, "Authentication failed / Email not registered",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent gotomain = new Intent(MainActivity.this,MainActivity.class);
                                            startActivity(gotomain);
                                        }

                                        // ...
                                    }
                                });
                    }
                }
        );

        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        email = emailtext.getText().toString();
                        password = passwordtext.getText().toString();
                        mAuth.createUserWithEmailAndPassword( email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Toast.makeText(getApplicationContext(),"Please login after confirming email",Toast.LENGTH_SHORT).show();


                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed / Email not registered",
                                            Toast.LENGTH_SHORT).show();
                                    Intent gotomain = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(gotomain);

                                }

                                // ...
                            }
                        });

                    }
                }
        );




    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }



    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
