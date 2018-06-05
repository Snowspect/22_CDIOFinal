package user;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Produktbatch;
import DTO.Raavare;

@Path("/produktbatch")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProduktResources {
	private static ArrayList <Produktbatch> produktList = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Produktbatch batch) throws FoundException
	{
		boolean found = false;
		for (Produktbatch produktBatch : produktList) {
			if (batch.getPbId() == produktBatch.getPbId()) {
				found = true;
			}
		}
		if (found) {
			throw new FoundException("Produktbatchen findes allerede");
		}
		
		produktList.add(batch);
		
		System.out.println("Created user: " + batch.toString());
		System.out.println("Current list " + produktList.toString());
		
		String result = "created productbatch";
		return result;
	}
	
	//GET
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Produktbatch> getProduktbatch()
	{
		return produktList;
	}	
}