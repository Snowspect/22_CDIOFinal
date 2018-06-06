package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import DTO.Raavare;

public class MySQLRaavareDAO implements RaavareDAO{

	@Override
	public Raavare getRaavare(int raavareId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getraavare = null;
		ResultSet rs = null;
		Raavare raaDTO = null;
		
		String getraa = "SELECT * FROM raavare WHERE raavare_id = ?";
		
		try {
			getraavare = conn.prepareStatement(getraa);
			getraavare.setInt(1, raavareId);
			rs = getraavare.executeQuery();
			if (!rs.first()) throw new DALException("Raavaren med id:  " + raavareId + " findes ikke");
			raaDTO = new Raavare (rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getraavare != null) {
				getraavare.close();
	        }
		}
		return raaDTO;
	}

	@Override
	public List<Raavare> getRaavareList() throws DALException, SQLException {
		List<Raavare> list = new ArrayList<Raavare>();

		Connection conn = Connector.getConn();
		PreparedStatement getRaavareList = null;
		ResultSet rs = null;
		
		String getRaaList = "SELECT * FROM raavare";
		
		try {
			getRaavareList = conn.prepareStatement(getRaaList);
			rs = getRaavareList.executeQuery();
			while (rs.next()) {
					list.add(new Raavare(rs.getInt("raavare_id"),rs.getString("raavare_leverandoer"),rs.getString("raavare_navn")));
				}
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (getRaavareList != null) {
				getRaavareList.close();
	        }
		}
		return list;
	}

	@Override
	public void createRaavare(Raavare raavare) throws DALException, SQLException {	
		Connection conn = Connector.getConn();
		PreparedStatement createRaavare = null;
		
		String createRaa = "INSERT INTO raavare(raavare_id, raavare_navn, raavare_leverandoer) VALUES ( ? , ? , ? )";

		try {
			createRaavare = conn.prepareStatement(createRaa);
			
			createRaavare.setInt(1, raavare.getRavareId());
			createRaavare.setString(2, raavare.getName());
			createRaavare.setString(3, raavare.getSupplier());
			createRaavare.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (createRaavare != null) {
				createRaavare.close();
	        }
		}
	}
		

	@Override
	public void updateRaavare(Raavare raavare) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateRaavare = null;
		
		String updateRaa = "UPDATE raavare SET raavare_navn = ? , raavare_leverandoer = ? WHERE raavare_id = ?";
		
		try {
			updateRaavare = conn.prepareStatement(updateRaa);
			updateRaavare.setString(1, raavare.getName());
			updateRaavare.setString(2, raavare.getSupplier());
			updateRaavare.setInt(3, raavare.getRavareId());
			updateRaavare.executeUpdate();
		} catch (SQLException e ) {
			//Do error handling
			//TODO
		} finally {
			if (updateRaavare != null) {
				updateRaavare.close();
	        }
		}
	}
}
