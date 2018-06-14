package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.RaavareBatch;

public interface RaavareBatchDAO {
//	RaavareBatch getRaavareBatch(int rbId) throws DALException, SQLException;
	List<RaavareBatch> getRaavareBatchList() throws DALException, SQLException;
//	List<RaavareBatch> getRaavareBatchList(int raavareId) throws DALException, SQLException;
	String createRaavareBatch(RaavareBatch raavarebatch) throws DALException, SQLException, FoundException, NotFoundException;
//	void updateRaavareBatch(RaavareBatch raavarebatch) throws DALException, SQLException;
	public boolean iterateRb(int rb_id) throws SQLException;
}

