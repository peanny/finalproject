package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateEvent extends AppCompatActivity {

    private EditText EventNameEdt, EventDetailsEdt, EventLocationEdt, EventDateEdt, EventTimeEdt, EventContactEdt;
    private Button updateEventBtn, deleteEventBtn;
    private FirebaseFirestore db;
    private ImageView eventPoster;
    String eventName, eventDetails, eventLocation, eventDate, eventTime, eventContact;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        getSupportActionBar().setTitle("Blood Donation Campaign");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Event event = (Event) getIntent().getSerializableExtra("events");

        EventNameEdt = (EditText) findViewById(R.id.idEdtEventName);
        EventDetailsEdt = (EditText) findViewById(R.id.idEdtEventDetails);
        EventLocationEdt = (EditText) findViewById(R.id.idEdtEventLocation);
        EventDateEdt = (EditText) findViewById(R.id.idEdtEventDate);
        EventTimeEdt = (EditText) findViewById(R.id.idEdtEventTime);
        EventContactEdt = (EditText) findViewById(R.id.idEdtEventContact);
        updateEventBtn = (Button) findViewById(R.id.idBtnUpdateEvent);
        deleteEventBtn = (Button) findViewById(R.id.idBtnDelete);
        eventPoster = (ImageView) findViewById(R.id.eventPoster);


        db = FirebaseFirestore.getInstance();
        EventNameEdt.setText(event.getEventName());
        EventDetailsEdt.setText(event.getEventDetails());
        EventLocationEdt.setText(event.getEventLocation());
        EventDateEdt.setText(event.getEventDate());
        EventTimeEdt.setText(event.getEventTime());
        EventContactEdt.setText(event.getEventContact());
        updateEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventName = EventNameEdt.getText().toString();
                eventDetails = EventDetailsEdt.getText().toString();
                eventLocation = EventLocationEdt.getText().toString();
                eventDate = EventDateEdt.getText().toString();
                eventTime = EventTimeEdt.getText().toString();
                eventContact = EventContactEdt.getText().toString();

                if (TextUtils.isEmpty(eventName)) {
                    EventNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(eventDetails)) {
                    EventDetailsEdt.setError("Please enter Event Details");
                } else if (TextUtils.isEmpty(eventLocation)) {
                    EventLocationEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(eventDate)) {
                    EventDateEdt.setError("Please enter Event Date");
                } else if (TextUtils.isEmpty(eventTime)) {
                    EventTimeEdt.setError("Please enter Event Time");
                } else if (TextUtils.isEmpty(eventContact)) {
                    EventContactEdt.setError("Please enter Contact");
                } else {
                    // calling method to add data to Firebase Firestore.
                    updateEvent(event, eventName, eventDetails, eventLocation, eventDate, eventTime, eventContact);
                }
            }
        });


    }
    private void updateEvent(Event event, String eventName, String eventDetails, String eventLocation, String eventDate, String eventTime, String eventContact){
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Event updatedEvent = new Event(eventName, eventDetails, eventLocation, eventDate, eventTime, eventContact);

// after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Event").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(event.getId()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(updatedEvent).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        Toast.makeText(UpdateEvent.this, "Events has been updated..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateEvent.this, ViewEvent.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateEvent.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEvent(Event event) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Event").
                // after that we are getting the document
                // which we have to delete.
                        document(event.getId()).

                // after passing the document id we are calling
                // delete method to delete this document.
                        delete().
                // after deleting call on complete listener
                // method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // inside on complete method we are checking
                        // if the task is success or not.
                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(UpdateEvent.this, "Event has been deleted.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateEvent.this, ViewEvent.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(UpdateEvent.this, "Fail to delete the event. ", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(UpdateEvent.this, AdminmainpageActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}