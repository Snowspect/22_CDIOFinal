package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import DTO.OperatoerDTO;
import DTO.Produktbatch;
import daointerfaces01917.ProduktBatchDAO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO {
	@Override
	public Produktbatch getProduktBatch(int pbId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getProBatch = null;
		ResultSet rs = null;
		Produktbatch PbDTO = null;
		
		String getProBa = "SELECT * FROM produktbatch WHERE pb_id = ?";
		
		try {
			getProBatch = conn.prepareStatement(getProBa);
			getProBatch.setInt(1, pbId);
			rs = getProBatch.executeQuery();
			if (!rs.first()) throw new DALException("Produktbatchet " + pbId + " findes ikke");
			PbDTO = new Produktbatch (rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id"));
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (getProBatch != null) {
				getProBatch.close();
			}
		}
		return PbDTO;
	}
	

	
	@Override
	public void createProduktBatch(Produktbatch pb) throws DALException, SQLException {		
		Connection conn = Connector.getConn();
		PreparedStatement createProBatch = null;

		String createProBa = "INSERT INTO produktbatch(pb_id, status, recept_id) VALUES " + 
				"(?, ?, ?)";

		try {
			createProBatch = conn.prepareStatement(createProBa);

			createProBatch.setInt(1, pb.getPbId());
			createProBatch.setInt(2, pb.getStatus());
			createProBatch.setInt(3, pb.getReceptId());
			createProBatch.executeUpdate();
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (createProBatch != null) {
				createProBatch.close();
			}
		}
	}
	@Override
	public void updateProduktBatch(Produktbatch pb) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateProBatch = null;

		String updateProBa = "UPDATE produktbatch SET pb_id = ? , status = ? , recept_id = ? WHERE pr_id = ?";

		try {
			updateProBatch = conn.prepareStatement(updateProBa);

			updateProBatch.setInt(1, pb.getPbId());
			updateProBatch.setInt(2, pb.getStatus());
			updateProBatch.setInt(3, pb.getReceptId());
			updateProBatch.setInt(4, pb.getPbId());
			updateProBatch.executeUpdate();
		} catch (SQLException e) {
			//Do error handling
			//TODO
		} finally {
			if (updateProBatch != null) {
				updateProBatch.close();
			}
		}
	}

	@Override
	public List<Produktbatch> getProduktBatchList() throws DALException, SQLException {
		List<Produktbatch> list = new ArrayList<Produktbatch>();

		Connection conn = Connector.getConn();
		PreparedStatement getProdBatchList = null;
		ResultSet rs = null;

		String getProBaList = "SELECT * FROM produktbatch";

		try {
			getProdBatchList = conn.prepareStatement(getProBaList);
			rs = getProdBatchList.executeQuery();
			while (rs.next()) 
			{
				list.add(new Produktbatch(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id")));
			}
		} catch (SQLException e) { 
			//throw new DALException(e);
			//Do error handling
			//TODO
		} finally {
			if (getProdBatchList != null) {
				getProdBatchList.close();
			}
		}
		return list;
	}
}
