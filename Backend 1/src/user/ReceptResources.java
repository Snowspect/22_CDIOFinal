package user;

import java.util.ArrayList; 

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.Recept;

@Path("/recept")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ReceptResources {
	private static ArrayList <Recept> receptList = new ArrayList<>();
	
	
	@PUT
//	@Path("{receptNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Recept rec) {
		receptList.add(rec);
		
		System.out.println("Created user: " + rec.toString());
		System.out.println("Current list " + receptList.toString());
		
		String result = "created recept";
		return result;
	}
	
	//GET
	@GET
//	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Recept> getRecept()
	{
		return receptList;
	}
	
	//POST
//	@POST
//	@Path("{receptNr}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String update(Recept rec) {
//		
//		for (Recept recpt : receptList) {
//			if(recpt.getReceptId() == rec.getReceptId())
//			{
//				recpt.setReceptId(rec.getReceptId());
//				recpt.setName(rec.getName());
//				recpt.setSupplier(rec.getSupplier());
//			}
//		}			
//		return "Updated råvare";
//	}
}
