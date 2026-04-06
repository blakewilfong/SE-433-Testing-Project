package testing_project;

public class Item {
	private String name;
	private int price; // in cents
	
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {return this.name;}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public int getPrice() {return this.price;}
	
	public void setPrice(int newPrice) {
		this.price = newPrice;
	}
	
}
