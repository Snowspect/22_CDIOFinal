package user;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import DTO.Raavare;
import DTO.RaavareBatch;

@Path("/raavarebatch")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class RaavareBatchResources {

	private static ArrayList <RaavareBatch> ravareBatchList = new ArrayList<>();

	//*** ravareBatch ***//
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(RaavareBatch ravBat)
	{ try {
		ravareBatchList.add(ravBat);

		System.out.println("Created raavareBatch: " + ravBat.toString());
		System.out.println("Current list " + ravareBatchList.toString());

		String result = "created ravareBatch";


		return result;
	} catch(Exception e) {
		System.out.println("hejsa2");
	}finally {
		System.out.println("hejsa3");
	}
	return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public ArrayList<RaavareBatch> getRaavarebatch(){

		return ravareBatchList;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(RaavareBatch ravBat) {

		for(RaavareBatch RavBatch : ravareBatchList)
			if(RavBatch.getRbId() == ravBat.getRbId())
			{
				RavBatch.setRbId(ravBat.getRbId());
				RavBatch.setRaavareId(ravBat.getRaavareId());
				RavBatch.setMaengde(ravBat.getMaengde());
			}
		return "Updated RaavareBatch";
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(int rbId)
	{
		ravareBatchList.removeIf(e-> e.getRbId().equals(rbId));
		return "deleted ravareBatch";
	}
}