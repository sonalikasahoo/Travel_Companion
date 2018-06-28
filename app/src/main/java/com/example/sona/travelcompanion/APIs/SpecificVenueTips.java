package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueTips {

    ArrayList<SpecificVenueGroups> groups;

    public SpecificVenueTips(ArrayList<SpecificVenueGroups> groups) {
        this.groups = groups;
    }

    public ArrayList<SpecificVenueGroups> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<SpecificVenueGroups> groups) {
        this.groups = groups;
    }
}
