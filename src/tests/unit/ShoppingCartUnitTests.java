package tests.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing_project.Catalog;
import testing_project.Item;
import testing_project.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

//methods:
//hasItem()
//addItem()
//removeItem()
//editQuantity()
//getSubTotal()
//toString()
//clearCart()
//countItems()

public class ShoppingCartUnitTests {

    private ShoppingCart cart;
    private Item apple;
    private Item pear;


    @BeforeEach
    void setUp() {
        Catalog catalog = new Catalog();
        cart = new ShoppingCart(catalog);
        apple = new Item("APPLE");
        pear = new Item("PEAR");
    }

    //black box tests

    @Test
    void newCartIsEmpty() {
        assertEquals(0, cart.countItems());
        assertEquals(0, cart.getSubTotal());
        assertFalse(cart.hasItem(apple));
        assertEquals("Cart is empty", cart.toString());
    }

    @Test
    void addItemToCart() {
        cart.addItem(apple, 2);

        assertTrue(cart.hasItem(apple));
        assertEquals(2, cart.countItems());
        assertEquals(198, cart.getSubTotal());
    }

    @Test
    void removeItemFromCart() {
        cart.addItem(apple, 2);
        cart.removeItem(apple);

        assertFalse(cart.hasItem(apple));
        assertEquals(0, cart.countItems());
        assertEquals(0, cart.getSubTotal());
    }

    @Test
    void editQuantityChangesQuantityWhenQuantityIsPositive() {
        cart.addItem(apple, 2);
        cart.editQuantity(apple, 5);

        assertTrue(cart.hasItem(apple));
        assertEquals(5, cart.countItems());
        assertEquals(495, cart.getSubTotal());
    }

    @Test
    void clearCartRemovesAllItems() {
        cart.addItem(apple, 2);
        cart.addItem(pear, 1);
        cart.clearCart();

        assertEquals(0, cart.countItems());
        assertEquals(0, cart.getSubTotal());
        assertFalse(cart.hasItem(apple));
        assertFalse(cart.hasItem(pear));
    }

    @Test
    void toStringReturnContentsAndSubtotal() {
        cart.addItem(apple, 2);
        cart.addItem(pear, 1);
        String result = cart.toString();

        assertTrue(result.contains("APPLE, 2, $1.98"));
        assertTrue(result.contains("PEAR, 1, $1.25"));
        assertTrue(result.contains("Subtotal.........$3.23"));
    }

    //white box tests

    @Test
    void addItemIncreasesQuantityWhenItemInCart() {
        cart.addItem(apple, 1);
        cart.addItem(apple, 2);

        assertTrue(cart.hasItem(apple));
        assertEquals(3, cart.countItems());
        assertEquals(297, cart.getSubTotal());
    }


    @Test
    void removeItemDoesNothingWhenItemNotInCart() {
        cart.removeItem(apple);

        assertFalse(cart.hasItem(apple));
        assertEquals(0, cart.countItems());
        assertEquals(0, cart.getSubTotal());
    }

    @Test
    void editQuantityRemovesItemWhenQuantityIsZero() {
        cart.addItem(apple, 2);
        cart.editQuantity(apple, 0);

        assertFalse(cart.hasItem(apple));
        assertEquals(0, cart.countItems());
        assertEquals(0, cart.getSubTotal());
    }

    @Test
    void editQuantityDoesNothingWhenQuantityIsNegative() {
        cart.addItem(apple, 2);
        cart.editQuantity(apple, -1);

        assertTrue(cart.hasItem(apple));
        assertEquals(2, cart.countItems());
        assertEquals(198, cart.getSubTotal());
    }
}
