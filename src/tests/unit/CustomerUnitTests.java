package tests.unit;

import org.junit.jupiter.api.Test;
import testing_project.Customer;
import testing_project.ShippingType;
import testing_project.State;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerUnitTests {

    @Test
    void constructorStoresInfo() {
        Customer customer = new Customer("Blake", "Wilfong", State.IL, ShippingType.STANDARD);

        assertEquals("Blake", customer.firstName());
        assertEquals("Wilfong", customer.lastName());
        assertEquals(State.IL, customer.state());
        assertEquals(ShippingType.STANDARD, customer.shippingType());
    }

    @Test
    void sameValuesAreEqual() {
        Customer customer1 = new Customer("Blake", "Wilfong", State.IL, ShippingType.STANDARD);
        Customer customer2 = new Customer("Blake", "Wilfong", State.IL, ShippingType.STANDARD);

        assertEquals(customer1, customer2);
    }

    @Test
    void differentValuesAreNotEqual() {
        Customer customer1 = new Customer("Blake", "Wilfong", State.IL, ShippingType.STANDARD);
        Customer customer2 = new Customer("Blake", "Wilfong", State.IN, ShippingType.STANDARD);

        assertNotEquals(customer1, customer2);
    }

}
