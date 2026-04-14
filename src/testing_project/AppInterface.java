package testing_project;

import testing_project.exceptions.ItemValidationException;

import java.util.Scanner;

public class AppInterface {
	private final ShoppingService shoppingService;
    private final Scanner sc;

    public AppInterface(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
        this.sc = new Scanner(System.in);
    }

    public void setUpNewCustomer() {
        String firstName;
        String lastName;
        ShippingType shippingType;
        State state;
        while (true) {
            shippingType = null;
            state = null;
            System.out.println("Please enter your first name");
            System.out.print("> ");
            firstName = sc.nextLine().trim();
            System.out.println("Please enter your last name");
            System.out.print("> ");
            lastName = sc.nextLine().trim();

            while (state == null) {
                System.out.println("What state do you want your order shipped to? Please enter the 2 letter state abbreviation");
                System.out.print("> ");
                String userInputState = sc.nextLine().trim().toUpperCase();
                try {
                    state = State.valueOf(userInputState);
                } catch (IllegalArgumentException e) {
                    System.out.println("Not a valid state");
                }
            }
            while (shippingType == null) {
                System.out.println("Enter STD for standard shipping or NEXT for next-day shipping");
                System.out.print("> ");
                String userInputShipping = sc.nextLine().trim().toUpperCase();
                if (userInputShipping.equals("STD"))
                    shippingType = ShippingType.STANDARD;
                else if (userInputShipping.equals("NEXT"))
                    shippingType = ShippingType.NEXTDAY;
                else {
                    System.out.println("Not a valid shipping type");
                }
            }
            System.out.println("Your name is " + firstName + " " + lastName);
            System.out.println("You want " + shippingType + " shipping to " + state.getFullName());
            System.out.println("Is that correct? (Yes/No)");
            String userResponse = sc.nextLine().trim().toUpperCase();
            if (userResponse.equals("YES")) {
                System.out.println("Thanks, " + firstName + "!");
                break;
            } else {
                System.out.println("Let's try this again");
            }
        }
        shoppingService.createCustomer(firstName, lastName, state, shippingType);
    }

    public void readCommands() {
        String command;
        int quantity;
        while (true) {

            System.out.println("Enter 'help' to see available user actions: ");
            System.out.print("> ");
            command = sc.nextLine().toLowerCase().split("\\s+")[0];

            if (command.equals("help")){
                System.out.println("Available user actions: ");
                System.out.println("ADD: add item to the shopping cart");
                System.out.println("TOTAL: get current total");
                System.out.println("CART: see contents of shopping cart");
                System.out.println("EDIT: edit quantity of an item in shopping cart");
                System.out.println("REMOVE: remove item from shopping cart");
                System.out.println("CHECKOUT: complete the transaction for the items in your cart");
                System.out.println("QUIT");
                System.out.println("Enter the action you want to perform");
                System.out.print("> ");
            }
            else if (command.equals("add")) {
                System.out.println("Enter the item name you want to add to your order");
                System.out.println("> ");
                String itemName = sc.nextLine().toUpperCase();
                System.out.println("Enter the quantity you want to order");
                System.out.println("> ");

                try {
                    quantity = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Quantity must be an integer");
                    continue;
                }
                try {
                    shoppingService.addItem(new Item(itemName), quantity);
                    System.out.println("Cart now has " + shoppingService.getItemCount() + " items");
                } catch (ItemValidationException e){
                    System.out.println(e.getMessage());
                }
            }
            else if (command.equals("total")){
                System.out.println("Your current total including tax and shipping is " + shoppingService.getTotal());
            }
            else if (command.equals("cart")){
                String cartMessage = shoppingService.seeContents();
                if (cartMessage.equals("Cart is empty")){
                    System.out.println(cartMessage);
                } else {
                    System.out.println("Item, Quantity, Total");
                    System.out.println(cartMessage);
                }
            }
            else if (command.equals("edit")){
                System.out.println(shoppingService.seeContents());
                System.out.println("Enter the item name you want to edit on your order");
                System.out.println("> ");
                String itemName = sc.nextLine().toUpperCase();
                System.out.println("Enter the quantity you want to order");
                System.out.println("> ");
                try {
                    quantity = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Quantity must be an integer");
                    continue;
                }
                try {
                    shoppingService.editQuantity(new Item(itemName), quantity);
                    System.out.println(itemName + " quantity updated to " + quantity);
                } catch (ItemValidationException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (command.equals("remove")){
                System.out.println(shoppingService.seeContents());
                System.out.println("Enter the item name you want to edit on your order");
                System.out.println("> ");
                String itemName = sc.nextLine().toUpperCase();
                try {
                    shoppingService.removeItem(new Item(itemName));
                    System.out.println(itemName + " removed from cart");
                } catch (ItemValidationException e) {
                    System.out.println(e.getMessage());
                }

            }
            else if (command.equals("checkout")){
                if (shoppingService.checkout()) {
                    System.out.println("transaction completed");
                    shoppingService.shoppingCart.clearCart();
                } else {
                    System.out.println("Subtotal must be above $1 or below $99,999.99 to checkout");
                }
            }
            else if (command.equals("quit")) {
                break;
            }
            else {
                System.out.println("Command not recognized");
            }


        }
    }
	

}
