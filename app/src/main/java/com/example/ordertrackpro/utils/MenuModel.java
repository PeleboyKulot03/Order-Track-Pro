package com.example.ordertrackpro.utils;

public class MenuModel {
    private String foodName;
    private double price;
    private int qty, imageUrl;

    public MenuModel(int imageUrl, String foodName, double price, int qty) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.price = price;
        this.qty = qty;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }
}
