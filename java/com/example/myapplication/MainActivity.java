package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email, input_password;
    private TextView reset_pass;
    private Button register, login;
    private ImageView adBtn;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        input_email = (EditText) findViewById(R.id.email);
        input_password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.loginadm);
        login.setOnClickListener(this);



        reset_pass = (TextView) findViewById(R.id.btn_reset_password);
        reset_pass.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Toast.makeText(MainActivity.this, "Welcome to Admin Page", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.loginadm:
                userlogin();
                break;

            case R.id.register:
                Toast.makeText(MainActivity.this, "REGISTRATION PAGE.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                break;


            case R.id.btn_reset_password:
                Toast.makeText(MainActivity.this, "FORGOT PASSWORD", Toast.LENGTH_LONG).show();
                Intent u = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(u);
                break;

        }
    }

    public void userlogin() {
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty()) {
            input_email.setError("Email is required");
            input_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Please enter a valid email");
            input_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            input_password.setError("Password is required");
            input_password.requestFocus();
            return;
        }
        if (password.length() < 8) {
            input_password.setError("Min Password is 8!");
            input_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user.isEmailVerified()) {
                    Toast.makeText(MainActivity.this, "LOGIN.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, MainpageActivity.class);
                    startActivity(i);
                } else {
                    user.sendEmailVerification();
                    Toast.makeText(MainActivity.this, "Check your email to verified your account!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to login,", Toast.LENGTH_LONG).show();
            }
        });
    }
}