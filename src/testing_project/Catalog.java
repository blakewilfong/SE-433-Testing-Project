package testing_project;

import java.util.HashMap;

public class Catalog {
	
	//hard-coded catalog to test with.  can be replaced as needed.
	
	private HashMap<Item, Integer> catalog;  // title, price
	
	public Catalog() {
		
		this.catalog = new HashMap<>();
		catalog.put(new Item("APPLE"), 99);
		catalog.put(new Item("ORANGE"), 79);
		catalog.put(new Item("PEAR"), 125);
		
//		catalog.put(new Item("Nintendo GameBoy"), 8999);
//		catalog.put(new Item("Nintendo Entertainment System"), 17999);
//		catalog.put(new Item("Super Mario Bros."), 2999);
//		catalog.put(new Item("Super Mario Bros. 3"), 3999);
//		catalog.put(new Item("The Legend of Zelda"), 3999);
//		catalog.put(new Item("Metroid"), 3999);
//		catalog.put(new Item("Contra"), 3999);
//		catalog.put(new Item("Fester's Quest"), 1999);
//		catalog.put(new Item("Kirby's Dream Land"), 2999);
//		catalog.put(new Item("The Legend of Zelda: Link's Awakening"), 2999);
//		catalog.put(new Item("Castlevania II: Belmont's Revenge"), 2999);
//		catalog.put(new Item("Batman Forever"), 1499);
//		catalog.put(new Item("Super Mario Land 2: 6 Golden Coins"), 2999);
	}
	
	public int getPrice(Item item) {
		return this.catalog.get(item);
	}
	
	public boolean hasItem(Item item) {
		if (catalog.containsKey(item)) return true;
		return false;
	}

}
