package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;
import DTO.RollerDTO;


public interface RollerDAO {
	RollerDTO getRoller(int opr_id, int cpr) throws DALException, SQLException;
	List<RollerDTO> getAllRoller(String cpr) throws DALException, SQLException;
	List<RollerDTO> getRollerList() throws DALException, SQLException;
	void createRoller(RollerDTO per) throws DALException, SQLException;
	void updateRoller(RollerDTO per) throws DALException, SQLException;
}
