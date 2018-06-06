package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.ReceptKompDTO;

public interface ReceptKompDAO {
	ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException, SQLException;
	List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException, SQLException;
	List<ReceptKompDTO> getReceptKompList() throws DALException, SQLException;
		void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException, SQLException;
	void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException, SQLException;
}
