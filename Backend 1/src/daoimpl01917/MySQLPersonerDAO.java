package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.PersonerDAO;
import DTO.Personer;

public class MySQLPersonerDAO implements PersonerDAO {

	@Override
	public Personer getPersoner(int cpr, int rolle_id) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getPerson = null;
		PreparedStatement getRolle = null;
		PreparedStatement getOperator = null;
		ResultSet rs = null;
		Personer perDTO = null;
		
		String getper = "SELECT * FROM personer WHERE cpr = ?";
		String getopr = "SELECT * FROM operatoer WHERE rolle_id = ?";
		String getrol = "SELECT * FROM roller WHERE rolle_id = ?";
		
		try {
			getPerson = conn.prepareStatement(getper);
			getPerson.setInt(1, cpr);
			rs = getPerson.executeQuery();
			String Cpr = rs.getString("cpr");
			String opr_navn = rs.getString("opr_navn");
			String ini = rs.getString("ini");
			
			
			getRolle = conn.prepareStatement(getrol);
			getRolle.setInt(1, rolle_id);
			rs = getRolle.executeQuery();
			int Rolle_id = rs.getInt("rolle_id");
//			String Rolle_Cpr = rs.getString("cpr");
			String Rolle = rs.getString("rolle");
			
			
			getOperator = conn.prepareStatement(getopr);
			getOperator.setInt(1, rolle_id);
			rs = getOperator.executeQuery();
//			String Roller_id = rs.getString("rolle_id");
			boolean Status = (rs.getInt("opr_status") != 0);
			
			
			if (!rs.first()) throw new DALException("Personen " + cpr + " findes ikke");  //Fix error message
			perDTO = new Personer (Rolle_id, opr_navn, ini, Cpr, Rolle, Status);   
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getPerson != null) {   //Fix error handling for roller and operator
				getPerson.close();
				getRolle.close();
				getOperator.close();
	        }
		}
		return perDTO;
	}

	@Override
	public List<Personer> getPersonerList() throws DALException, SQLException {
		List<Personer> list = new ArrayList<Personer>();

		Connection conn = Connector.getConn();
		PreparedStatement getPerson = null;
		PreparedStatement getRolle = null;
		PreparedStatement getOperator = null;
		ResultSet rs = null;
		
		String getper = "SELECT * FROM personer";
		String getopr = "SELECT * FROM operatoer";
		String getrol = "SELECT * FROM roller";
		
		try {
			getPerson = conn.prepareStatement(getper);
			rs = getPerson.executeQuery();
			String Cpr = rs.getString("cpr");
			String opr_navn = rs.getString("opr_navn");
			String ini = rs.getString("ini");
			
			
			getRolle = conn.prepareStatement(getrol);
			rs = getRolle.executeQuery();
			int Rolle_id = rs.getInt("rolle_id");
//			String Rolle_Cpr = rs.getString("cpr");
			String Rolle = rs.getString("rolle");
			
			
			getOperator = conn.prepareStatement(getopr);
			rs = getOperator.executeQuery();
//			String Roller_id = rs.getString("rolle_id");
			boolean Status = (rs.getInt("opr_status") != 0);
			
						
			while (rs.next()) {
					list.add(new Personer(Rolle_id, opr_navn, ini, Cpr, Rolle, Status));  //TODO Fix parameters
				}
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getPerson != null) {
				getPerson.close();
				getRolle.close();
				getOperator.close();
	        }
		}
		return list;
	}

	@Override
	public void createPersoner(Personer per) throws DALException, SQLException {
		
		Connection conn = Connector.getConn();
		PreparedStatement createPerson = null;
		
		String createPer = "INSERT INTO personer(opr_navn, ini, cpr) VALUES " +
						"( ? , ? , ? )";
		
		try {
			createPerson = conn.prepareStatement(createPer);
			
			createPerson.setString(2, per.getUserName());
			createPerson.setString(3, per.getIni());
			createPerson.setString(4, per.getCpr());
			createPerson.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (createPerson != null) {
				createPerson.close();
	        }
		}

	}

	@Override
	public void updatePersoner(Personer per) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updatePerson = null;
		
		String updatePer = "UPDATE personer SET opr_navn = ?, ini = ? WHERE cpr = ?";
		
		try {
			updatePerson = conn.prepareStatement(updatePer);
			
			updatePerson.setString(1, per.getUserName());
			updatePerson.setString(2, per.getIni());
			updatePerson.setString(3, per.getCpr());
			updatePerson.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (updatePerson != null) {
				updatePerson.close();
	        }
		}
	}

}
