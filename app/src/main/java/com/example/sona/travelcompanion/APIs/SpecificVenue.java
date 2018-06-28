package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenue {

    SpecificVenueResponse response;

    public SpecificVenue(SpecificVenueResponse response) {
        this.response = response;
    }

    public SpecificVenueResponse getResponse() {
        return response;
    }

    public void setResponse(SpecificVenueResponse response) {
        this.response = response;
    }
}
