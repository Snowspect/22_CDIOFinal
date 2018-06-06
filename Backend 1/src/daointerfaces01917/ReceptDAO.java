package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.Recept;

public interface ReceptDAO {
	Recept getRecept(int receptId) throws DALException, SQLException;
	List<Recept> getReceptList() throws DALException, SQLException;
	void createRecept(Recept recept) throws DALException, SQLException;
	void updateRecept(Recept recept) throws DALException, SQLException;
}
