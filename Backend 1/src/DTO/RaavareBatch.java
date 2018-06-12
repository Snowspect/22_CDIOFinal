package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import JDBC.Connector;

// Data Transfer object to hold information regarding one instance of RaavareBatch
public class RaavareBatch {
  
	int rbId;
	int raavareId; // must match a raavareId in the database
	double amount;	// Amount on storage
	
	public RaavareBatch() { }
	
	public RaavareBatch(int rbId, int raavareId, double amount) 
	{
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.amount = amount;
	}

	public int getRbId() { return rbId; }
	public void setRbId(int rbId) { this.rbId = rbId; }
	public int getRaavareId() { return raavareId; }
	public void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public double getamount() { return amount; }
	public void setamount(double amount) { this.amount = amount; }
	public String toString()
	{
		return "raavareBatchId: " + rbId + ", raavareId: " + raavareId + ", amount: " + amount;
	}

}

