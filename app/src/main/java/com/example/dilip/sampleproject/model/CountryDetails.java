package com.example.dilip.sampleproject.model;

/**
 * Created by Dilip on 28-05-2016.
 */
public class CountryDetails {

    private String title,description, thumbnailUrl;

    public CountryDetails() {
    }

    public CountryDetails(String name, String description, String thumbnailUrl) {
        this.title = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
