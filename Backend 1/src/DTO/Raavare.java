package DTO;

public class Raavare {

	private int ravareId;
	private String name, supplier;
	
	public Raavare(int ravareId, String name, String supplier) {
		// TODO Auto-generated constructor stub
		this.ravareId = ravareId;
		this.name = name;
		this.supplier = supplier;
	}

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
		return "ravareId: " + ravareId + ", Name: " + name + ", Supplier: " + supplier;
	}
}
