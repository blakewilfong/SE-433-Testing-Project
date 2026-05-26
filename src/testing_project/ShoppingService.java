package testing_project;

import testing_project.exceptions.ItemValidationException;

public class ShoppingService {
	
	ShoppingCart shoppingCart;
	Customer customer;
	Catalog catalog;

	public ShoppingService(Catalog catalog) {
		this.catalog = catalog;
		this.shoppingCart = new ShoppingCart(catalog);
		this.customer = null;
	}
	
	public void createCustomer(String firstName, String lastName, State state, ShippingType shippingType ) {
		this.customer = new Customer(firstName, lastName, state, shippingType);
	}

	public void addItem(Item item, int quantity) throws ItemValidationException {
		if (quantity <= 0) throw new ItemValidationException("Quantity must be greater than zero");
		if (!catalog.hasItem(item)) throw new ItemValidationException("Item not in catalog");
		shoppingCart.addItem(item, quantity);
	}

	public void removeItem(Item item) throws ItemValidationException {
		if (!shoppingCart.hasItem(item)) throw new ItemValidationException("Cart does not contain " + item.name());
		shoppingCart.removeItem(item);
	}

	public void editQuantity(Item item, int quantity) throws ItemValidationException {
		if (!shoppingCart.hasItem(item)) throw new ItemValidationException("Cart does not contain " + item.name());
		if (quantity <= 0) throw new ItemValidationException("Quantity must be greater than zero");

		shoppingCart.editQuantity(item, quantity);
	}

	public String getTotal(){

		int subTotal = shoppingCart.getSubTotal();
		if (subTotal == 0) return "0";
		int total = subTotal;
		State state = customer.state();
		ShippingType shippingType = customer.shippingType();

		if (state == State.IL || state == State.CA || state == State.NY) {
			int tax = subTotal * 6 / 100;
			total += tax;
		}
		int shippingCost = 0;
		if (shippingType == ShippingType.NEXTDAY) {
			shippingCost = 2500;
		} else if (shippingType == ShippingType.STANDARD && subTotal <= 5000){
			shippingCost = 1000;
		}
		total += shippingCost;

		int dollars = Math.abs(total / 100);
		int cents = Math.abs(total % 100);
		return String.format("$%d.%02d", dollars, cents);
    }

	public String seeContents(){
		return shoppingCart.toString();
	}

	public boolean checkout() {
		int subtotal = shoppingCart.getSubTotal();
        return subtotal >= 100 && subtotal <= 9999999;
    }

	public int getItemCount(){
		return shoppingCart.countItems();
	}

	public void clearCart() {
		shoppingCart.clearCart();
	}

}
