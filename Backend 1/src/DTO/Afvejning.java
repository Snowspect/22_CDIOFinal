package DTO;

public class Afvejning {
	private int userId;
	private int rbId;
	private double tara;
	private double netto;
	private double brutto;
	private Personer pers;
	private RaavareBatch raav;

	public double getBrutto() {
		return this.brutto;
	}
	public void setBrutto(double brutto) {
		this.brutto = brutto;
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRbId() {
		return this.rbId;
	}
	public void setRbId(int rbId) {
		this.rbId = rbId;
	}
	public double getTara() {
		return this.tara;
	}
	public void setTara(double tara) {
		this.tara = tara;
	}
	public double getNetto() {
		return this.netto;
	}
	public void setNetto(double netto) {
		this.netto = netto;
	}
	
	public String toString(){
		return "userId: " + this.pers.getUserId() + " råvareBatchId: " + this.raav.getRbId()
				+ " taravægt: " + this.tara + " netto: "+ this.netto + " brutto: " 
				+ this.brutto;
	}
}
