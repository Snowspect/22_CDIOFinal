package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Raavare;

public class MySQLRaavareDAO implements RaavareDAO{

/*	@Override
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
*/
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
					list.add(new Raavare(rs.getInt("raavare_id"),rs.getString("leverandoer"),rs.getString("raavare_navn")));
				}
		} catch (SQLException e ) {
			System.out.println(e);
			//TODO
		} finally {
			if (getRaavareList != null) {
				getRaavareList.close();
	        }
		}
		return list;
	}

	@Override
	public String createRaavare(Raavare raavare) throws DALException, SQLException, FoundException {	
		Connection conn = Connector.getConn();
		PreparedStatement createRaavare = null;
		
		String createRaa = "INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES ( ? , ? , ? )";

		try {
			createRaavare = conn.prepareStatement(createRaa);
			
			createRaavare.setInt(1, raavare.getRavareId());
			createRaavare.setString(2, raavare.getName());
			createRaavare.setString(3, raavare.getSupplier());
			createRaavare.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new FoundException("RaavareBatchen findes allerede");
		} catch (SQLException e ) {
			System.out.println(e);
			return "sql fejl ikke relateret til eksisterende id, tjek consol";
		} finally {
			if (createRaavare != null) {
				createRaavare.close();
	        }
		}
		return "raavare oprettet";
	}
		

	@Override
	public String updateRaavare(Raavare raavare) throws DALException, SQLException, NotFoundException{
		Connection conn = Connector.getConn();
		PreparedStatement updateRaavare = null;		
				
		System.out.println("heoo");
		String updateRaa = "UPDATE raavare SET raavare_navn = ? , leverandoer = ? WHERE raavare_id = ?";
		int NumberOfRows = -1;
		try {
			updateRaavare = conn.prepareStatement(updateRaa);
			updateRaavare.setString(1, raavare.getName());
			updateRaavare.setString(2, raavare.getSupplier());
			updateRaavare.setInt(3, raavare.getRavareId());
			NumberOfRows = updateRaavare.executeUpdate();
		} catch (SQLException e ) {
				System.out.println(e);
				return "sql fejl ikke relateret til mangel p� eksistens af raavare, se consol";
		} finally {
			if (updateRaavare != null) {
				updateRaavare.close();
	        }
		}
		if(NumberOfRows == 0)
		{
			throw new NotFoundException("Raavaren eksisterer ikke");
		}
		return "Raavaren er opdateret";
	}
	
	public String findRaavareName (int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		PreparedStatement getWeighed = null;
		PreparedStatement getToWeigh = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int count = 0;
		String name = null;

		PreparedStatement getRaavareName = null;	
		ResultSet rs = null;

		//Ser hvilke råvare vi mangler 
		String getName = "SELECT raavare_navn FROM raavare WHERE raavare_id = ?;";

		//Det vi har vejet	
		String getWeighedItems = "SELECT raavare_id FROM raavarebatch WHERE rb_id IN (SELECT rb_id FROM produktbatchkomponent WHERE pb_id = ?);";
		//Det vi skal veje
		String getToWeighItems = "SELECT raavare_id FROM receptkomponent WHERE recept_id = (SELECT recept_id FROM produktbatch WHERE pb_id = ?);";

		try {

			getWeighed = sqlCon.prepareStatement(getWeighedItems);
			getWeighed.setInt(1,pb_id);
			rs1 = getWeighed.executeQuery();

			// Go to the last row 
			rs1.last(); 
			int rowCount = rs1.getRow(); 

			// Reset row before iterating to get data 
			rs1.beforeFirst();

			int [] checkerArr1 = new int [rowCount];
			int arrayCount = 0;

			while(rs1.next()) {
				checkerArr1[arrayCount] = rs1.getInt(1);
				arrayCount++;
			}
			System.out.println("Arary 1: \n" + Arrays.toString(checkerArr1));

			//Get second array from database
			getToWeigh = sqlCon.prepareStatement(getToWeighItems);
			getToWeigh.setInt(1,pb_id);
			rs2 = getToWeigh.executeQuery();

			// Go to the last row 
			rs2.last(); 
			int rowCount2 = rs2.getRow(); 

			// Reset row before iterating to get data 
			rs2.beforeFirst();

			int [] checkerArr2 = new int [rowCount2];
			int arrayCount2 = 0;
			int compare = 0;

			while(rs2.next()) {
				checkerArr2[arrayCount2] = rs2.getInt(1);
				arrayCount2++;
			}
			System.out.println("Arary 2: \n" + Arrays.toString(checkerArr2));

			//compare arrays
			for (int i = 0; i < checkerArr1.length; i++) {
				if(checkerArr1[i] != checkerArr2[i]) {
					compare = checkerArr2[i]; 
					count++;
					break;
				}
			}
			if(count == checkerArr2.length) {
				return "Opfyldt";
			}

			getRaavareName = sqlCon.prepareStatement(getName);

			getRaavareName.setInt(1, compare);
			rs = getRaavareName.executeQuery();
			if(rs.first()) {
				name = rs.getString("raavare_navn");
				System.out.println(name);
				return name;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(getRaavareName != null) {
				getRaavareName.close();
			}
		}
		return name;
	}
	
}