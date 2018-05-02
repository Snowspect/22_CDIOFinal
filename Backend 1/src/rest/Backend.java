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
	private static ArrayList <Personer> perList = new ArrayList<Personer>();

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(Personer per) {
		perList.add(per);
		//perList.add(new Personer(45, "peter", "pl", "12345678", "password", "admin"));
		//perList.add(new Personer(22, "Hans", "hs", "87654321", "kode", "admin"));
		System.out.println("Created user: " + per.toString());
		System.out.println("Current list " + perList.toString());
		
		String result = "It works, maybe";
		return result;
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() {
		System.out.println("Get list: " + perList.toString());
		return perList;
	}

}