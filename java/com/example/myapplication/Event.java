package com.example.myapplication;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Event implements Serializable {

    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }

    // we are using exclude because
    // we are not saving our id
    @Exclude
    private String id;
    private String eventName;
    private String eventDetails;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private String eventContact;


    public Event() {
        // empty constructor
        // required for Firebase.
    }

    // constructor
    public Event (String eventName, String eventDetails, String eventLocation, String eventDate, String eventTime, String eventContact) {
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventContact = eventContact;
    }

        // creating getter and setter methods
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }
    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventLocation() {
        return eventLocation;
    }
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate (String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {return eventTime;}
    public void  setEventTime (String eventTime) {this.eventTime = eventTime;}

    public String getEventContact() {
        return eventContact;
    }
    public void setEventContact (String eventContact){
        this.eventContact = eventContact;
    }

}



