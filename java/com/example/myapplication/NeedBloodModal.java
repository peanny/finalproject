package com.example.myapplication;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class NeedBloodModal implements Serializable {

    // getter method for our id
    public String getId() {
        return id;
    }

    // setter method for our id
    public void setId(String id) {
        this.id = id;
    }

    @Exclude

    private String id;
    private String requestName;
    private String requestContact;
    private String requestBloodGroup;
    private String requestBloodBag;
    private String requestLocation;

    private NeedBloodModal(){

    }


    // constructor
    public NeedBloodModal(String requestName, String requestContact, String requestBloodGroup, String requestBloodBag, String requestLocation) {
        this.requestName = requestName;
        this.requestContact= requestContact;
        this.requestBloodGroup = requestBloodGroup;
        this.requestBloodBag = requestBloodBag;
        this.requestLocation = requestLocation;
    }

    // creating getter and setter methods
    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getRequestContact() {
        return requestContact;
    }

    public void setRequestContact(String requestContact) {
        this.requestContact = requestContact;
    }

    public String getRequestBloodGroup() {
        return requestBloodGroup;
    }

    public void setRequestBloodGroup(String requestBloodGroup) { this.requestBloodGroup = requestBloodGroup;
    }

    public String getRequestBloodBag() {
        return requestBloodBag;
    }

    public void setRequestBloodBag (String requestBloodBag) { this.requestBloodBag = requestBloodBag;
    }

    public String getRequestLocation() {
        return requestLocation;
    }
    public void setRequestLocation (String requestLocation){ this.requestLocation = requestLocation;
    }

}
