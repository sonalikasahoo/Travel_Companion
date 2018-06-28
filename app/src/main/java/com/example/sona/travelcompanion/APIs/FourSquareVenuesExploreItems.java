package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class FourSquareVenuesExploreItems {
    FourSquareVenuesExploreItemsVenues venue;

    public FourSquareVenuesExploreItems(FourSquareVenuesExploreItemsVenues venue) {
        this.venue = venue;
    }

    public FourSquareVenuesExploreItemsVenues getVenue() {
        return venue;
    }

    public void setVenue(FourSquareVenuesExploreItemsVenues venue) {
        this.venue = venue;
    }
}
