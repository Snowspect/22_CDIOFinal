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
	public Personer getPersoner(int rolle_id) throws DALException, SQLException {
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
			String cpr = null;;
			String Cpr = null, opr_navn = null,ini = null,Rolle = null;
			int Rolle_id = 0;
			Boolean Status = null;
			

			getRolle = conn.prepareStatement(getrol);
			getRolle.setInt(1, rolle_id);
			rs = getRolle.executeQuery();
			if(rs.first())
			{
				Rolle_id = rs.getInt("rolle_id");
				Cpr = rs.getString("cpr");
				Rolle = rs.getString("rolle");
				cpr = Cpr;
			}
			
			getOperator = conn.prepareStatement(getopr);
			getOperator.setInt(1, rolle_id);
			rs = getOperator.executeQuery();
			if(rs.first())
			{
				Status = (rs.getBoolean("opr_status"));				
			}

			getPerson = conn.prepareStatement(getper);
			getPerson.setString(1, cpr);
			rs = getPerson.executeQuery();
			if(rs.first())
			{
				opr_navn = rs.getString("opr_navn");
				ini = rs.getString("ini");				
			}

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
	public ArrayList<Personer> getPersonerList() throws DALException, SQLException {
		ArrayList<Personer> list = new ArrayList<Personer>();

		Connection conn = Connector.getConn();
		PreparedStatement getPerson = null;
		PreparedStatement getRolle = null;
		PreparedStatement getOperator = null;
		ResultSet rs = null;
		
		String getper = "SELECT * FROM personer";
		String getopr = "SELECT * FROM operatoer";
		String getrol = "SELECT * FROM roller";
		
		try {
			ArrayList<String> cpr = new ArrayList<String>(), opr_navn = new ArrayList<String>(), ini = new ArrayList<String>(),rolle = new ArrayList<String>();
			ArrayList<Integer> rolle_id = new ArrayList<Integer>();
			ArrayList<Boolean> status = new ArrayList<Boolean>();
			
			
			getPerson = conn.prepareStatement(getper);
			rs = getPerson.executeQuery();
			
			while (rs.next()) {
			cpr.add(rs.getString("cpr"));
			opr_navn.add(rs.getString("opr_navn"));
			ini.add(rs.getString("ini"));
			}
//			rs = null;
			
			getRolle = conn.prepareStatement(getrol);
			rs = getRolle.executeQuery();
			while(rs.next()) {
				rolle_id.add(rs.getInt("rolle_id"));
				rolle.add(rs.getString("rolle"));
			}
//			rs = null;
			
			getOperator = conn.prepareStatement(getopr);
			rs = getOperator.executeQuery();
			while(rs.next())
			{
				status.add((rs.getBoolean("opr_status")));				
			}
//			rs = null;
			
			int i = 0;
			for (String string : ini) {
				list.add(new Personer(rolle_id.get(i), opr_navn.get(i), ini.get(i), cpr.get(i), rolle.get(i), status.get(i)));  //TODO Fix parameters
				i++;
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
		String createPer = "CALL NewEmployee(?,?,?,?,?,?)";
		try {
			createPerson = conn.prepareStatement(createPer);
		
			createPerson.setString(1, per.getUserName());
			createPerson.setString(2, per.getIni());
			createPerson.setBoolean(3, per.isStatus());
			createPerson.setString(4, per.getCpr());
			createPerson.setInt(5, per.getUserId());
			createPerson.setString(6, per.getRoles());
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
		
		String updatePer = "CALL UpdateEmployee(?,?,?,?,?)";
		
		try {
			updatePerson = conn.prepareStatement(updatePer);
			
			updatePerson.setString(1, per.getUserName());
			updatePerson.setString(2, per.getIni());
			updatePerson.setString(3, per.getCpr());
			updatePerson.setInt(4, per.getUserId());
			updatePerson.setString(5, per.getRoles());
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

	public void deletePersoner(int id) throws DALException, SQLException {
		
		Connection conn = Connector.getConn();
		PreparedStatement deletePerson = null;
		String deletePer = "UPDATE operatoer SET opr_status = 0 WHERE rolle_id = ?;";
		try {
			deletePerson = conn.prepareStatement(deletePer);
			deletePerson.setInt(1, id);
			deletePerson.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (deletePerson != null) {
				deletePerson.close();
	        }
		}

	}
	
}
