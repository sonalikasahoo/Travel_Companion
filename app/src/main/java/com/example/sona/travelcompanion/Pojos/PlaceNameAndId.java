package com.example.sona.travelcompanion.Pojos;

/**
 * Created by sona on 6/28/2018.
 */

public class PlaceNameAndId {
    String placeName;
    String id;

    public PlaceNameAndId(String placeName, String id) {
        this.placeName = placeName;
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
