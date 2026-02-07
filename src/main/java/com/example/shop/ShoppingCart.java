package com.example.shop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Item>  items = new ArrayList<>();

    private static class Item {
        String name;
        double price;
        int quantity;

        Item(String item, double price, int quantity) {
            this.name = item;
            this.price = price;
            this.quantity = quantity;
        }
    }

    public void addItem(String name, double price, int quantity) {
        items.add(new Item(name, price, quantity));
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.price * item.quantity;
        }
        return totalPrice;
    }

    public void removeItem(String name) {
        items.removeIf(item -> item.name.equals(name));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void applyDiscount(double percentage) {
        for (Item item : items) {
            item.price *= (1 - percentage / 100);
        }
    }

    public void updateQuantity(String name, int quantity) {
        for (Item item : items) {
            if (item.name.equals(name)) {
                item.quantity = quantity;
            }
        }
    }
}
