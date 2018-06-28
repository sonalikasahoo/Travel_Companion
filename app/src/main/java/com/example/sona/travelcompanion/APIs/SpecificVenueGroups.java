package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueGroups {

    ArrayList<SpecificVenueItemsTips> items;

    public SpecificVenueGroups(ArrayList<SpecificVenueItemsTips> items) {
        this.items = items;
    }

    public ArrayList<SpecificVenueItemsTips> getItems() {
        return items;
    }

    public void setItems(ArrayList<SpecificVenueItemsTips> items) {
        this.items = items;
    }
}
