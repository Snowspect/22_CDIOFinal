package DTO;

import java.util.ArrayList;

public class Produktbatch {
	private int pbId;
	private int receptId;
	private int status;
	private ArrayList<Afvejning> afvejning;




	public Produktbatch(int pbId, int receptId, int status) {
		// TODO Auto-generated constructor stub
		this.pbId = pbId;
		this.receptId = receptId;
		this.status = status;
	}
	public int getPbId() {
		return pbId;
	}
	public void setPbId(int pbId) {
		this.pbId = pbId;
	}
	public int getReceptId() {
		return receptId;
	}
	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<Afvejning> getAfvejning() {
		return afvejning;
	}
	public void setAfvejning(ArrayList<Afvejning> afvejning) {
		this.afvejning = afvejning;
	} 
}
