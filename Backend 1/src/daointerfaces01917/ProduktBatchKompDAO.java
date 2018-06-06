package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.ProduktBatchKompDTO;

public interface ProduktBatchKompDAO {
	ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException, SQLException;
	List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException, SQLException;
	List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException, SQLException;
	void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException, SQLException;
	void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException, SQLException;	
}

