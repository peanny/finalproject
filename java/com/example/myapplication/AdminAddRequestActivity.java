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

public class AdminAddRequestActivity extends AppCompatActivity {

    private EditText RequestNameEdt, RequestContactEdt, RequestBloodGEdt, RequestBloodBEdt, RequestLocationEdt;
    private Button addRequestBtn, readRequestBtn;
    private ImageView back;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrequest);

        getSupportActionBar().setTitle("Add New Request Donor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        RequestNameEdt = (EditText) findViewById (R.id.idEdtRequestName);
        RequestContactEdt = (EditText) findViewById (R.id.idEdtRequestContact);
        RequestBloodGEdt = (EditText) findViewById (R.id.idEdtRequestBlood);
        RequestBloodBEdt = (EditText) findViewById (R.id.idEdtRequestBag);
        RequestLocationEdt = (EditText) findViewById (R.id.idEdtRequestLocation);

        addRequestBtn = (Button) findViewById (R.id.idBtnAddRequest);







        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String requestName = RequestNameEdt.getText().toString();
                String requestContact = RequestContactEdt.getText().toString();
                String requestBloodGroup = RequestBloodGEdt.getText().toString();
                String requestBloodBag = RequestBloodBEdt.getText().toString();
                String requestLocation= RequestLocationEdt.getText().toString();


                if (TextUtils.isEmpty(requestName)) {
                    RequestNameEdt.setError("Please enter Event Name");
                } else if (TextUtils.isEmpty(requestContact)) {
                    RequestContactEdt.setError("Please enter Event Details");
                } else if (TextUtils.isEmpty(requestBloodGroup)) {
                    RequestBloodGEdt.setError("Please enter Course Duration");
                } else if (TextUtils.isEmpty(requestBloodBag)){
                    RequestBloodBEdt.setError("Please enter Event Date");
                } else if(TextUtils.isEmpty(requestLocation)){
                    RequestLocationEdt.setError("Please enter Event Time");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(requestName, requestContact, requestBloodGroup, requestBloodBag, requestLocation);
                }
            }
        });


}

    private void addDataToFirestore(String requestName, String requestContact,
                                    String requestBloodGroup, String requestBloodBag, String requestLocation){
        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbRequest = db.collection("RequestDonor");

        // adding our data to our courses object class.
        NeedBloodModal need = new NeedBloodModal(requestName, requestContact, requestBloodGroup, requestBloodBag, requestLocation);

        // below method is use to add data to Firebase Firestore.
        dbRequest.add(need).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(AdminAddRequestActivity.this, " Your Request has been added ", Toast.LENGTH_SHORT).show();
                Intent e =new Intent(AdminAddRequestActivity.this, ViewRequestDonor.class);
                startActivity(e);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(AdminAddRequestActivity.this, "Fail to add request \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
