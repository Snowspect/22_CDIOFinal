package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import DTO.OperatoerDTO;

public interface OperatoerDAO {
	OperatoerDTO getOperatoer(int oprId) throws DALException, SQLException;
	List<OperatoerDTO> getOperatoerList() throws DALException, SQLException;
	void createOperatoer(OperatoerDTO opr) throws DALException, SQLException;
	void updateOperatoer(OperatoerDTO opr) throws DALException, SQLException;
}
