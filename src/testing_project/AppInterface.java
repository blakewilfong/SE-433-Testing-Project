package testing_project;

import testing_project.exceptions.ItemValidationException;

import java.util.Scanner;

public class AppInterface {
	private final ProcessingEngine engine;
    private final Scanner sc;

    public AppInterface(ProcessingEngine engine) {
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

        String input;
        while (true) {
            System.out.println("Available user actions: ");
            System.out.println("Add item to the shopping cart");
            System.out.println("Get current total");
            System.out.println("See contents of shopping cart");
            System.out.println("Edit quantity of items in shopping cart");
            System.out.println("Remove items from shopping cart");
            System.out.println("Quit");
            System.out.println("Enter the first word of the action you want to do");
            System.out.print("> ");

            input = sc.nextLine().toLowerCase().split("\\s+")[0];

            if (input.equals("add")) {
                System.out.println("Enter the item name you want to add to your order");
                String itemName = sc.nextLine();
                System.out.println("Enter the quantity you want to order");
                int quantity = Integer.parseInt(sc.nextLine());
                try {
                    engine.shoppingCart.addItem(new Item(itemName), quantity);
                    System.out.println(quantity + " " + itemName + " added to order");
                } catch (ItemValidationException e){
                    System.out.println(e.getMessage());
                }
            }
            else if (input.equals("quit")) {
                break;
            }


        }
    }
	

}
