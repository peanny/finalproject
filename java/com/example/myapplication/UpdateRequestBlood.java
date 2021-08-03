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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateRequestBlood extends AppCompatActivity {
    private EditText RequestNameEdt, RequestContactEdt, RequestBloodGEdt, RequestBloodBEdt, RequestLocationEdt;
    private TextView backbtn;
    private Button updateRequestBtn, deleteRequestBtn;
    private FirebaseFirestore db;

    String requestName, requestContact, requestBloodGroup, requestBloodBag, requestLocation;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request_donor);

        getSupportActionBar().setTitle("Request Donor Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NeedBloodModal need = (NeedBloodModal) getIntent().getSerializableExtra("requests");

        RequestNameEdt = (EditText) findViewById (R.id.idEdtRequestName);
        RequestContactEdt = (EditText) findViewById (R.id.idEdtRequestContact);
        RequestBloodGEdt = (EditText) findViewById (R.id.idEdtRequestBlood);
        RequestBloodBEdt = (EditText) findViewById (R.id.idEdtRequestBag);
        RequestLocationEdt = (EditText) findViewById (R.id.idEdtRequestLocation);

        updateRequestBtn = (Button) findViewById (R.id.idBtnUpdateResquest);
        deleteRequestBtn = (Button) findViewById (R.id.idBtnDelete);

        db = FirebaseFirestore.getInstance();


        RequestNameEdt.setText(need.getRequestName());
        RequestContactEdt.setText(need.getRequestContact());
        RequestBloodGEdt.setText(need.getRequestBloodGroup());
        RequestBloodBEdt.setText(need.getRequestBloodBag());
        RequestLocationEdt.setText(need.getRequestLocation());

        updateRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestName = RequestNameEdt.getText().toString();
                requestContact = RequestContactEdt.getText().toString();
                requestBloodGroup = RequestBloodGEdt.getText().toString();
                requestBloodBag = RequestBloodBEdt.getText().toString();
                requestLocation = RequestLocationEdt.getText().toString();

                if (TextUtils.isEmpty(requestName)) {
                    RequestNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(requestContact)) {
                    RequestContactEdt.setError("Please enter Event Details");
                } else if (TextUtils.isEmpty(requestBloodGroup)) {
                    RequestBloodGEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(requestBloodBag)) {
                   RequestBloodBEdt.setError("Please enter Event Date");
                } else if (TextUtils.isEmpty(requestLocation)) {
                    RequestLocationEdt.setError("Please enter Event Time");
                } else {
                    // calling method to add data to Firebase Firestore.
                    updateRequest(need, requestName, requestContact,requestBloodGroup, requestBloodBag, requestLocation);
                }
            }
        });

        deleteRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete our course.
                deleteRequest(need);
            }
        });



    }

    private void updateRequest( NeedBloodModal need, String  requestName, String requestContact,
                               String requestBloodGroup, String requestBloodBag, String requestLocation){
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        NeedBloodModal updatedRequest = new NeedBloodModal(requestName, requestContact,requestBloodGroup, requestBloodBag, requestLocation);

// after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("RequestDonor").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(need.getId()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(updatedRequest).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        Toast.makeText(UpdateRequestBlood.this, "Request has been updated..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateRequestBlood.this, ViewRequestDonor.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateRequestBlood.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteRequest(NeedBloodModal need) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("RequestDonor").
                // after that we are getting the document
                // which we have to delete.
                        document(need.getId()).

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
                            Toast.makeText(UpdateRequestBlood.this, "Request has been deleted.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateRequestBlood.this, ViewRequestDonor.class);
                            startActivity(i);
                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(UpdateRequestBlood.this, "Fail to delete the request. ", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(UpdateRequestBlood.this, AdminmainpageActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

