package com.example.sona.travelcompanion.APIs;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class FourSquareVenuesExploreGroupsElement {

    ArrayList<FourSquareVenuesExploreItems> items;

    public FourSquareVenuesExploreGroupsElement(ArrayList<FourSquareVenuesExploreItems> items) {
        this.items = items;
    }

    public ArrayList<FourSquareVenuesExploreItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<FourSquareVenuesExploreItems> items) {
        this.items = items;
    }
}
