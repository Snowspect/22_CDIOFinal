package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.NotFoundException;
import DTO.produktBatchKompDTO;

public interface ProduktBatchKompDAO {
	produktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException, SQLException;
	List<produktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException, SQLException, NotFoundException;
	List<produktBatchKompDTO> getProduktBatchKompList() throws DALException, SQLException;
	void createProduktBatchKomp(produktBatchKompDTO produktbatchkomponent) throws DALException, SQLException;
	void updateProduktBatchKomp(produktBatchKompDTO produktbatchkomponent) throws DALException, SQLException;	
	public void insertProBaKomRow(int pd_id, int rb_id, double tara, double netto, int oprId) throws SQLException;
}

