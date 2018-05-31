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
import DTO.Recept;

@Path("/stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockResources {
	private static ArrayList <Raavare> ravareList = new ArrayList<>();
	private static ArrayList <Recept> receptList = new ArrayList<>();

	//*** Ravare ***//
	//PUT
	@PUT
	@Path("{ravareNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Raavare rav) {
		ravareList.add(rav);
		
		System.out.println("Created user: " + rav.toString());
		System.out.println("Current list " + ravareList.toString());
		
		String result = "created ravare";
		return result;
	}
	
	//GET
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Raavare> getRavare()
	{
		return ravareList;
	}
	
	//POST
	@POST
	@Path("{ravareNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(Raavare rav) {
		
		for (Raavare ravare : ravareList) {
			if(ravare.getRavareId() == rav.getRavareId())
			{
				ravare.setRavareId(rav.getRavareId());
				ravare.setName(rav.getName());
				ravare.setSupplier(rav.getSupplier());
			}
		}			
		return "Updated råvare";
	}
	
	//DELETE
	@DELETE
	@Path("{ravreNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(int ravareNr)
	{
		ravareList.removeIf(e-> e.getRavareId() == ravareNr);
		return "deleted ravare";
	}
	
	//*** Recept ***//
	//PUT 
	@PUT
	@Path("{receptNr}")
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
	@Path("")
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
	
	//DELETE
	
	//*** ravareBatch ***//
	//PUT
	
	//GET
	
	//POST
	
	//DELETE
	
	//*** productBatch ***//
	//PUT
	
	//GET
	
	//POST
	
	//DELETE
}