package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ViewNeedBloodDetails extends AppCompatActivity {

    private TextView  RequestNameEdt, RequestContactEdt, RequestBloodGEdt, RequestBloodBEdt, RequestLocationEdt;
    private Button btnback;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_need_blood_details);

        getSupportActionBar().setTitle("Request Donor Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NeedBloodModal modal = (NeedBloodModal) getIntent().getSerializableExtra("requests");

        db = FirebaseFirestore.getInstance();

        RequestNameEdt = (TextView) findViewById (R.id.idEdtRequestName);
        RequestContactEdt = (TextView) findViewById (R.id.idEdtRequestContact);
        RequestBloodGEdt = (TextView) findViewById (R.id.idEdtRequestBlood);
        RequestBloodBEdt = (TextView) findViewById (R.id.idEdtRequestBag);
        RequestLocationEdt = (TextView) findViewById (R.id.idEdtRequestLocation);
        btnback = (Button) findViewById (R.id.buttonback);

        RequestNameEdt.setText(modal.getRequestName());
        RequestContactEdt.setText(modal.getRequestContact());
        RequestBloodGEdt.setText(modal.getRequestBloodGroup());
        RequestBloodBEdt.setText(modal.getRequestBloodBag());
        RequestLocationEdt.setText(modal.getRequestLocation());


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RequestContactEdt = (TextView) findViewById(R.id.idEdtRequestContact);

                Toast.makeText(ViewNeedBloodDetails.this, "Contact Now",
                        Toast.LENGTH_LONG).show();

                Uri u = Uri.parse("tel:" + RequestContactEdt.getText().toString());

                Intent i = new Intent(Intent.ACTION_DIAL, u);

                try {
                    startActivity(i);
                }
                catch (SecurityException s){
                    Toast.makeText(ViewNeedBloodDetails.this,"An Error Occurred",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu3) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3, menu3);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Intent i = new Intent(ViewNeedBloodDetails.this, MainpageActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}