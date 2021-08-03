package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText email, password;

    private TextView back;

    private Button LoginAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        setupUI();
        setupListener();
        
        getSupportActionBar().setTitle("Admin Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupUI(){
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        LoginAdm = (Button) findViewById(R.id.loginadm);
    }

    private void setupListener(){

        LoginAdm.setOnClickListener(v -> checkEmail());

    }


    void checkEmail() {
        boolean isValid = true;
        if (isEmpty(email)){
            email.setError("You must enter username to login!");
            isValid = false;
        } else {
            if (!isEmail(email)) {
               email.setError("Enter valid email!");
                isValid = false;
            }
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }

        if (isValid) {
            String usernameValue = email.getText().toString();
            String passwordValue = password.getText().toString();
            if (usernameValue.equals("admin@gmail.com") && passwordValue.equals("password1234")) {
                //everything checked we open new activity
                Toast.makeText(AdminLogin.this, "LOGIN", Toast.LENGTH_LONG).show();
                Intent i = new Intent(AdminLogin.this, AdminmainpageActivity.class);
                startActivity(i);
                //we close this activity
                this.finish();
            } else {
                Toast t = Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}
