package com.example.blaumtask.models;

public class ReviewsModel {

    private String reviewsName;
    private String reviewsDate;
    private float reviewsRating;
    private String reviewsMessage;
    private int reviewsImage;

    public String getReviewsName() {
        return reviewsName;
    }

    public void setReviewsName(String reviewsName) {
        this.reviewsName = reviewsName;
    }

    public String getReviewsDate() {
        return reviewsDate;
    }

    public void setReviewsDate(String reviewsDate) {
        this.reviewsDate = reviewsDate;
    }

    public float getReviewsRating() {
        return reviewsRating;
    }

    public void setReviewsRating(float reviewsRating) {
        this.reviewsRating = reviewsRating;
    }

    public String getReviewsMessage() {
        return reviewsMessage;
    }

    public void setReviewsMessage(String reviewsMessage) {
        this.reviewsMessage = reviewsMessage;
    }

    public int getReviewsImage() {
        return reviewsImage;
    }

    public void setReviewsImage(int reviewsImage) {
        this.reviewsImage = reviewsImage;
    }
}
