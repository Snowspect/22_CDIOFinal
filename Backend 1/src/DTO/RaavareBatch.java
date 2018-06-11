package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import JDBC.Connector;

public class RaavareBatch {
	/** raavare batch id i området 1-99999999. Vælges af brugerne */  
	int rbId;                     
	/** raavare id i området 1-99999999 vælges af brugerne */
	int raavareId;             
	/** mængde på lager */
	double maengde;
	
	public RaavareBatch() {
	}
	
	public RaavareBatch(int rbId, int raavareId, double maengde) {
		// TODO Auto-generated constructor stub
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
	}

	public int getRbId() {
		return rbId;
	}
	public void setRbId(int rbId) {
		this.rbId = rbId;
	}
	public int getRaavareId() {
		return raavareId;
	}
	public void setRaavareId(int raavareId) {
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

	public boolean iterateRb(int rb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		PreparedStatement getRb = null;
		ResultSet rs = null;
		int count = 0;
	
		String getRbItems = "SELECT rb_id FROM raavarebatch;";
	
		try {
			//Get first array from database
			getRb = sqlCon.prepareStatement(getRbItems);
			rs = getRb.executeQuery();
	
			// Go to the last row 
			rs.last(); 
			int rowCount = rs.getRow(); 
	
			// Reset row before iterating to get data 
			rs.beforeFirst();
	
			int [] checkerArr1 = new int [rowCount];
			int arrayCount = 0;
	
			while(rs.next()) {
				checkerArr1[arrayCount] = rs.getInt(1);
				arrayCount++;
				//				System.out.println("arrayCount: " + arrayCount);
			}
			System.out.println("Arary 1: \n" + Arrays.toString(checkerArr1));
	
			//compare arrays
			for (int i = 0; i < checkerArr1.length; i++) {
					if(rb_id == checkerArr1[i]) {
						return true;
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if( getRb != null) {
				getRb.close();
			}
		}
		return false;
	}
}

