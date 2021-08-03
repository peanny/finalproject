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

public class NeedBloodRVAdapter  extends RecyclerView.Adapter<NeedBloodRVAdapter.ViewHolder> {

    private ArrayList<NeedBloodModal> needModalArrayList;
    private Context context4;

    public NeedBloodRVAdapter(ArrayList<NeedBloodModal> needModalArrayList, Context context4) {
        this.needModalArrayList = needModalArrayList;
        this.context4 = context4;

    }

    public void filterList(ArrayList<NeedBloodModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        needModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NeedBloodRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        return new NeedBloodRVAdapter.ViewHolder(LayoutInflater.from(context4).inflate(R.layout.activity_needblood_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NeedBloodRVAdapter.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        NeedBloodModal modal = needModalArrayList.get(position);
        holder.requestNameTV.setText(modal.getRequestName());
        holder.requestContactTV.setText(modal.getRequestContact());
        holder.requestBloodGroupTV.setText(modal.getRequestBloodGroup());
        holder.requestBloodBagTV.setText(modal.getRequestBloodBag());
        holder.requestLocationTV.setText(modal.getRequestLocation());

    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return needModalArrayList.size();
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
                    NeedBloodModal modal = needModalArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context4, ViewNeedBloodDetails.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("requests", modal);

                    // after passing the data we are starting our activity.
                    context4.startActivity(i);
                }
            });
        }
    }
}