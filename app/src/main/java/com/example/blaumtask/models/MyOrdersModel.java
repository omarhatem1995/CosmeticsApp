package com.example.blaumtask.models;

public class MyOrdersModel {
    private String itemName;
    private String itemCategory , itemID;
    private double itemPrice ;
    private int itemCount;
    private int itemImage;
    private int minus , plus , numberCounter;

    public MyOrdersModel(){

    }

    public MyOrdersModel(String itemName, double itemPrice , String itemID , int itemCount){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemID = itemID;
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemImage() {
        return itemImage;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public int getMinus() {
        return minus;
    }

    public void setMinus(int minus) {
        this.minus = minus;
    }

    public int getPlus() {
        return plus;
    }

    public void setPlus(int plus) {
        this.plus = plus;
    }

    public int getNumberCounter() {
        return numberCounter;
    }

    public void setNumberCounter(int numberCounter) {
        this.numberCounter = numberCounter;
    }
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

}
