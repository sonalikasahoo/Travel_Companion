package com.example.sona.travelcompanion.Pojos;

/**
 * Created by sona on 6/30/2018.
 */

public class TripPhotosElements {

    private String photoUrl;
    private String caption;

    public TripPhotosElements(String photoUrl, String caption) {
        this.photoUrl = photoUrl;
        this.caption = caption;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
