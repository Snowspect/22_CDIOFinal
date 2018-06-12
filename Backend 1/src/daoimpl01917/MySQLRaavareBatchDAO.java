package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareBatchDAO;
import DTO.FoundException;
import DTO.RaavareBatch;

public class MySQLRaavareBatchDAO implements RaavareBatchDAO {

	@Override
	public List<RaavareBatch> getRaavareBatchList() throws DALException, SQLException {
		List<RaavareBatch> list = new ArrayList<RaavareBatch>();
		Connection conn = Connector.getConn();
		PreparedStatement getRaavareBatchList = null;
		ResultSet rs = null;
		
		String getRaaBaList = "SELECT * FROM raavarebatch";
		
		try {
			getRaavareBatchList = conn.prepareStatement(getRaaBaList);
			rs = getRaavareBatchList.executeQuery();
			while (rs.next()) {
					list.add(new RaavareBatch(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
				}
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getRaavareBatchList != null) {
				getRaavareBatchList.close();
	        }
		}
		return list;
	}

	@Override
	public String createRaavareBatch(RaavareBatch raavarebatch) throws DALException, SQLException, FoundException {
		Connection conn = Connector.getConn();
		PreparedStatement createRaavareBatch = null;
		
		String createRaaBa = "INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES ( ? , ? , ? )";

		try {
			createRaavareBatch = conn.prepareStatement(createRaaBa);
			
			createRaavareBatch.setInt(1, raavarebatch.getRbId());
			createRaavareBatch.setInt(2, raavarebatch.getRaavareId());
			createRaavareBatch.setDouble(3, raavarebatch.getMaengde());
			createRaavareBatch.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new FoundException("RaavareBatchen findes allerede");	
		}
		catch (SQLException e) {
			//i tvivl om hvorvidt dette nogensinde bliver udf�rt grundet vores input handling gennem html
			//ogs� i tvivl om den kan finde ud af at k�re begge to eller den stoppe efter sout.(system out print)
			System.out.println(e);
			return "Error ikke relateret til allerede eksisterende id - se consol output";
		} finally {
			if (createRaavareBatch != null) {
				createRaavareBatch.close();
	        }
		}
		return "raavareBatch oprettet";
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