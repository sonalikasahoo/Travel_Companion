package com.example.sona.travelcompanion.Pojos;

/**
 * Created by sona on 6/25/2018.
 */

public class MyPlansElements {
    String tripTitle;
    String destination;
    String hotel;
    String flight_train;

    public MyPlansElements(String tripTitle, String destination, String hotel, String flight_train) {
        this.tripTitle = tripTitle;
        this.destination = destination;
        this.hotel = hotel;
        this.flight_train = flight_train;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getFlight_train() {
        return flight_train;
    }

    public void setFlight_train(String flight_train) {
        this.flight_train = flight_train;
    }
}
