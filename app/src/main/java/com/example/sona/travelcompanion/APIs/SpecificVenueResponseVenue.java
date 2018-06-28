package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueResponseVenue {

    String name;
    SpecificVenueLikes likes;
    SpecificVenueTips tips;
    SpecificVenueBestPhoto bestPhoto;
    SpecificVenueLocation location;

    public SpecificVenueResponseVenue(String name, SpecificVenueLikes likes, SpecificVenueTips tips,
                                      SpecificVenueBestPhoto bestPhoto, SpecificVenueLocation location) {
        this.name = name;
        this.likes = likes;
        this.tips = tips;
        this.bestPhoto = bestPhoto;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecificVenueLikes getLikes() {
        return likes;
    }

    public void setLikes(SpecificVenueLikes likes) {
        this.likes = likes;
    }

    public SpecificVenueTips getTips() {
        return tips;
    }

    public void setTips(SpecificVenueTips tips) {
        this.tips = tips;
    }

    public SpecificVenueBestPhoto getBestPhoto() {
        return bestPhoto;
    }

    public void setBestPhoto(SpecificVenueBestPhoto bestPhoto) {
        this.bestPhoto = bestPhoto;
    }

    public SpecificVenueLocation getLocation() {
        return location;
    }

    public void setLocation(SpecificVenueLocation location) {
        this.location = location;
    }
}
