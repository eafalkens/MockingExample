package com.example;

import com.example.shop.ShoppingCart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    @Test
    void shouldAddItemToCart() {
        String item = "Umbrella";
        double price = 2.99;
        int quantity = 1;

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(item, price, quantity);

        assertEquals(2.99, shoppingCart.getTotalPrice(), 0.01);
    }

}
//Testmetoder?:
//shouldIncreaseQuantityWhenAddingSameItem
//shouldRemoveItem
//shouldCalculateTotalPrice
//shouldApplyDiscount
//shouldUpdateQuantity

//Steg 8: Bestäm kantfall (edge cases)
//Du ska ha tester för:
//lägga till item med quantity = 0
//ta bort item som inte finns
//negativ quantity
//rabatt över 100%
//tom kundvagn totalpris

//TDD-regel att minnas
//1. Skriv test (rött)
//2. Skriv minimal kod (grönt)
//3. Refaktorera
//4. Commit