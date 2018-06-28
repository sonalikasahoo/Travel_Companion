package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class FourSquareVenuesExploreResponses {

    ArrayList<FourSquareVenuesExploreGroupsElement> groups;

    public FourSquareVenuesExploreResponses(ArrayList<FourSquareVenuesExploreGroupsElement> groups) {
        this.groups = groups;
    }

    public ArrayList<FourSquareVenuesExploreGroupsElement> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<FourSquareVenuesExploreGroupsElement> groups) {
        this.groups = groups;
    }
}
