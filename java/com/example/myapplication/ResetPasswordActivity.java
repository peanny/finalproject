package com.example.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button forgotpass;
    private ProgressBar progressBar;


    FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);


        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailPass);
        forgotpass = (Button) findViewById(R.id.resetpassword_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        auth = FirebaseAuth.getInstance();

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }
        });
    }
        private void resetPassword(){
            String email = editTextEmail.getText().toString().trim();

            if(email.isEmpty()){
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;

        }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                editTextEmail.setError("Please provide a valid email");
                editTextEmail.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "Check your email to restart your password", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ResetPasswordActivity.this,"Try any! Something wrong happened!", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
    }


}
