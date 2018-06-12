package DTO;

import java.util.ArrayList;

//Data Transfer object to hold information regarding one instance of ProduktBatch
public class Produktbatch {
	
	private int pbId;
	private int receptId;
	private int status;
	private ArrayList<produktBatchKompDTO> produktBatchKomponent = new ArrayList<>(); //components in the produktbatch

	public Produktbatch() {};

	public Produktbatch(int pbId, int receptId, int status) {
		this.pbId = pbId;
		this.receptId = receptId;
		this.status = status;
	}

	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public ArrayList<produktBatchKompDTO> getProduktBatchKomponent() { return produktBatchKomponent; }
	public void setProduktBatchKomponent(ArrayList<produktBatchKompDTO> produktBaKo) { this.produktBatchKomponent = produktBaKo; }
	public String toString()
	{
		return "";
	}
}