package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ReceptDAO;
import DTO.Recept;

public class MySQLReceptDAO implements ReceptDAO {

	@Override
	public Recept getRecept(int receptId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getRecep = null;
		ResultSet rs = null;
		Recept ReDTO = null;
		
		String getRcpt = "SELECT * FROM recept WHERE recept_id = ?";
		
		try {
			getRecep = conn.prepareStatement(getRcpt);
			getRecep.setInt(1, receptId);
			rs = getRecep.executeQuery();
	    	if (!rs.first()) throw new DALException("Recepten " + receptId + " findes ikke");
			ReDTO = new Recept (rs.getInt("recept_id"), rs.getString("recept_navn"));
		} catch (SQLException e) {
			//do error handling
			//TODO
		} finally {
			if (getRecep != null) {
				getRecep.close();
			}
		}
		return ReDTO;
	}
		
		
		
		
//		ResultSet rs = Connector.doQuery("SELECT * FROM recept WHERE recept_id = " + receptId);
//	    try {
//	    	if (!rs.first()) throw new DALException("Recepten " + receptId + " findes ikke");
//	    	return new Recept (rs.getInt("recept_id"), rs.getString("recept_navn"));
//	    }
//	    catch (SQLException e) {throw new DALException(e); }
//	}

	@Override
	public List<Recept> getReceptList() throws DALException, SQLException {
		List<Recept> list = new ArrayList<Recept>();
		
		Connection conn = Connector.getConn();
		PreparedStatement getRecepList = null;
		ResultSet rs = null;

		String getRcptList = "SELECT * FROM recept";

		try {
			getRecepList = conn.prepareStatement(getRcptList);
			rs = getRecepList.executeQuery();
			while (rs.next()) 
			{
				list.add(new Recept(rs.getInt("recept_id"), rs.getString("recept_navn")));
			}
		} catch (SQLException e) { 
//			throw new DALException(e);
			//Do error handling
			//TODO
		} finally {
			if (getRecepList != null) {
				getRecepList.close();
			}
		}
		return list;
	}
		
		
//		ResultSet rs = Connector.doQuery("SELECT * FROM recept");
//		try
//		{
//			while (rs.next()) 
//			{
//				list.add(new Recept(rs.getInt("recept_id"), rs.getString("recept_navn")));
//			}
//		}
//		catch (SQLException e) { throw new DALException(e); }
//		return list;
//	}

	@Override
	public void createRecept(Recept recept) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement createRec = null;
		
		String createRcpt = "INSERT INTO recept(recept_id, recept_navn) VALUES " + "(?, ?,)";
		
		try {
			createRec = conn.prepareStatement(createRcpt);

			createRec.setInt(1, recept.getReceptId());
			createRec.setString(2, recept.getReceptNavn());
			createRec.executeUpdate();
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (createRec != null) {
				createRec.close();
			}
		}
	}
		
		
		
//		Connector.doUpdate(
//				"INSERT INTO recept(recept_id, recept_navn) VALUES " +
//				"(" + recept.getReceptId() + ", '" + recept.getReceptNavn() + "')"
//			);
//	}

	@Override
	public void updateRecept(Recept recept) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateRec = null;

		String updateRcpt = "UPDATE recept SET  recept_id = ?, recept_navn = ? WHERE opr_id = ?";
		
		try {
			updateRec = conn.prepareStatement(updateRcpt);

			updateRec.setInt(1, recept.getReceptId());
			updateRec.setString(2, recept.getReceptNavn());
			updateRec.setInt(3, recept.getReceptId());
			updateRec.executeUpdate();
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (updateRec != null) {
				updateRec.close();
			}
		}
	}
}
		
//		
//		Connector.doUpdate(
//				"UPDATE recept SET  recept_id = '" + recept.getReceptId() + "', recept_navn =  '" + recept.getReceptNavn() + "' WHERE opr_id = " +
//				recept.getReceptId()
//		);
//	}
//
//}
