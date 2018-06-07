package user;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Produktbatch;
import daoimpl01917.MySQLPersonerDAO;
import daoimpl01917.MySQLProduktBatchDAO;
import daointerfaces01917.DALException;

@Path("/produktbatch")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProduktResources {
	MySQLProduktBatchDAO proConn = new MySQLProduktBatchDAO();
	private static ArrayList <Produktbatch> proConnList = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Produktbatch batch) throws FoundException
	{
		boolean found = false;
		for (Produktbatch produktBatch : proConnList) {
			if (batch.getPbId() == produktBatch.getPbId()) {
				found = true;
			}
		}
		if (found) {
			throw new FoundException("Produktbatchen findes allerede");
		}
		
		try {
			proConn.createProduktBatch(batch);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Created user: " + batch.toString());
		System.out.println("Current list " + proConnList.toString());
		
		String result = "created productbatch";
		return result;
	}
	
	//GET
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Produktbatch> getProduktbatch()
	{
		
		return proConnList;
	}	
}