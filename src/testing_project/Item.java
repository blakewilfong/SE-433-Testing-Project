package testing_project;

import java.util.Objects;

public class Item {
	private final String name;
	private final int price; // in cents
	
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {return this.name;}
	
	public int getPrice() {return this.price;}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Item)) return false;
        Item other = (Item) obj;
        return price == other.price && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
