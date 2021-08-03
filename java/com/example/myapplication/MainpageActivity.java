package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainpageActivity extends AppCompatActivity {

    private Button bloodcamp, needBlood;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        bloodcamp=(Button) findViewById(R.id.buttonevent);
        bloodcamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainpageActivity.this,BloodCamp.class));
            }
        });

        needBlood=(Button)findViewById(R.id.buttonrequest);
        needBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainpageActivity.this,NeedDonorActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Toast.makeText(MainpageActivity.this, "My Profile", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainpageActivity.this, UserProfileActivity.class);
                startActivity(i);
                return true;
            case R.id.item4:
                Toast.makeText(MainpageActivity.this, "Information", Toast.LENGTH_SHORT).show();
                Intent r = new Intent(MainpageActivity.this, Aboutus.class);
                startActivity(r);
                return true;
            case R.id.item5:
                Toast.makeText(MainpageActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(MainpageActivity.this, MainActivity.class);
                startActivity(l);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


