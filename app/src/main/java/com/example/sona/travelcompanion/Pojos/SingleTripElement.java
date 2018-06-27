package com.example.sona.travelcompanion.Pojos;

/**
 * Created by sona on 6/27/2018.
 */

public class SingleTripElement {
    String title;
    String list;

    public SingleTripElement(String title, String list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
