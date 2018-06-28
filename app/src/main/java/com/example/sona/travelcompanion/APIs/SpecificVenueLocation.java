package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueLocation {

    ArrayList<String> formattedAddress;

    public SpecificVenueLocation(ArrayList<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public ArrayList<String> getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(ArrayList<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
