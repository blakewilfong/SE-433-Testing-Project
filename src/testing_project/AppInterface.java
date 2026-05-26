package testing_project;

import testing_project.exceptions.ItemValidationException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class AppInterface {

    private final ShoppingService shoppingService;
    private final Scanner sc;
    private final PrintStream out;

    public AppInterface(ShoppingService shoppingService) {
        this(shoppingService, System.in, System.out);
    }

    public AppInterface(ShoppingService shoppingService, InputStream input, PrintStream output) {
        this.shoppingService = shoppingService;
        this.sc = new Scanner(input);
        this.out = output;
    }

    public void setUpNewCustomer() {
        String firstName;
        String lastName;
        ShippingType shippingType;
        State state;

        while (true) {
            shippingType = null;
            state = null;

            out.println("Please enter your first name");
            out.print("> ");
            firstName = sc.nextLine().trim();

            out.println("Please enter your last name");
            out.print("> ");
            lastName = sc.nextLine().trim();

            while (state == null) {
                out.println("What state do you want your order shipped to? Please enter the 2 letter state abbreviation");
                out.print("> ");
                String userInputState = sc.nextLine().trim().toUpperCase();

                try {
                    state = State.valueOf(userInputState);
                } catch (IllegalArgumentException e) {
                    out.println("Not a valid state");
                }
            }

            while (shippingType == null) {
                out.println("Enter STD for standard shipping or NEXT for next-day shipping");
                out.print("> ");
                String userInputShipping = sc.nextLine().trim().toUpperCase();

                if (userInputShipping.equals("STD")) {
                    shippingType = ShippingType.STANDARD;
                } else if (userInputShipping.equals("NEXT")) {
                    shippingType = ShippingType.NEXTDAY;
                } else {
                    out.println("Not a valid shipping type");
                }
            }

            out.println("Your name is " + firstName + " " + lastName);
            out.println("You want " + shippingType + " shipping to " + state.getFullName());
            out.println("Is that correct? (Yes/No)");

            String userResponse = sc.nextLine().trim().toUpperCase();

            if (userResponse.equals("YES")) {
                out.println("Thanks, " + firstName + "!");
                break;
            } else {
                out.println("Let's try this again");
            }
        }

        shoppingService.createCustomer(firstName, lastName, state, shippingType);
    }

    public void readCommands() {
        String command;
        int quantity;

        while (true) {
            out.println("Enter 'help' to see available user actions: ");
            out.print("> ");

            command = sc.nextLine().trim().toLowerCase().split("\\s+")[0];

            if (command.equals("help")) {
                out.println("Available user actions: ");
                out.println("ADD: add item to the shopping cart");
                out.println("TOTAL: get current total");
                out.println("CART: see contents of shopping cart");
                out.println("EDIT: edit quantity of an item in shopping cart");
                out.println("REMOVE: remove item from shopping cart");
                out.println("CHECKOUT: complete the transaction for the items in your cart");
                out.println("QUIT");
                out.println("Enter the action you want to perform");
                out.print("> ");
            } else if (command.equals("add")) {
                out.println("Enter the item name you want to add to your order");
                out.print("> ");
                String itemName = sc.nextLine().trim().toUpperCase();

                out.println("Enter the quantity you want to order");
                out.print("> ");

                try {
                    quantity = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    out.println("Quantity must be an integer");
                    continue;
                }

                try {
                    shoppingService.addItem(new Item(itemName), quantity);
                    out.println("Cart now has " + shoppingService.getItemCount() + " items");
                } catch (ItemValidationException e) {
                    out.println(e.getMessage());
                }
            } else if (command.equals("total")) {
                out.println("Your current total including tax and shipping is " + shoppingService.getTotal());
            } else if (command.equals("cart")) {
                String cartMessage = shoppingService.seeContents();

                if (cartMessage.equals("Cart is empty")) {
                    out.println(cartMessage);
                } else {
                    out.println("Item, Quantity, Total");
                    out.println(cartMessage);
                }
            } else if (command.equals("edit")) {
                out.println(shoppingService.seeContents());
                out.println("Enter the item name you want to edit on your order");
                out.print("> ");
                String itemName = sc.nextLine().trim().toUpperCase();

                out.println("Enter the quantity you want to order");
                out.print("> ");

                try {
                    quantity = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    out.println("Quantity must be an integer");
                    continue;
                }

                try {
                    shoppingService.editQuantity(new Item(itemName), quantity);
                    out.println(itemName + " quantity updated to " + quantity);
                } catch (ItemValidationException e) {
                    out.println(e.getMessage());
                }
            } else if (command.equals("remove")) {
                out.println(shoppingService.seeContents());
                out.println("Enter the item name you want to remove from your order");
                out.print("> ");
                String itemName = sc.nextLine().trim().toUpperCase();

                try {
                    shoppingService.removeItem(new Item(itemName));
                    out.println(itemName + " removed from cart");
                } catch (ItemValidationException e) {
                    out.println(e.getMessage());
                }
            } else if (command.equals("checkout")) {
                if (shoppingService.checkout()) {
                    out.println("transaction completed");
                    shoppingService.clearCart();
                } else {
                    out.println("Subtotal must be at least $1 and no more than $99,999.99 to checkout");
                }
            } else if (command.equals("quit")) {
                break;
            } else {
                out.println("Command not recognized");
            }
        }
    }
}