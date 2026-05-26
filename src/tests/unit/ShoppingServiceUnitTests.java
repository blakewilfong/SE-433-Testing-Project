package tests.unit;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing_project.*;
import testing_project.exceptions.ItemValidationException;

import static org.junit.jupiter.api.Assertions.*;


public class ShoppingServiceUnitTests {


    private ShoppingService service;
    private Item apple;
    private Item pear;
    private Item banana;
    private Item goldenApple;
    private Item kiwi;

    @BeforeEach
    void setUp() {
        service = new ShoppingService(new Catalog());
        service.createCustomer("Doyle", "Brunson", State.TX, ShippingType.STANDARD);

        apple = new Item("APPLE");
        pear = new Item("PEAR");
        banana = new Item("BANANA");
        goldenApple = new Item("GOLDEN APPLE");
        kiwi = new Item("KIWI");
    }

    // Black box tests
    @Test
    void addValidItemIncreasesItemCount() throws ItemValidationException {
        service.addItem(apple, 2);

        assertEquals(2, service.getItemCount());
    }

    @Test
    void addItemWithZeroQtyThrows() {
        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.addItem(apple, 0)
        );
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void addItemWithNegQtyThrows() {
        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.addItem(apple, -1)
        );
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void addItemNotInCatalogThrowsException() {
        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.addItem(banana, 1)
        );

        assertEquals("Item not in catalog", exception.getMessage());
    }

    @Test
    void removeExistingItemRemovesItFromCart() throws ItemValidationException {
        service.addItem(apple, 2);
        service.removeItem(apple);

        assertEquals(0, service.getItemCount());
        assertEquals("Cart is empty", service.seeContents());
    }

    @Test
    void removeItemNotInCartThrowsException() {
        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.removeItem(apple)
        );

        assertEquals("Cart does not contain APPLE", exception.getMessage());
    }

    @Test
    void editQtyChangesItemQty() throws ItemValidationException {
        service.addItem(apple, 2);
        service.editQuantity(apple, 5);

        assertEquals(5, service.getItemCount());
        assertTrue(service.seeContents().contains("APPLE, 5, $4.95"));
    }

    @Test
    void editQtyForItemNotInCartThrows() {
        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.editQuantity(apple, 5)
        );

        assertEquals("Cart does not contain APPLE", exception.getMessage());
    }

    @Test
    void editQtyZeroThrows() throws ItemValidationException {
        service.addItem(apple, 2);

        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.editQuantity(apple, 0)
        );

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void editQtyWithNegNumberThrowsException() throws ItemValidationException {
        service.addItem(apple, 2);

        ItemValidationException exception = assertThrows(
                ItemValidationException.class,
                () -> service.editQuantity(apple, -1)
        );

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @Test
    void seeContentsReturnsCartContents() throws ItemValidationException {
        service.addItem(apple, 2);

        String result = service.seeContents();

        assertTrue(result.contains("APPLE, 2, $1.98"));
        assertTrue(result.contains("Subtotal.........$1.98"));
    }

    @Test
    void getTotalReturnsZeroWhenCartIsEmpty() {
        assertEquals("0", service.getTotal());
    }

    @Test
    void getTotalForNonTaxStateWithStandardShippingUnderFiftyDollars() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.TX, ShippingType.STANDARD);
        service.addItem(apple, 2);

        assertEquals("$11.98", service.getTotal());
    }

    @Test
    void getTotalForTaxStateWithStandardShippingUnderFiftyDollars() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.IL, ShippingType.STANDARD);
        service.addItem(apple, 2);

        assertEquals("$12.09", service.getTotal());
    }

    @Test
    void getTotalWithNextDayShippingAddsTwentyFiveDollars() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.TX, ShippingType.NEXTDAY);
        service.addItem(apple, 2);

        assertEquals("$26.98", service.getTotal());
    }

    @Test
    void checkoutReturnsFalseWhenSubtotalIsBelowOneDollar() throws ItemValidationException {
        service.addItem(apple, 1);

        assertFalse(service.checkout());
    }

    @Test
    void checkoutReturnsTrueWhenSubtotalIsAtLeastOneDollar() throws ItemValidationException {
        service.addItem(pear, 1);

        assertTrue(service.checkout());
    }


    //White box tests

    @Test
    void getTotalForCaliforniaAddsTax() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.CA, ShippingType.STANDARD);
        service.addItem(apple, 2);

        assertEquals("$12.09", service.getTotal());
    }

    @Test
    void getTotalForNewYorkAddsTax() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.NY, ShippingType.STANDARD);
        service.addItem(apple, 2);

        assertEquals("$12.09", service.getTotal());
    }

    @Test
    void standardShippingAtExactlyFiftyDollarsStillChargesShipping() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.TX, ShippingType.STANDARD);
        service.addItem(pear, 40);

        assertEquals("$60.00", service.getTotal());
    }

    @Test
    void standardShippingOverFiftyDollarsIsFree() throws ItemValidationException {
        service.createCustomer("Blake", "Wilfong", State.TX, ShippingType.STANDARD);
        service.addItem(pear, 41);

        assertEquals("$51.25", service.getTotal());
    }

    @Test
    void checkoutReturnsFalseBelowMinimumAllowedSubtotal() throws ItemValidationException {
        // apples costs $0.99
        service.addItem(apple, 1);
        assertFalse(service.checkout());
    }

    @Test
    void checkoutReturnsTrueAtMinimumAllowedSubtotal() throws ItemValidationException {
        // kiwis cost exactly $1.00
        service.addItem(kiwi, 1);
        assertTrue(service.checkout());
    }

    @Test
    void checkoutReturnsTrueAtMaximumAllowedSubtotal() throws ItemValidationException {
        // goldenApple costs exactly $99,999.99
        service.addItem(goldenApple, 1);
        assertTrue(service.checkout());
    }

    @Test
    void checkoutReturnsFalseAboveMaximumAllowedSubtotal() throws ItemValidationException {
        // 2 GOLDEN APPLES cost $199,999.98, well above the maximum
        service.addItem(goldenApple, 2);
        assertFalse(service.checkout());
    }

}
