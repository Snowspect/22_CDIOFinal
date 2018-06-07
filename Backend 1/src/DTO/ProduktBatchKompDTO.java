package DTO;

public class ProduktBatchKompDTO 
{
	int pbId; 	  // produktbatchets id
	int rbId;        // i omraadet 1-99999999
	double tara;
	double netto;
	int rolle_id;					// operatoer-nummer

	
	public ProduktBatchKompDTO() {}
	
	public ProduktBatchKompDTO(int pbId, int rbId, double tara, double netto, int oprId)
	{
		this.pbId = pbId;
		this.rbId = rbId;
		this.tara = tara;
		this.netto = netto;
		this.rolle_id = oprId;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public double getTara() { return tara; }
	public void setTara(double tara) { this.tara = tara; }
	public double getNetto() { return netto; }
	public void setNetto(double netto) { this.netto = netto; }
	public int getRolleId() { return rolle_id; }
	public void setRolleId(int rolle_id) { this.rolle_id = rolle_id; }
	public String toString() { 
		return pbId + "\t" + rbId +"\t" + tara +"\t" + netto + "\t" + rolle_id ; 
	}
}
