package rest;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.Personer;

@Path("/backend")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Backend {
//	private ArrayList <Personer> perList = new ArrayList<Personer>();
	UserList perList = new UserList();

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(Personer per) {
		
		//Causes code: 500 (internal server error)
		perList.addPerToList(per);
		perList.addPerToList(new Personer(45, "peter", "pl", "12345678", "password", "admin"));
		perList.addPerToList(new Personer(22, "Hans", "hs", "87654321", "kode", "admin"));
		System.out.println(perList.getPerList().toString());
		
		//TODO add to list
		System.out.println(per.toString());
		
		String result = "It works, maybe";
		//Old
		//String result = "The ingredient added has id-number: " + id + " and the name " + name +
		//		". The amount added was " + amount + ".";
		return result;
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() {
		//Personer per = new Personer(45, "peter", "pl", "12345678", "password", "admin");
		System.out.println("Get list: " + perList.getPerList().toString());
		return perList.getPerList();
	}

//	public ArrayList<Personer> getList() {
//		return perList;
//	}
//
//	public void setList(ArrayList<Personer> perList) {
//		this.perList = perList;
//	}
	
	//Notes
	//Consumes(MediaType.APPLICATION_JSON)
	//Produces(MediaType.APPLICATION_JSON)
	
	
	
//	@POST
//	@Path("/query")
//	@Consumes("application/x-www-form-urlencoded")
//	public String setQueryIngredient(@QueryParam("id") String id,
//			@QueryParam("name") String name,
//			@QueryParam("amount") String amount) {
//
//		String result = "The ingredient added has id-number: " + id + " and the name " + name +
//				". The amount added was " + amount + ".";
//
//		return result;
//	}
	
	
	
	
}