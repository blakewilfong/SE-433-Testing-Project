package testing_project;

public class Main {
	
	public static void main( String[] args) {

		Catalog catalog = new Catalog();
		ShoppingService shoppingService = new ShoppingService(catalog);
		AppInterface appInterface = new AppInterface(shoppingService);
		appInterface.setUpNewCustomer();
		appInterface.readCommands();
	}
}
