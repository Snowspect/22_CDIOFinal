package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Produktbatch;
import DTO.produktBatchKompDTO;
import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchDAO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO {

	// Creates a Produktbatch in the database with the information from the DTO parameter.
	@Override
	public String createProduktBatch(Produktbatch pb) throws DALException, SQLException, FoundException, NotFoundException {		
		Connection conn = Connector.getConn();
		PreparedStatement createProBatch = null;
		PreparedStatement createProBaKomponent = null;

		String createProBa = "INSERT INTO produktbatch(pb_id, status, recept_id) VALUES " + 
				"(?, ?, ?)";
		try {
			createProBatch = conn.prepareStatement(createProBa);

			createProBatch.setInt(1, pb.getPbId());
			createProBatch.setInt(2, pb.getStatus());
			createProBatch.setInt(3, pb.getReceptId());
			createProBatch.executeUpdate();
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062)
		        throw new FoundException("Produktbatch already exists - Pbx01"); 
			if(e.getErrorCode() == 1452)
				throw new NotFoundException("Recept id does not exists, please create it first - Pbx02");
			
			System.out.println(e);
			e.printStackTrace();
			return "unknown error, please contact technical department with error code Pbx03";
		} finally {
			if (createProBatch != null) {
				createProBatch.close();
			}
		}
		return "produktbatch created";
		}

	//Returns a list of ProduktBatch from the database.
	@Override
	public List<Produktbatch> getProduktBatchList() throws DALException, SQLException, NotFoundException {
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
			System.out.println(e);
		} finally {
			if (getProdBatchList != null) {
				getProdBatchList.close();
			}
		}
		return list;
	}
}
