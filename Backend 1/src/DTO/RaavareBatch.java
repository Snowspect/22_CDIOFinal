package DTO;

public class RaavareBatch {
	/** raavare batch id i området 1-99999999. Vælges af brugerne */  
	String rbId;                     
	/** raavare id i området 1-99999999 vælges af brugerne */
	String raavareId;             
	/** mængde på lager */
	double maengde;
	
	public RaavareBatch() {
	}
	
	public String getRbId() {
		return rbId;
	}
	public void setRbId(String rbId) {
		this.rbId = rbId;
	}
	public String getRaavareId() {
		return raavareId;
	}
	public void setRaavareId(String raavareId) {
		this.raavareId = raavareId;
	}
	public double getMaengde() {
		return maengde;
	}
	public void setMaengde(double maengde) {
		this.maengde = maengde;
	}
	public String toString()
	{
		return "raavareBatchId: " + rbId + ", raavareId: " + raavareId + ", Maengde: " + maengde;
	}
}

