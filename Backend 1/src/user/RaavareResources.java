package user;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.NotFoundException;
import DTO.Raavare;

@Path("/raavare")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class RaavareResources {

	private static ArrayList <Raavare> raavareList = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Raavare rav)
	{ try {
		raavareList.add(rav);

		System.out.println("Created raavare: " + rav.toString());
		System.out.println("Current list " + raavareList.toString());

		String result = "created ravare";


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

	public ArrayList<Raavare> getRaavare(){

		return raavareList;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	public String update(Raavare rav) throws NotFoundException {
		boolean found = false;
		
		for(Raavare Rav : raavareList) {
			if(Rav.getRavareId() == rav.getRavareId())
			{
				Rav.setRavareId(rav.getRavareId());
				Rav.setName(rav.getName());
				Rav.setSupplier(rav.getSupplier());
				found = true;
			}
		}
		if (!found) {
			throw new NotFoundException("Raavaren findes ikke");
		}
		
		return "Updated Raavare";
	}

}