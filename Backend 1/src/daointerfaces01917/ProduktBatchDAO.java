package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.Produktbatch;

public interface ProduktBatchDAO {
	//Produktbatch getProduktBatch(int pbId) throws DALException, SQLException;
	List<Produktbatch> getProduktBatchList() throws DALException, SQLException;
	void createProduktBatch(Produktbatch produktbatch) throws DALException, SQLException;
	//void updateProduktBatch(Produktbatch produktbatch) throws DALException, SQLException;
}