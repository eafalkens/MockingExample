package com.example.shop;

public class ShoppingCart {
    private double totalPrice = 0;
    private int itemCount = 0;

    public void addItem(String item, double price, int quantity) {
        totalPrice += price * quantity;
        itemCount++;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void removeItem(String item, double price, int quantity) {
        totalPrice -= price * quantity;
        itemCount--;
    }
    public boolean isEmpty() {
        return itemCount == 0;
    }
}
