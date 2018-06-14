package daointerfaces01917;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Personer;


public interface PersonerDAO {
//	Personer getPersoner(int rolle_id) throws DALException, SQLException;
	ArrayList<Personer> getPersonerList() throws DALException, SQLException;
	String createPersoner(Personer per) throws DALException, SQLException, FoundException;
	String updatePersoner(Personer per) throws DALException, SQLException, NotFoundException;
	public String findUserName (int id) throws SQLException;
	public String deletePersoner(int id) throws DALException, SQLException, NotFoundException;
}
