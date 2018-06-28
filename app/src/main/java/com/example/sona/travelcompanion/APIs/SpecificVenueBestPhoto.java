package com.example.sona.travelcompanion.APIs;

/**
 * Created by sona on 6/28/2018.
 */

public class SpecificVenueBestPhoto {

    String prefix;
    String suffix;
    int width;
    int height;

    public SpecificVenueBestPhoto(String prefix, String suffix, int width, int height) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.width = width;
        this.height = height;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
