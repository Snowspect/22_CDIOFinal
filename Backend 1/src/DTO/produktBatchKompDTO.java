package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.Connector;

public class produktBatchKompDTO 
{
	int pbId; 	  // produktbatchets id
	int rbId;        // i omraadet 1-99999999
	double tara;
	double netto;
	int rolle_id;					// operatoer-nummer

	
	public produktBatchKompDTO() {}
	
	public produktBatchKompDTO(int pbId, int rbId, double tara, double netto, int oprId)
	{
		this.pbId = pbId;
		this.rbId = rbId;
		this.tara = tara;
		this.netto = netto;
		this.rolle_id = oprId;
	}
	
	
	
	
	public String toString() { 
		return pbId + "\t" + rbId +"\t" + tara +"\t" + netto + "\t" + rolle_id ; 
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public int getRolle_id() {
		return rolle_id;
	}

	public void setRolle_id(int rolle_id) {
		this.rolle_id = rolle_id;
	}

	//Calls a SP in the SQL that will insert a row in produktbatchkomp 
	public void insertProBaKomRow(int pd_id, int rb_id, double tara, double netto, int oprId) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		PreparedStatement row = null;
		ResultSet rs = null;
	
		String insertRow = "CALL MakeProBaKompRow(?,?,?,?,?) ";
	
		try {
			row = sqlCon.prepareStatement(insertRow);
	
			row.setInt(1,pd_id);
			row.setInt(2,rb_id);
			row.setDouble(3,tara);
			row.setDouble(4,netto);
			row.setInt(5,oprId);
	
			row.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(row != null) {
				row.close();
			}
		}
	}
}