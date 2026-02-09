package com.example;

import com.example.shop.ShoppingCart;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void shouldRemoveItem() {
        String item = "Umbrella";
        double price = 2.99;
        int quantity = 1;

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(item, price, quantity);
        shoppingCart.removeItem(item);

        assertTrue(shoppingCart.isEmpty());
    }

    @Test
    void shouldCalculateTotalPrice() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem("Umbrella", 2.99, 1);
        shoppingCart.addItem("Apple", 1, 3);

        assertEquals(5.99, shoppingCart.getTotalPrice(), 0.01);
    }

    @Test
    void shouldApplyDiscount() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem("Umbrella", 10.00, 1);
        shoppingCart.applyDiscount(50);

        assertEquals(5.00, shoppingCart.getTotalPrice(), 0.01);
    }

    @Test
    void shouldUpdateQuantity() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem("Umbrella", 5.00, 1);
        shoppingCart.updateQuantity("Umbrella", 4);

        assertEquals(20.00, shoppingCart.getTotalPrice(), 0.01);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuantityIsZero() {
        ShoppingCart shoppingCart = new ShoppingCart();

        assertThatThrownBy(() ->
                shoppingCart.addItem("Pear", 2, 0)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenQuantityIsNegative() {
        ShoppingCart shoppingCart = new ShoppingCart();

        assertThatThrownBy(() ->
                shoppingCart.addItem("Pear", 2, -1)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void removingItemThatDoesNotExistShouldNotThrowException() {
        ShoppingCart shoppingCart = new ShoppingCart();

        assertThatCode(() ->
                shoppingCart.removeItem("Banana")
        ).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDiscountIsOver100() {
        ShoppingCart shoppingCart = new ShoppingCart();

        assertThatThrownBy(() ->
                shoppingCart.applyDiscount(150)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

//Steg 8: Bestäm kantfall (edge cases)
//rabatt över 100%
//tom kundvagn totalpris