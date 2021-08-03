package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestBloodRVAdapter extends RecyclerView.Adapter<RequestBloodRVAdapter.ViewHolder> {

    private ArrayList<NeedBloodModal> requestModalArrayList;
    private Context context2;

    public RequestBloodRVAdapter(ArrayList<NeedBloodModal> requestModalArrayList, Context context2) {
        this.requestModalArrayList = requestModalArrayList;
        this.context2 = context2;

    }
    public void filterList(ArrayList<NeedBloodModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        requestModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestBloodRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestBloodRVAdapter.ViewHolder(LayoutInflater.from(context2).inflate(R.layout.activity_request_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestBloodRVAdapter.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        NeedBloodModal modal = requestModalArrayList.get(position);
        holder.requestNameTV.setText(modal.getRequestName());
        holder.requestContactTV.setText(modal.getRequestContact());
        holder.requestBloodGroupTV.setText(modal.getRequestBloodGroup());
        holder.requestBloodBagTV.setText(modal.getRequestBloodBag());
        holder.requestLocationTV.setText(modal.getRequestLocation());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return requestModalArrayList.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {

    // creating variables for our text views.
    private TextView requestNameTV, requestContactTV, requestBloodGroupTV, requestBloodBagTV, requestLocationTV;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        // initializing our text views
        requestNameTV = itemView.findViewById(R.id.idTVRequestName);
        requestContactTV = itemView.findViewById(R.id.idTVRequestContact);
        requestBloodGroupTV = itemView.findViewById(R.id.idTVRequestBloodGroup);
        requestBloodBagTV = itemView.findViewById(R.id.idTVRequestBloodBag);
        requestLocationTV = itemView.findViewById(R.id.idTVRequestLocation);

        // here we are adding on click listener
        // for our item of recycler view.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // after clicking of the item of recycler view.
                // we are passing our course object to the new activity.
                NeedBloodModal modal = requestModalArrayList.get(getAdapterPosition());

                // below line is creating a new intent.
                Intent i = new Intent(context2, UpdateRequestBlood.class);

                // below line is for putting our course object to our next activity.
                i.putExtra("requests", modal);

                // after passing the data we are starting our activity.
                context2.startActivity(i);
            }
        });
    }
    }
}


