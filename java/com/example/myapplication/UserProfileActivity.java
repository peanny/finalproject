package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class UserProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView fullName,email,phone,back, age,bloodgroup;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button changeProfileImage;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone = findViewById(R.id.profilePhone);
        fullName = findViewById(R.id.profileName);
        email    = findViewById(R.id.profileEmail);
        age      = findViewById(R.id.age);
        bloodgroup = findViewById(R.id.bloodgroup);



        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                    age.setText(documentSnapshot.getString("age"));
                    bloodgroup.setText(documentSnapshot.getString("bloodgroup"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });



        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent i = new Intent(v.getContext(),EditProfileActivity.class);
                i.putExtra("fullName",fullName.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("phone",phone.getText().toString());
                i.putExtra("age",age.getText().toString());
                i.putExtra("bloodgroup",bloodgroup.getText().toString());
                startActivity(i);
//

            }
        });



    }








}