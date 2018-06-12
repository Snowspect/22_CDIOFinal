package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import JDBC.Connector;

public class Raavare {

	private int ravareId;
	private String name, supplier;

	public Raavare() {}

	public Raavare(int ravareId, String name, String supplier) {
		// TODO Auto-generated constructor stub
		this.ravareId = ravareId;
		this.name = name;
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public int getRavareId() {
		return ravareId;
	}
	public void setRavareId(int ravareId) {
		this.ravareId = ravareId;
	}

	public String toString()
	{
		return "ravareId: " + ravareId + ", Name: " + name + ", Supplier: " + supplier;
	}

	public String findRaavareName (int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		PreparedStatement getWeighed = null;
		PreparedStatement getToWeigh = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int count = 0;

		PreparedStatement getRaavareName = null;	
		ResultSet rs = null;

		//Ser hvilke råvare vi mangler 
		String getName = "SELECT raavare_navn FROM raavare WHERE raavare_id = ?;";

		//Det vi har vejet	
		String getWeighedItems = "SELECT raavare_id FROM raavarebatch WHERE rb_id IN (SELECT rb_id FROM produktbatchkomponent WHERE pb_id = ?);";
		//Det vi skal veje
		String getToWeighItems = "SELECT raavare_id FROM receptkomponent WHERE recept_id = (SELECT recept_id FROM produktbatch WHERE pb_id = ?);";

		try {

			getWeighed = sqlCon.prepareStatement(getWeighedItems);
			getWeighed.setInt(1,pb_id);
			rs1 = getWeighed.executeQuery();

			// Go to the last row 
			rs1.last(); 
			int rowCount = rs1.getRow(); 

			// Reset row before iterating to get data 
			rs1.beforeFirst();

			int [] checkerArr1 = new int [rowCount];
			int arrayCount = 0;

			while(rs1.next()) {
				checkerArr1[arrayCount] = rs1.getInt(1);
				arrayCount++;
			}
			System.out.println("Arary 1: \n" + Arrays.toString(checkerArr1));

			//Get second array from database
			getToWeigh = sqlCon.prepareStatement(getToWeighItems);
			getToWeigh.setInt(1,pb_id);
			rs2 = getToWeigh.executeQuery();

			// Go to the last row 
			rs2.last(); 
			int rowCount2 = rs2.getRow(); 

			// Reset row before iterating to get data 
			rs2.beforeFirst();

			int [] checkerArr2 = new int [rowCount2];
			int arrayCount2 = 0;
			int compare = 0;

			while(rs2.next()) {
				checkerArr2[arrayCount2] = rs2.getInt(1);
				arrayCount2++;
			}
			System.out.println("Arary 2: \n" + Arrays.toString(checkerArr2));

			//compare arrays
			for (int i = 0; i < checkerArr1.length; i++) {
				if(checkerArr1[i] != checkerArr2[i]) {
					compare = checkerArr2[i]; 
					count++;
					break;
				}
			}
			if(count == checkerArr2.length) {
				return "Opfyldt";
			}

			getRaavareName = sqlCon.prepareStatement(getName);

			getRaavareName.setInt(1, compare);
			rs = getRaavareName.executeQuery();
			if(rs.first()) {
				name = rs.getString("raavare_navn");
				System.out.println(name);
				return name;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(getRaavareName != null) {
				getRaavareName.close();
			}
		}
		return name;
	}
}
