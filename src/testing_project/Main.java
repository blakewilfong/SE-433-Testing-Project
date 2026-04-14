package testing_project;

public class Main {
	
	public static void main( String[] args) {

		Catalog catalog = new Catalog();
		Engine engine = new Engine(catalog);
		AppInterface appInterface = new AppInterface(engine);
		appInterface.setUpNewCustomer();
		appInterface.readCommands();
	}
}
