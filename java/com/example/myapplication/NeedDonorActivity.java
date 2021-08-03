package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NeedDonorActivity extends AppCompatActivity {

    private ArrayList<NeedBloodModal> needModalArrayList;
    private FirebaseFirestore db;
    private NeedBloodRVAdapter needBloodRVAdapter;
    private RecyclerView needBloodsRV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_donor);

        getSupportActionBar().setTitle("List of Need Donor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        needBloodsRV = findViewById(R.id.idRVNeedBlood);
        loadingPB = findViewById(R.id.idProgressBar);


        db = FirebaseFirestore.getInstance();

        // creating our new array list
        needModalArrayList = new ArrayList<>();
        needBloodsRV.setHasFixedSize(true);
        needBloodsRV.setLayoutManager(new LinearLayoutManager(NeedDonorActivity.this));

        // on below line passing our array lost to our adapter class.
        needBloodRVAdapter= new NeedBloodRVAdapter(needModalArrayList, NeedDonorActivity.this);
        // setting adapter to our recycler view.
        needBloodsRV.setAdapter(needBloodRVAdapter);


        db.collection("RequestDonor").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                NeedBloodModal c = d.toObject(NeedBloodModal.class);
                                // below is the updated line of code which we have to
                                // add to pass the document id inside our modal class.
                                // we are setting our document id with d.getId() method
                                c.setId(d.getId());
                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                needModalArrayList.add(c);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            needBloodRVAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(NeedDonorActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(NeedDonorActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu5) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu5, menu5);

        MenuItem item =menu5.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    private void searchData(String query) {
        ArrayList<NeedBloodModal> search = new ArrayList<>();
        // running a for loop to compare elements.
        for (NeedBloodModal item : needModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getRequestBloodGroup().toLowerCase().contains(query.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                search.add(item);
            }
        }
        if (search.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(NeedDonorActivity.this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            needBloodRVAdapter.filterList(search);
        }
    }
    
}