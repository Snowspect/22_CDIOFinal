package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.FoundException;
import DTO.Recept;

public interface ReceptDAO {
//	Recept getRecept(int receptId) throws DALException, SQLException;
	List<Recept> getReceptList() throws DALException, SQLException;
	String createRecept(Recept recept) throws DALException, SQLException, FoundException;
//	void updateRecept(Recept recept) throws DALException, SQLException;
	public String findReceptName (int id) throws SQLException;
}
