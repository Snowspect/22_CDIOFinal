package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RollerDAO;
import DTO.RollerDTO;
import DTO.rolleEnum;

public class MySQLRollerDAO implements RollerDAO {

	// Returns the RollerDTO associated with the opr_id and cpr parameters, from the database.
	@Override
	public RollerDTO getRoller(int opr_id, int cpr) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getRoller = null;
		ResultSet rs = null;
		RollerDTO rolleDTO = null;
		
		String getRolle = "SELECT * FROM roller WHERE opr_id = ?";
		
		try {
			getRoller = conn.prepareStatement(getRolle);
			getRoller.setInt(1, opr_id);
			rs = getRoller.executeQuery();
			if (!rs.first()) throw new DALException("Cpr nummeret " + cpr + " findes ikke");
			rolleDTO = new RollerDTO (rs.getInt("opr_id"), rs.getString("cpr"), rolleEnum.valueOf(rs.getString("rolle")));
		} catch (SQLException e ) {
			//Do error handling
		} finally {
			if (getRoller != null) {
				getRoller.close();
	        }
		}
		return rolleDTO;
	}

	// Returns a list of RollerDTO associated with the cpr parameter, from the database.
	@Override
	public List<RollerDTO> getAllRoller(String cpr) throws DALException, SQLException {
		List<RollerDTO> list = new ArrayList<RollerDTO>();
		Connection conn = Connector.getConn();
		PreparedStatement getRollerList = null;
		ResultSet rs = null;
		
		String getRolleList = "SELECT * FROM roller WHERE cpr = ?";
		
		try {
			getRollerList = conn.prepareStatement(getRolleList);
			getRollerList.setString(1, cpr);
			rs = getRollerList.executeQuery();
			while (rs.next()) {
					list.add(new RollerDTO(rs.getInt("opr_id"), rs.getString("cpr"), rolleEnum.valueOf(rs.getString("rolle"))));
				}
		} catch (SQLException e ) {
			//Do error handling
	
		} finally {
			if (getRollerList != null) {
				getRollerList.close();
	        }
		}
		return list;
	}

	// Returns a list of RollerDTO containing all the information from roller in the database 
	@Override
	public List<RollerDTO> getRollerList() throws DALException, SQLException {
		List<RollerDTO> list = new ArrayList<RollerDTO>();

		Connection conn = Connector.getConn();
		PreparedStatement getRollerList = null;
		ResultSet rs = null;
		
		String getRolleList = "SELECT * FROM roller";
		
		try {
			getRollerList = conn.prepareStatement(getRolleList);
			rs = getRollerList.executeQuery();
			while (rs.next()) {
					list.add(new RollerDTO(rs.getInt("opr_id"), rs.getString("cpr"), rolleEnum.valueOf(rs.getString("rolle"))));
				}
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getRollerList != null) {
				getRollerList.close();
	        }
		}
		return list;
	}

	// Inserts the information from the RollerDTO parameter into the table roller in the database.
	@Override
	public void createRoller(RollerDTO rolle) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement createRoller = null;
		
		String createRolle = "INSERT INTO roller(opr_id, cpr, rolle) VALUES " +
						"( ? , ? , ? )";
		
		try {
			createRoller = conn.prepareStatement(createRolle);
			createRoller.setInt(1, rolle.getOprId());
			createRoller.setString(2, rolle.getCpr());
			createRoller.setString(3, rolle.getRolle().toString());
			createRoller.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			
		} finally {
			if (createRoller != null) {
				createRoller.close();
	        }
		}
		
	}

	// Updates Roller with the information from the RollerDTO parameter, in the database.
	@Override
	public void updateRoller(RollerDTO rolle) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateRoller = null;
		
		String updateRolle = "UPDATE rolle SET rolle = ? WHERE opr_id = ?";
		
		try {
			updateRoller = conn.prepareStatement(updateRolle);
			
			updateRoller.setString(1, rolle.getRolle().toString());
			updateRoller.setInt(2, rolle.getOprId());
			updateRoller.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			
		} finally {
			if (updateRoller != null) {
				updateRoller.close();
	        }
		}
		
	}



}
