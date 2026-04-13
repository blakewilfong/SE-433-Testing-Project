package testing_project;

import java.util.HashMap;

public class ShoppingCart {

    private final HashMap<Item, Integer> cartMap;
    private final Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.cartMap = new HashMap<>();
        this.catalog = catalog;
    }

    public void addItem(Item item, int quantity) {
        if (!catalog.hasItem(item)) {
            System.out.println("Item not in catalog");
            return;
        }
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

    public void editQuantity(Item item, int quantity) {
        if (!cartMap.containsKey(item)) {
            System.out.println("Cart does not contain " + item.getName());
            return;
        }
        if (quantity <= 0) {
            cartMap.remove(item);
        } else {
            cartMap.put(item, quantity);
        }
    }

    public int getTotal() {
        int total = 0;
        for (Item item : cartMap.keySet()) {
            total +=  cartMap.get(item) * catalog.getPrice(item);
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
