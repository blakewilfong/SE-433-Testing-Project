package testing_project;

public class Customer {
    private String firstName;
    private String lastName;
    private State state;
    private ShippingType shippingType;

    public Customer(String firstName, String lastName, State state, ShippingType shippingType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
        this.shippingType = shippingType;
    }
}
