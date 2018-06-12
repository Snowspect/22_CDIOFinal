package daoimpl01917;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.OperatoerDAO;
import DTO.OperatoerDTO;

public class MySQLOperatoerDAO implements OperatoerDAO {
	
	// Creates a operatoerDTO with operatoer information associated with the oprId from database
	public OperatoerDTO getOperatoer(int oprId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getOperat = null;
		ResultSet rs = null;
		OperatoerDTO oprDTO = null;
		
		String getOpr = "SELECT * FROM operatoer WHERE opr_id = ?";
		
		try {
			getOperat = conn.prepareStatement(getOpr);
			getOperat.setInt(1, oprId);
			rs = getOperat.executeQuery();
			if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke");
			oprDTO = new OperatoerDTO (rs.getInt("opr_id"), rs.getString("password"));
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getOperat != null) {
				getOperat.close();
	        }
		}
		return oprDTO;
	}
	// Creates a operatoer in the database with the information from the DTO parameter.
	public void createOperatoer(OperatoerDTO opr) throws DALException, SQLException {		
		Connection conn = Connector.getConn();
		PreparedStatement createOperat = null;
		
		String createOpr = "INSERT INTO operatoer(opr_id, password) VALUES " +
						"( ? , ? )";

		
		try {
			createOperat = conn.prepareStatement(createOpr);
			
			createOperat.setInt(1, opr.getOprId());
			createOperat.setString(2, opr.getPassword());
			createOperat.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (createOperat != null) {
				createOperat.close();
	        }
		}
	}
	
	// Updates the operatoer information in the database with the information from the DTO parameter.
	public void updateOperatoer(OperatoerDTO opr) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateOperat = null;
		
		String updateOpr = "UPDATE operatoer SET password = ? WHERE opr_id = ?";
		
		try {
			updateOperat = conn.prepareStatement(updateOpr);
			
			updateOperat.setString(1, opr.getPassword());
			updateOperat.setInt(2, opr.getOprId());
			updateOperat.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (updateOperat != null) {
				updateOperat.close();
	        }
		}
	}
	
	// Returns a list of the operatoers in the database.
	public List<OperatoerDTO> getOperatoerList() throws DALException, SQLException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();

		Connection conn = Connector.getConn();
		PreparedStatement getOperatList = null;
		ResultSet rs = null;
		
		String getOprList = "SELECT * FROM operatoer";
		
		try {
			getOperatList = conn.prepareStatement(getOprList);
			rs = getOperatList.executeQuery();
			while (rs.next()) {
					list.add(new OperatoerDTO(rs.getInt("opr_id"), rs.getString("password")));
				}
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getOperatList != null) {
				getOperatList.close();
	        }
		}
		return list;
	}
		
		
}
	
