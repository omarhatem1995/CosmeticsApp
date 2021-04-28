package com.example.blaumtask.models;

public class RecentsModel {

    private String recentName;
    private String recentPrice;
    private String rating;
    private int image;

    public String getProductName() {
        return recentName;
    }

    public void setProductName(String recentName) {
        this.recentName = recentName;
    }

    public String getProductPrice() {
        return recentPrice;
    }

    public void setProductPrice(String recentPrice) {
        this.recentPrice = recentPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
