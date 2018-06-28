package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/27/2018.
 */

public class FourSquareVenuesExplore {
    FourSquareVenuesExploreMeta meta;
    FourSquareVenuesExploreResponses response;

    public FourSquareVenuesExplore(FourSquareVenuesExploreMeta meta, FourSquareVenuesExploreResponses response) {
        this.meta = meta;
        this.response = response;
    }

    public FourSquareVenuesExploreMeta getMeta() {
        return meta;
    }

    public void setMeta(FourSquareVenuesExploreMeta meta) {
        this.meta = meta;
    }

    public FourSquareVenuesExploreResponses getResponse() {
        return response;
    }

    public void setResponse(FourSquareVenuesExploreResponses response) {
        this.response = response;
    }
}
