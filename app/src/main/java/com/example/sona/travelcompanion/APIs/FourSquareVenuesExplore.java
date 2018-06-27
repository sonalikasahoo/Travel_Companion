package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/27/2018.
 */

public class FourSquareVenuesExplore {
    FourSquareExploreMeta meta;

    public FourSquareVenuesExplore(FourSquareExploreMeta meta) {
        this.meta = meta;
    }

    public FourSquareExploreMeta getMeta() {
        return meta;
    }

    public void setMeta(FourSquareExploreMeta meta) {
        this.meta = meta;
    }
}
