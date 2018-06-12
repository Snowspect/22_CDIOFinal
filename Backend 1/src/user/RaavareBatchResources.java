package user;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mysql.jdbc.Connection;

import DTO.FoundException;
import DTO.Raavare;
import DTO.RaavareBatch;
import daoimpl01917.MySQLRaavareBatchDAO;
import daointerfaces01917.DALException;

@Path("/raavarebatch")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class RaavareBatchResources {

	private static ArrayList <RaavareBatch> ravareBatchList = new ArrayList<>();
	MySQLRaavareBatchDAO ravB = new MySQLRaavareBatchDAO();
	
	// inserts raavareBatch into database
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(RaavareBatch ravBat) throws FoundException, DALException, SQLException
	{		
		String result = ravB.createRaavareBatch(ravBat);

		System.out.println("Created raavareBatch: " + ravBat.toString());
		System.out.println("Current list " + ravareBatchList.toString());

		return result;
	}

	// gets list of raavarebatches from database
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RaavareBatch> getRaavarebatch() throws DALException, SQLException{
		return (ArrayList<RaavareBatch>) ravB.getRaavareBatchList();
	}
}