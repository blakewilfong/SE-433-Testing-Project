package testing_project;
import java.util.HashMap;
import java.util.ArrayList;


public class ShoppingCart {
	
	private final HashMap<Item, Integer> cartMap;
	
	public ShoppingCart() {
		this.cartMap = new HashMap<Item, Integer>();
	}
	
	public void addItem(Item item, int quantity) {
		if (cartMap.containsKey(item)) {
			int newQty = cartMap.get(item) + quantity;
			cartMap.put(item, newQty);
		} else {
			cartMap.put(item, quantity);
		}
	}
	
	public void removeItem(Item item, int quantity) {
		if (cartMap.containsKey(item)) {
			int newQty = cartMap.get(item) - quantity;
			if (newQty <= 0) {
				cartMap.remove(item);
			} else {
				cartMap.put(item, newQty);
			}
		}
	}
	
	public int getTotal() {
		int total = 0;
		for (Item item : cartMap.keySet()) {
			total += item.getPrice() * cartMap.get(item);
		}
		return total;
	}
	
	public void seeContents() {
		System.out.println("Item, Quantity, Total");
		for (Item item : cartMap.keySet()) {
			int price = cartMap.get(item);
			String priceString = String.format("$%.2f", price / 100.0);
			System.out.println(item.getName() + ", " + cartMap.get(item) + ", " + priceString);
			
		}
	}
	
	
}
