package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.PersonerDAO;
import jersey.repackaged.com.google.common.base.Throwables;
import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Personer;

public class MySQLPersonerDAO implements PersonerDAO {


	//Returns a list of personer from the database.

	@Override
	public ArrayList<Personer> getPersonerList() throws DALException, SQLException {
		ArrayList<Personer> list = new ArrayList<Personer>();

		Connection conn = Connector.getConn();
		PreparedStatement getPerson = null;
		ResultSet rs = null;
		
		String getper = "SELECT * FROM roller natural join operatoer natural join personer";
		
		try {
			ArrayList<String> cpr = new ArrayList<String>();
			ArrayList<String> opr_navn = new ArrayList<String>();
			ArrayList<String> ini = new ArrayList<String>();
			ArrayList<String> rolle = new ArrayList<String>();
			ArrayList<Integer> rolle_id = new ArrayList<Integer>();
			ArrayList<Boolean> status = new ArrayList<Boolean>();
			
			
			getPerson = conn.prepareStatement(getper);
			rs = getPerson.executeQuery();
			
			while (rs.next()) {
			cpr.add(rs.getString("cpr"));
			opr_navn.add(rs.getString("opr_navn"));
			ini.add(rs.getString("ini"));
			rolle_id.add(rs.getInt("rolle_id"));
			rolle.add(rs.getString("rolle"));
			status.add(rs.getBoolean("opr_status"));
			}
			
			for(int j = 0; j < cpr.size(); j++)
			{
				list.add(new Personer(rolle_id.get(j), opr_navn.get(j), ini.get(j), cpr.get(j), rolle.get(j), status.get(j)));  //TODO Fix parameters
			}
		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getPerson != null) {
				getPerson.close();
	        }
		}
		return list;
	}

	// Creates a person in the database with information from the Personer parameter.
	@Override
	public String createPersoner(Personer per) throws DALException, SQLException, FoundException {
		
		Connection conn = Connector.getConn();
		CallableStatement createPerson = null;
		String createPer = "CALL NewEmployee(?,?,?,?,?,?,?)";
		int result = -1;
		try {
			createPerson = (CallableStatement) conn.prepareCall(createPer);
		
			createPerson.setString(1, per.getUserName());
			createPerson.setString(2, per.getIni());
			createPerson.setBoolean(3, per.isStatus());
			createPerson.setString(4, per.getCpr());
			createPerson.setInt(5, per.getUserId());
			createPerson.setString(6, per.getRoles());
			createPerson.registerOutParameter(7, java.sql.Types.INTEGER);
			createPerson.executeUpdate();
			result = createPerson.getInt(7);
		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (createPerson != null) {
				createPerson.close();
	        }
		}
		if(result == 3)
		{
			throw new FoundException("user id already exists");
		}
		if(result == 4)
		{
			return "a problem occured - contact nearest technical department with code 60ax";
		}
		return "user created";
	}

	// Updates a person in the database with the information from the Personer parameter.
	// The CPR nr and UserId needs to be from an existing person and it must be the same person.
	@Override
	public void updatePersoner(Personer per) throws DALException, SQLException, NotFoundException {
		Connection conn = Connector.getConn();
		PreparedStatement updatePerson = null;
		
		String updatePer = "CALL UpdateEmployee(?,?,?,?,?)";
		int affectedRows = -1;
		try {
			updatePerson = conn.prepareStatement(updatePer);
			
			updatePerson.setString(1, per.getUserName());
			updatePerson.setString(2, per.getIni());
			updatePerson.setInt(3, per.getUserId());
			updatePerson.setString(4, per.getRoles());
			updatePerson.setString(5, per.getCpr());
			updatePerson.executeUpdate();
		} catch (SQLException e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			if (updatePerson != null) {
				updatePerson.close();
	        }
		}
		if (affectedRows == 0)
		{
			throw new NotFoundException("Ingen Bruger fundet - ingen bruger opdateret");
		}
	}

	// Sets status to false on a operatoer associated with the id parameter.
	public void deletePersoner(int id) throws DALException, SQLException, NotFoundException 
	{
		Connection conn = Connector.getConn();
		PreparedStatement deletePerson = null;
		String deletePer = "UPDATE operatoer SET opr_status = 0 WHERE rolle_id = ?;";
		int affectedRows = -1;
		try {
			deletePerson = conn.prepareStatement(deletePer);
			deletePerson.setInt(1, id);
			affectedRows = deletePerson.executeUpdate();
		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (deletePerson != null) {
				deletePerson.close();
	        }
		}
		//delete returns a number for affected rows.
		if(affectedRows == 0)
		{
			throw new NotFoundException("Bruger ikke fundet og derfor ingen fjernet brugere");
		}
	}
	
	public String findUserName (int id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		String name = null;
		PreparedStatement getUserName = null;
		ResultSet rs = null;
	
		String getName = "Select opr_navn from personer natural join roller where rolle_id = ?;";
	
		try {
			getUserName = sqlCon.prepareStatement(getName);
	
			getUserName.setInt(1, id);
			rs = getUserName.executeQuery();
			if(rs.first()) {
				name = rs.getString("opr_navn");
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if(getUserName != null) {
				getUserName.close();
			}
		}
		return name;
	}
}