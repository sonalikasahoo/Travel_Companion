package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class FourSquareVenuesSearchResponses {

    ArrayList<FourSquareVenuesSearchElement> venues;

    public FourSquareVenuesSearchResponses(ArrayList<FourSquareVenuesSearchElement> venues) {
        this.venues = venues;
    }

    public ArrayList<FourSquareVenuesSearchElement> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<FourSquareVenuesSearchElement> venues) {
        this.venues = venues;
    }
}
