package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminAddEventActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText EventNameEdt, EventDetailsEdt, EventLocationEdt, EventDateEdt, EventTimeEdt, EventContactEdt;
    private Button addEventBtn, mButtonChooseImage;
    private ImageView mImageView;
    private FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        getSupportActionBar().setTitle("Add New Blood Donation Campaign");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        EventNameEdt = (EditText) findViewById(R.id.idEdtEventName);
        EventDetailsEdt = (EditText) findViewById(R.id.idEdtEventDetails);
        EventLocationEdt = (EditText) findViewById(R.id.idEdtEventLocation);
        EventDateEdt = (EditText) findViewById(R.id.idEdtEventDate);
        EventTimeEdt = (EditText) findViewById(R.id.idEdtTime);
        EventContactEdt =(EditText) findViewById(R.id.idEdtContact);

        mImageView = (ImageView) findViewById(R.id.eventPoster);

        addEventBtn = (Button) findViewById(R.id.idBtnAddRequest);



        // below line is to add on click listener for our add course button.
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String eventName = EventNameEdt.getText().toString();
                String eventDetails = EventDetailsEdt.getText().toString();
                String eventLocation = EventLocationEdt.getText().toString();
                String eventDate = EventDateEdt.getText().toString();
                String eventTime = EventTimeEdt.getText().toString();
                String eventContact = EventContactEdt.getText().toString();


                if (TextUtils.isEmpty(eventName)) {
                    EventNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(eventDetails)) {
                    EventDetailsEdt.setError("Please enter Event Details");
                } else if (TextUtils.isEmpty(eventLocation)) {
                    EventLocationEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(eventDate)){
                    EventDateEdt.setError("Please enter Event Date");
                } else if(TextUtils.isEmpty(eventTime)){
                    EventTimeEdt.setError("Please enter Event Time");
                }else if(TextUtils.isEmpty(eventContact)){
                    EventContactEdt.setError("Please enter Contact");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(eventName, eventDetails, eventLocation, eventDate, eventTime, eventContact);

                }
            }
        });




    }
    private void addDataToFirestore(String eventName, String eventDetails, String eventLocation, String eventDate, String eventTime, String eventContact){
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbEvents = db.collection("Event");

        // adding our data to our courses object class.
        Event event = new Event(eventName, eventDetails, eventLocation, eventDate, eventTime, eventContact);

        // below method is use to add data to Firebase Firestore.
        dbEvents.add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(AdminAddEventActivity.this, " Your Event has been added ", Toast.LENGTH_SHORT).show();
                Intent y = new Intent(AdminAddEventActivity.this,ViewEvent.class);
                startActivity(y);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(AdminAddEventActivity.this, "Fail to add event \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
