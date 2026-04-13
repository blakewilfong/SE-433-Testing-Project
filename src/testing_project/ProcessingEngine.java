package testing_project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProcessingEngine {
	
	ShoppingCart shoppingCart;
	Customer customer;

	public ProcessingEngine(Catalog catalog) {
		this.shoppingCart = new ShoppingCart(catalog);
		this.customer = null;
	}
	
	public void createCustomer(String firstName, String lastName, State state, ShippingType shippingType ) {
		this.customer = new Customer(firstName, lastName, state, shippingType);
	}

}
