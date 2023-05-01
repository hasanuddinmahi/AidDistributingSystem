

public class Aid {
	// attribute store name and quantity
	private String name;
	private int quantity;
	
	//constructor
	public Aid(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	//getters and setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
