package testing_project;

import testing_project.exceptions.ItemValidationException;

import java.util.HashMap;

public class ShoppingCart {

    private final HashMap<Item, Integer> cartMap;
    private final Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.cartMap = new HashMap<>();
        this.catalog = catalog;
    }

    public boolean validateItem(Item item){
        return catalog.hasItem(item);
    }

    public void addItem(Item item, int quantity) throws ItemValidationException {

        if (quantity <= 0) throw new ItemValidationException("Quantity must be greater than zero");
        if (!catalog.hasItem(item)) throw new ItemValidationException("Item not in catalog");

        if (cartMap.containsKey(item)) {
            int newQty = cartMap.get(item) + quantity;
            cartMap.put(item, newQty);
        } else {
            cartMap.put(item, quantity);
        }
    }

    public void removeItem(Item item) throws ItemValidationException {
        if (!cartMap.containsKey(item)) throw new ItemValidationException("Cart does not contain " + item.getName());
        cartMap.remove(item);
        System.out.println(item + " removed from cart");
    }

    public void editQuantity(Item item, int quantity) throws ItemValidationException {
        if (!cartMap.containsKey(item)) throw new ItemValidationException("Cart does not contain " + item.getName());
        if (quantity == 0) {
            cartMap.remove(item);
        } else if (quantity > 0) {
            cartMap.put(item, quantity);
        } else throw new ItemValidationException("Quantity must be greater than or equal to zero");
        System.out.println(item + " quantity updated to " + quantity);
    }

    public String getTotal() {
        int total = 0;
        for (Item item : cartMap.keySet()) {
            total +=  cartMap.get(item) * catalog.getPrice(item);
        }
        int dollars = Math.abs(total / 100);
        int cents = Math.abs(total % 100);
        return String.format("$%d.%02d", dollars, cents);
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
