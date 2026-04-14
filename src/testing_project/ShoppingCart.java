package testing_project;

import java.util.HashMap;

public class ShoppingCart {

    private final HashMap<Item, Integer> cartMap;
    private final Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.cartMap = new HashMap<>();
        this.catalog = catalog;
    }

    public boolean hasItem(Item item) {
        return cartMap.containsKey(item);
    }

    public void addItem(Item item, int quantity){
        if (cartMap.containsKey(item)) {
            int newQty = cartMap.get(item) + quantity;
            cartMap.put(item, newQty);
        } else {
            cartMap.put(item, quantity);
        }
    }

    public void removeItem(Item item){
        cartMap.remove(item);
    }

    public void editQuantity(Item item, int quantity){
        if (quantity == 0) {
            cartMap.remove(item);
        } else if (quantity > 0) {
            cartMap.put(item, quantity);
        }
    }

    public int getSubTotal() {
        int subTotal = 0;
        for (Item item : cartMap.keySet()) {
            subTotal +=  cartMap.get(item) * catalog.getPrice(item);
        }
        return subTotal;
    }
    @Override
    public String toString() {
        if (cartMap.isEmpty()) return "Cart is empty";
        int total = 0;
        StringBuilder sb = new StringBuilder();
        String totalString = "";
        for (Item item : cartMap.keySet()) {
            int quantity = cartMap.get(item);
            int itemSubtotal = (catalog.getPrice(item) * quantity);
            total += itemSubtotal;
            String priceString = String.format("$%.2f", itemSubtotal / 100.0);
            sb.append(item.getName())
                    .append(", ")
                    .append(quantity)
                    .append(", ")
                    .append(priceString)
                    .append("\n");
        }
        totalString = String.format("$%.2f", total / 100.0);
        sb.append("Subtotal.........").append(totalString).append("\n");
        return sb.toString();
    }

    public void clearCart() {
        cartMap.clear();
    }

    public int countItems(){
        int totalItems = 0;
        for (int num: cartMap.values()){
            totalItems += num;
        }
        return totalItems;
    }
}
