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
import DTO.NotFoundException;
import DTO.ReceptKompDTO;

public class MySQLReceptKompDAO implements ReceptKompDAO {

	// Returns the ReceptKompDTO associated with the parameters receptId and raavareId, from the database.
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
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getRecepKomp != null) {
				getRecepKomp.close();
			}
		}
		return RkDTO;
	}

	// Returns a list of ReceptKompDTO associated with receptId, from the database.
	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException, SQLException, NotFoundException {
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
		if(list.size() == 0)
		{
			throw new NotFoundException("no recept with that id - error code Rx04)");
		}
		return list;
	}

	// Returns a list of ReceptKompDTO from the database, containing all receptkomponent.
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

	// Creates a receptkomponent in the database with information from the ReceptKompDTO parameter.
	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement createRecKomp = null;
		
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
}