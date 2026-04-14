package testing_project;

public class Customer {
    public String firstName;
    public String lastName;
    public State state;
    public ShippingType shippingType;

    public Customer(String firstName, String lastName, State state, ShippingType shippingType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
        this.shippingType = shippingType;
    }
}
