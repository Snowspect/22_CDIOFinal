package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.Raavare;

public interface RaavareDAO {
	Raavare getRaavare(int raavareId) throws DALException, SQLException;
	List<Raavare> getRaavareList() throws DALException, SQLException;
	void createRaavare(Raavare raavare) throws DALException, SQLException;
	void updateRaavare(Raavare raavare) throws DALException, SQLException;
}
