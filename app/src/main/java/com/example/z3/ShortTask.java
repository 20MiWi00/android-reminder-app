package com.example.z3;

import android.net.Uri;

import java.io.Serializable;

public class ShortTask implements Serializable {

    private String title;
    private String description;
    private String category;
    private String photo;

    public ShortTask(String title, String description, String category, String photo) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}