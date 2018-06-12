package daointerfaces01917;

import java.sql.SQLException;

public interface AfvejningDAO {
	public double getNom_netto (int rb_id, int pb_id) throws SQLException;
	public boolean checkTolerance(int rb_id, double netto, int pb_id) throws SQLException;
}
