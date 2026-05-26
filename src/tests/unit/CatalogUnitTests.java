package tests.unit;

import org.junit.jupiter.api.Test;
import testing_project.Catalog;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import testing_project.Item;

public class CatalogUnitTests {

    private Catalog catalog;

    @BeforeEach
    void createCatalog() {
        catalog = new Catalog();
    }

    @Test
    void hasItemReturnsTrueGoodItem() {
        assertTrue(catalog.hasItem(new Item("APPLE")));
    }

    @Test
    void hasItemReturnsFalseBadItem() {
        assertFalse(catalog.hasItem(new Item("BANANA")));
    }

    @Test
    void getPriceReturnsKnownItemPrice() {
        assertEquals(99, catalog.getPrice(new Item("APPLE")));
    }

}
