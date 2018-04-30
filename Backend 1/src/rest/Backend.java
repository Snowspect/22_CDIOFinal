package rest;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.Personer;

@Path("/backend")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Backend {

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(
			@FormParam("userId") int userId,
			@FormParam("userName") String userName,
			@FormParam("ini") String ini,
			@FormParam("cpr") String cpr,
			@FormParam("password") String password,
			@FormParam("roles") List <String> roles) {
		
		//TODO add to list
		Personer test = new Personer(userId, userName, ini, cpr, password, roles);
		System.out.println(test.toString());
		
		String result = "It works, maybe";
		
		//Old
		//String result = "The ingredient added has id-number: " + id + " and the name " + name +
		//		". The amount added was " + amount + ".";
		return result;
	}
	
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