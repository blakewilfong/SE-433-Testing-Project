package testing_project;

public class StoreEngine {
	
	public static void main( String[] args) {

		Catalog catalog = new Catalog();
		ProcessingEngine engine = new ProcessingEngine(catalog);
		AppInterface appInterface = new AppInterface(engine);
		appInterface.setUpNewCustomer();
	}
}
