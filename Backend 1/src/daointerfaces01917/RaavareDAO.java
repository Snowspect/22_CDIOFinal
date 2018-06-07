package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Raavare;

public interface RaavareDAO {
	//Raavare getRaavare(int raavareId) throws DALException, SQLException;
	List<Raavare> getRaavareList() throws DALException, SQLException;
	String createRaavare(Raavare raavare) throws DALException, SQLException, FoundException;
	String updateRaavare(Raavare raavare) throws DALException, SQLException, NotFoundException;
}
