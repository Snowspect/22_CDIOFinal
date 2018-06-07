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
}

