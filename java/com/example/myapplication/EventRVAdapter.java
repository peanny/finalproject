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

public class EventRVAdapter extends RecyclerView.Adapter<EventRVAdapter.ViewHolder> {
    // variable for our array list and context
    private ArrayList<Event> eventArrayList;
    private Context context;

    // constructor
    public EventRVAdapter(ArrayList<Event>eventArrayList, Context context) {
        this.eventArrayList = eventArrayList;
        this.context = context;
    }
    public void filterList(ArrayList<Event> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        eventArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EventRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
       return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_event_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventRVAdapter.ViewHolder holder, int position) {

        Event event = eventArrayList.get(position);
        holder.eventNameTV.setText(event.getEventName());
        holder.eventDetailsTV.setText(event.getEventDetails());
        holder.eventLocationTV.setText(event.getEventLocation());
        holder.eventDateTV.setText(event.getEventDate());
        holder.eventTimeTV.setText(event.getEventTime());
        holder.eventContactTV.setText(event.getEventContact());




    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return eventArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private final TextView eventNameTV;
        private final TextView eventDetailsTV;
        private final TextView eventLocationTV;
        private final TextView eventDateTV;
        private final TextView eventTimeTV;
        private final TextView eventContactTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            eventNameTV = itemView.findViewById(R.id.idTVEventName);
            eventDetailsTV = itemView.findViewById(R.id.idTVEventDetails);
            eventLocationTV = itemView.findViewById(R.id.idTVEventLocation);
            eventDateTV = itemView.findViewById(R.id.idTVEventDate);
            eventTimeTV = itemView.findViewById(R.id.idTVEventTime);
            eventContactTV = itemView.findViewById(R.id.idTVEventContact);

            // here we are adding on click listener
            // for our item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    Event event = eventArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, UpdateEvent.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("events", event);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }
            });
        }
    }
}
