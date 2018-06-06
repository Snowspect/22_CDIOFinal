package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.Personer;


public interface PersonerDAO {
	Personer getPersoner(int cpr) throws DALException, SQLException;
	List<Personer> getPersonerList() throws DALException, SQLException;
	void createPersoner(Personer per) throws DALException, SQLException;
	void updatePersoner(Personer per) throws DALException, SQLException;
}
