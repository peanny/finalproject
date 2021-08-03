package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ViewBloodViewDetails extends AppCompatActivity {

    private TextView EventNameEdt, EventDetailsEdt, EventLocationEdt, EventDateEdt, EventTimeEdt, EventContactEdt;
    private FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood_view_details);

        getSupportActionBar().setTitle("Campaign Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Event event = (Event) getIntent().getSerializableExtra("events");

        db = FirebaseFirestore.getInstance();

        EventNameEdt = (TextView) findViewById(R.id.idEdtEventName);
        EventDetailsEdt = (TextView) findViewById(R.id.idEdtEventDetails);
        EventLocationEdt = (TextView) findViewById(R.id.idEdtEventLocation);
        EventDateEdt = (TextView) findViewById(R.id.idEdtEventDate);
        EventTimeEdt = (TextView) findViewById(R.id.idEdtTime);
        EventContactEdt = (TextView) findViewById(R.id.idEdtEventContact);


        EventNameEdt.setText(event.getEventName());
        EventDetailsEdt.setText(event.getEventDetails());
        EventLocationEdt.setText(event.getEventLocation());
        EventDateEdt.setText(event.getEventDate());
        EventTimeEdt.setText(event.getEventTime());
        EventContactEdt.setText(event.getEventContact());



    }

    public void AddCalendarEvent(View view) {
        Intent i = new Intent(Intent.ACTION_INSERT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra(CalendarContract.Events.TITLE, EventNameEdt.getText().toString());
        i.putExtra(CalendarContract.Events.EVENT_LOCATION, EventLocationEdt.getText().toString());
        i.putExtra(CalendarContract.Events.DESCRIPTION,EventDetailsEdt.getText().toString());
        startActivity(i);
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
                Intent i = new Intent(ViewBloodViewDetails.this, MainpageActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
