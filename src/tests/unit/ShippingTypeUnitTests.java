package tests.unit;

import org.junit.jupiter.api.Test;
import testing_project.ShippingType;

import static org.junit.jupiter.api.Assertions.*;

public class ShippingTypeUnitTests {

    @Test
    void shippingTypeContainsStandardAndNextDay() {
        assertEquals(ShippingType.STANDARD, ShippingType.valueOf("STANDARD"));
        assertEquals(ShippingType.NEXTDAY, ShippingType.valueOf("NEXTDAY"));
    }
}