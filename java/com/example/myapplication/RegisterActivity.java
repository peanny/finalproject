package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";
    private EditText editName, editEmail, editPhone, editPass;
    private ProgressBar pBar;
    private Button registerUser;
    private TextView login;
    private FirebaseFirestore mstore;
    private FirebaseAuth mAuth;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        mstore = FirebaseFirestore.getInstance();

        login = (TextView) findViewById(R.id.textViewalreadylogin);
        login.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.resetpassword_btn);
        registerUser.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.editTextTextEmailPass);
        editName = (EditText) findViewById(R.id.editTextTextPersonName);
        editPass = (EditText) findViewById(R.id.editTextTextPasswordReg);
        editPhone = (EditText) findViewById(R.id.editTextPhone);

        pBar = (ProgressBar) findViewById(R.id.progressBarReg);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.textViewalreadylogin:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.resetpassword_btn:
                registerUser();
                break;
        }
    }


    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPass.getText().toString().trim();
        String fullname = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (fullname.isEmpty()) {
            editName.setError("Name is required");
            editName.requestFocus();
            return;

        }
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Valid email is required");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editPass.setError("Password is required");
            editPass.requestFocus();
            return;
        }
        if (password.length() < 8) {
            editPass.setError("Your password should be more than 8 characters");
            editPass.requestFocus();
            return;
        }

        pBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser fuser = mAuth.getCurrentUser();

                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mstore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",fullname);
                            user.put("email",email);
                            user.put("phone",phone);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            pBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.GONE);
                        }

                    }
                });


    }
}
