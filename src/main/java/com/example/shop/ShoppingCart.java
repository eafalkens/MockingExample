package com.example.shop;

public class ShoppingCart {
    private double totalPrice = 0;

    public void addItem(String item, double price, int quantity) {
        totalPrice += price * quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
