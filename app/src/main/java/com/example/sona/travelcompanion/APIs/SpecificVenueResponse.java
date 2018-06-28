package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueResponse {
    SpecificVenueResponseVenue venue;

    public SpecificVenueResponse(SpecificVenueResponseVenue venue) {
        this.venue = venue;
    }

    public SpecificVenueResponseVenue getVenue() {
        return venue;
    }

    public void setVenue(SpecificVenueResponseVenue venue) {
        this.venue = venue;
    }
}
