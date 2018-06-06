package func;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import DTO.Afvejning;
import DTO.Personer;
import DTO.RaavareBatch;
import JDBC.Connector;
import user.UserResources;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Afvejning afv = new Afvejning();
		UserResources UsRe = new UserResources();
//		UsRe.getPerList().add(new Personer(12, "Hans", "hs", "87654321", "kode", "admin"));
//		Personer pers = new Personer();
//		RaavareBatch RaaBa = new RaavareBatch();
		try { new Connector(); 
		System.out.println("DB connection works, maybe!");} 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }	
		Weight_IO io = new Weight_IO(afv);
		io.run();
	}

}

