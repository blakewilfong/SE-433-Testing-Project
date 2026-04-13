package testing_project;

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

        String command;
        while (true) {
            System.out.print("> ");
            command = sc.nextLine();
            if (command.compareTo("quit") == 0) {
                break;
            }
        }
    }
	

}
