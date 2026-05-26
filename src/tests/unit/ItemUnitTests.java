package tests.unit;

import org.junit.jupiter.api.Test;
import testing_project.*;
import static org.junit.jupiter.api.Assertions.*;


public class ItemUnitTests {

    @Test
    void sameNamesAreEqual() {
        Item item1 = new Item("APPLE");
        Item item2 = new Item("APPLE");
        assertEquals(item1, item2);
    }

    @Test
    void differentNamesAreNotEqual() {
        Item item1 = new Item("APPLE");
        Item item2 = new Item("PEAR");
        assertNotEquals(item1, item2);
    }

    @Test
    void nameReturnsName() {
        Item item = new Item("APPLE");
        assertEquals("APPLE", item.name());
    }
}
