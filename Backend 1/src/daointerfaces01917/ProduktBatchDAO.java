package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Produktbatch;

public interface ProduktBatchDAO {
	//Produktbatch getProduktBatch(int pbId) throws DALException, SQLException;
	List<Produktbatch> getProduktBatchList() throws DALException, SQLException, NotFoundException;
	String createProduktBatch(Produktbatch produktbatch) throws DALException, SQLException, FoundException, NotFoundException;
	//void updateProduktBatch(Produktbatch produktbatch) throws DALException, SQLException;
}