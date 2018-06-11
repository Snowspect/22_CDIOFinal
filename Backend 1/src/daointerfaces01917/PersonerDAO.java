package daointerfaces01917;

import java.sql.SQLException;
import java.util.ArrayList;

import DTO.NotFoundException;
import DTO.Personer;


public interface PersonerDAO {
//	Personer getPersoner(int rolle_id) throws DALException, SQLException;
	ArrayList<Personer> getPersonerList() throws DALException, SQLException;
	void createPersoner(Personer per) throws DALException, SQLException;
	void updatePersoner(Personer per) throws DALException, SQLException, NotFoundException;
}
