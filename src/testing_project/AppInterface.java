package testing_project;

import testing_project.exceptions.ItemValidationException;

import java.util.Scanner;

public class AppInterface {
	private final Engine engine;
    private final Scanner sc;

    public AppInterface(Engine engine) {
        this.engine = engine;
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
                System.out.println("Great!");
                break;
            } else {
                System.out.println("Let's try this again");
            }
        }
        engine.createCustomer(firstName, lastName, state, shippingType);
    }

    public void readCommands() {
        String command = null;

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
                int quantity = Integer.parseInt(sc.nextLine());
                try {
                    engine.addItem(new Item(itemName), quantity);
                    System.out.println(quantity + " " + itemName + " added to order");
                } catch (ItemValidationException e){
                    System.out.println(e.getMessage());
                }
            }
            else if (command.equals("total")){
                System.out.println("Your current total including tax and shipping is " + engine.getTotal());
            }
            else if (command.equals("cart")){
                System.out.println("Item, Quantity, Total");
                System.out.println(engine.seeContents());
            }
            else if (command.equals("edit")){
                System.out.println(engine.seeContents());
                System.out.println("Enter the item name you want to edit on your order");
                System.out.println("> ");
                String itemName = sc.nextLine().toUpperCase();
                System.out.println("Enter the quantity you want to order");
                System.out.println("> ");
                int quantity = Integer.parseInt(sc.nextLine());
                try {
                    engine.editQuantity(new Item(itemName), quantity);
                    System.out.println(itemName + " quantity updated to " + quantity);
                } catch (ItemValidationException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (command.equals("remove")){
                System.out.println(engine.seeContents());
                System.out.println("Enter the item name you want to edit on your order");
                System.out.println("> ");
                String itemName = sc.nextLine().toUpperCase();
                try {
                    engine.removeItem(new Item(itemName));
                } catch (ItemValidationException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(itemName + " removed from cart");
            }
            else if (command.equals("checkout")){
                System.out.println("transaction completed");
                engine.shoppingCart.clearCart();
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
