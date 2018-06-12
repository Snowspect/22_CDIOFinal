package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import JDBC.Connector;
import daointerfaces01917.StatusDAO;

public class MySQLStatusDAO implements StatusDAO {
	/*Checks if a produktbatch is done by comparing to SQL columns converted into java arrays 
	  using the values of raavare_id and comparing them value by value*/
	public boolean checkIfDone(int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		PreparedStatement getWeighed = null;
		PreparedStatement getToWeigh = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int count = 0;
	
		//Items that we have weighed
		String getWeighedItems = "SELECT raavare_id FROM raavarebatch WHERE rb_id IN (SELECT rb_id FROM produktbatchkomponent WHERE pb_id = ?);";
		//Items that has to be weighed
		String getToWeighItems = "SELECT raavare_id FROM receptkomponent WHERE recept_id = (SELECT recept_id FROM produktbatch WHERE pb_id = ?);";
	
		try {
			//Get first array from database
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
	
			while(rs2.next()) {
				checkerArr2[arrayCount2] = rs2.getInt(1);
				arrayCount2++;
			}
			System.out.println("Arary 2: \n" + Arrays.toString(checkerArr2));
	
			//compare arrays
			for (int i = 0; i < checkerArr1.length; i++) {
				for(int j = 0; j < checkerArr2.length; j++) {
					if(checkerArr1[i] == checkerArr2[j]) {
						count++;
					}
				}
			}
			if(count == checkerArr2.length) {
				return true;
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( getWeighed != null || getToWeigh != null) {
				getWeighed.close();
			}
		}
		return false;
	}
	
	//A method that checks if a status needs updating by calling other methods.
	public int updateStatus(int id) throws SQLException {
		try {
			switch (checkStatus(id)) {
			case 0:
				setStatus(id, 1);			
				break;
			case 1:
				if (checkIfDone(id)){
					setStatus(id, 2);
				} 
				break;
			case 2:
				break;
			default:
				System.out.println("WHAT!!??");
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkStatus(id);
	}
	
	//Simply sets the status of a produktbatch to 1 or 2.
	public void setStatus(int id, int stat) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		int status = 0;
		int update = 0;
		PreparedStatement setStatus1 = null;
		PreparedStatement setStatus2 = null;
	
		String setProduktStatus1 = "UPDATE produktbatch SET status = 1 WHERE pb_id = ?;";
		String setProduktStatus2 = "UPDATE produktbatch SET status = 2 WHERE pb_id = ?;";
	
		try {
			if(stat == 1) {
				setStatus1 = sqlCon.prepareStatement(setProduktStatus1);
				setStatus1.setInt(1,id);
				setStatus1.execute();
			} else {
				setStatus2 = sqlCon.prepareStatement(setProduktStatus2);
				setStatus2.setInt(1,id);
				setStatus2.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(setStatus1 != null || setStatus2 != null) {
				setStatus1.close();
				setStatus2.close();
			}
		}
	}
	//Checks the status of a produktbatch given the pb_id. Then returns it.
	public int checkStatus(int id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		int status = 0;
		PreparedStatement checkStatus = null;
		ResultSet rs = null;
	
		String checkProduktStatus = "SELECT status FROM produktbatch WHERE pb_id = ?;";
	
		try {
			checkStatus = sqlCon.prepareStatement(checkProduktStatus);
	
			checkStatus.setInt(1,id);
			rs = checkStatus.executeQuery();
			if(rs.first()) {
				status = rs.getInt(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(checkStatus != null) {
				checkStatus.close();
			}
		}
		return status;
	}
}
