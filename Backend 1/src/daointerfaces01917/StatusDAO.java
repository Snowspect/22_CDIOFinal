package daointerfaces01917;

import java.sql.SQLException;


public interface StatusDAO {

	public interface PersonerDAO {
		public boolean checkIfDone(int pb_id) throws SQLException;
		public int updateStatus(int pb_id) throws SQLException;
		public void setStatus(int pb_id, int stat) throws SQLException;
		public int checkStatus (int pb_id) throws SQLException;
	}
}
