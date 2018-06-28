package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class FourSquareVenuesSearch {

    FourSquareVenuesSearchResponses response;

    public FourSquareVenuesSearch( FourSquareVenuesSearchResponses response) {
        this.response = response;
    }

    public FourSquareVenuesSearchResponses getResponse() {
        return response;
    }

    public void setResponse(FourSquareVenuesSearchResponses response) {
        this.response = response;
    }
}
