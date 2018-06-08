package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ReceptKompDAO;
import DTO.ReceptKompDTO;

public class MySQLReceptKompDAO implements ReceptKompDAO {

	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getRecepKomp = null;
		ResultSet rs = null;
		ReceptKompDTO RkDTO = null;
		
		String getRecptKo = "SELECT * FROM receptkomponent WHERE recept_id = ? AND raavare_id = ?";
		
		try {
			getRecepKomp = conn.prepareStatement(getRecptKo);
			getRecepKomp.setInt(1, receptId);
			getRecepKomp.setInt(2, raavareId);
			rs = getRecepKomp.executeQuery();
	    	if (!rs.first()) throw new DALException("Receptkomponentet med recept ID: " + receptId + " eller raavare ID: " + raavareId + " findes ikke");
			RkDTO = new ReceptKompDTO (rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
		} catch (SQLException e) {
			//do error handling
			//TODO
		} finally {
			if (getRecepKomp != null) {
				getRecepKomp.close();
			}
		}
		return RkDTO;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException, SQLException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		
		Connection conn = Connector.getConn();
		PreparedStatement getRecepKompList = null;
		ResultSet rs = null;
		
		String getRcptKoList = "SELECT * FROM receptkomponent WHERE recept_id = ?";
		
		try {
			getRecepKompList = conn.prepareStatement(getRcptKoList);

			getRecepKompList.setInt(1, receptId);
			rs = getRecepKompList.executeQuery();
			while (rs.next())
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getRecepKompList != null) {
				getRecepKompList.close();
			}
		}
		return list;
	}
		
//		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptId);
//		try
//		{
//			while (rs.next()) 
//			{
//				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
//			}
//		}
//		catch (SQLException e) { throw new DALException(e); }
//		return list;
//	}

	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException, SQLException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		
		Connection conn = Connector.getConn();
		PreparedStatement getReceptListKomp = null;
		ResultSet rs = null;

		String getRcptListKo = "SELECT * FROM receptkomponent";

		try {
			getReceptListKomp = conn.prepareStatement(getRcptListKo);
			rs = getReceptListKomp.executeQuery();
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		} catch (SQLException e) { 
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getReceptListKomp != null) {
				getReceptListKomp.close();
			}
		}
		return list;
	}
		
		
		
//		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent");
//		try
//		{
//			while (rs.next()) 
//			{
//				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
//			}
//		}
//		catch (SQLException e) { throw new DALException(e); }
//		return list;
//	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement createRecKomp = null;
		System.out.println("inhere");
		
		String createRcptKo = "INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " + "(?, ?, ?, ?)";
		
		try {
			createRecKomp = conn.prepareStatement(createRcptKo);

			createRecKomp.setInt(1, receptkomponent.getReceptId());
			createRecKomp.setInt(2, receptkomponent.getRaavareId());
			createRecKomp.setDouble(3, receptkomponent.getNomNetto());
			createRecKomp.setDouble(4, receptkomponent.getTolerance());
			createRecKomp.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (createRecKomp != null) {
				createRecKomp.close();
			}
		}
	}
//		Connector.doUpdate(
//				"INSERT INTO receptkomponent(recept_id, raavare_id, nom_netto, tolerance) VALUES " +
//				"(" + receptkomponent.getReceptId() + ", " + receptkomponent.getRaavareId() + ", " + receptkomponent.getNomNetto() + ", " + 
//				receptkomponent.getTolerance() + ")"
//			);
//
//	}
/*
	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateRecKomp = null;

		String updateRcptKo = "UPDATE receptkomponent SET recept_id = ?, raavare_id = ?, nom_netto = ?, tolerance = ? WHERE recept_id = ? AND raavare_id = ?";
		
		try {
			updateRecKomp = conn.prepareStatement(updateRcptKo);

			updateRecKomp.setInt(1, receptkomponent.getReceptId());
			updateRecKomp.setInt(2, receptkomponent.getRaavareId());
			updateRecKomp.setDouble(3, receptkomponent.getNomNetto());
			updateRecKomp.setDouble(4,  receptkomponent.getTolerance());
			updateRecKomp.setInt(5, receptkomponent.getReceptId());
			updateRecKomp.setInt(6, receptkomponent.getRaavareId());
			updateRecKomp.executeUpdate();
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (updateRecKomp != null) {
				updateRecKomp.close();
			}
		}
	}
*/		
//		Connector.doUpdate(
//				"UPDATE receptkomponent SET  recept_id = " + receptkomponent.getReceptId() + ", raavare_id =  " + receptkomponent.getRaavareId() + 
//				", nom_netto = " + receptkomponent.getNomNetto() + ", tolerance = " + receptkomponent.getTolerance() + " WHERE recept_id = " + receptkomponent.getReceptId() + " AND raavare_id = " + receptkomponent.getRaavareId()
//		);
//
//	}

}
