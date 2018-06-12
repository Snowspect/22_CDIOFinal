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

}

