package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminmainpageActivity extends AppCompatActivity {

    private Button addevent, addrequest;
    private ImageView signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admainpage);

        addevent=(Button) findViewById(R.id.buttonevent);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminmainpageActivity.this,AdminAddEventActivity.class));
            }
        });

        addrequest=(Button)findViewById(R.id.buttonrequest);
        addrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminmainpageActivity.this,AdminAddRequestActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu4) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu4, menu4);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Toast.makeText(AdminmainpageActivity.this, "List of Blood Donation Campaign", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AdminmainpageActivity.this, ViewEvent.class);
                startActivity(i);
                return true;
            case R.id.item3:
                Toast.makeText(AdminmainpageActivity.this, "List of Request Donor", Toast.LENGTH_SHORT).show();
                Intent r = new Intent(AdminmainpageActivity.this, ViewRequestDonor.class);
                startActivity(r);
                return true;
            case R.id.item4:
                Toast.makeText(AdminmainpageActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(AdminmainpageActivity.this, MainActivity.class);
                startActivity(l);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
