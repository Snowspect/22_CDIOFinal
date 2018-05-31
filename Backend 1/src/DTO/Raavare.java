package DTO;

public class Raavare {

	private String name, supplier;
	private int ravareId;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getRavareId() {
		return ravareId;
	}
	public void setRavareId(int ravareId) {
		this.ravareId = ravareId;
	}
	
	public String toString()
	{
		return "råvareNr: " + ravareId + ", Name: " + name + ", Supplier: " + supplier;
	}
}
