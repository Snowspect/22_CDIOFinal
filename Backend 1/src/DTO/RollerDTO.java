package DTO;

public class RollerDTO {
	int oprId;  
	String cpr;
	rolleEnum rolle;
	
	public RollerDTO(int oprId, String cpr, rolleEnum rolle) {
		this.oprId = oprId;
		this.cpr = cpr;
		this.rolle = rolle;
	}

	
	public int getOprId() {
		return oprId;
	}

	public void setOprId(int oprId) {
		this.oprId = oprId;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public rolleEnum getRolle() {
		return rolle;
	}

	public void setRolle(rolleEnum rolle) {
		this.rolle = rolle;
	}
	
	public String toString() { 
		return oprId + "\t" + cpr + "\t" + rolle + "\n"; 
	}
	
}