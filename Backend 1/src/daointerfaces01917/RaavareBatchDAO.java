package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.RaavareBatch;

public interface RaavareBatchDAO {
	RaavareBatch getRaavareBatch(int rbId) throws DALException, SQLException;
	List<RaavareBatch> getRaavareBatchList() throws DALException, SQLException;
	List<RaavareBatch> getRaavareBatchList(int raavareId) throws DALException, SQLException;
	void createRaavareBatch(RaavareBatch raavarebatch) throws DALException, SQLException;
	void updateRaavareBatch(RaavareBatch raavarebatch) throws DALException, SQLException;
}

