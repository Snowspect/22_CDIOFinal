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
	//*** ravareBatch ***//
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(RaavareBatch ravBat) throws FoundException, DALException, SQLException
	{		
//		GET liste først og derefter tjekkke? eller kør den direkte ind og kør exception handling der?
//				- dvs gør hvert felt unikt men ikke en auto increment.
		MySQLRaavareBatchDAO test = new MySQLRaavareBatchDAO();
		String result = test.createRaavareBatch(ravBat);
		
	 /* boolean found = false;
		for (RaavareBatch raavareBatch : ravareBatchList) {
			if (ravBat.getRbId() == raavareBatch.getRbId()) {
				found = true;
			}
		}
		if (found) {
			throw new FoundException("RaavareBatchen findes allerede");
		}*/
		
		// ravareBatchList.add(ravBat);

		System.out.println("Created raavareBatch: " + ravBat.toString());
		System.out.println("Current list " + ravareBatchList.toString());

		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RaavareBatch> getRaavarebatch() throws DALException, SQLException{
		MySQLRaavareBatchDAO test = new MySQLRaavareBatchDAO();
		ravareBatchList = (ArrayList<RaavareBatch>) test.getRaavareBatchList();
		return ravareBatchList;
	}
	
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String update(RaavareBatch ravBat) {
//
//		for(RaavareBatch RavBatch : ravareBatchList)
//			if(RavBatch.getRbId() == ravBat.getRbId())
//			{
//				RavBatch.setRbId(ravBat.getRbId());
//				RavBatch.setRaavareId(ravBat.getRaavareId());
//				RavBatch.setMaengde(ravBat.getMaengde());
//			}
//		return "Updated RaavareBatch";
//	}

//	@DELETE
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String delete(int rbId)
//	{
//		ravareBatchList.removeIf(e-> e.getRbId() == rbId);
//		return "deleted ravareBatch";
//	}
}