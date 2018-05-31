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

import DTO.Personer;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {
	private static ArrayList <Personer> perList = new ArrayList<Personer>();

	//Inserts new user into system
	@PUT
	@Path("{id}")
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

	//Gets list of users
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() {
		System.out.println("Get list: " + perList.toString());
		return perList;
	}

	//removes user from list
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(int id)
	{
		perList.removeIf(e-> e.getUserId() == id);
	}
	
	//Updates a user
	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(Personer per)
	{
		for (Personer person : perList) {
			if(person.getUserId() == per.getUserId())
			{
				person.setUserId(per.getUserId());
				person.setUserName(per.getUserName());
				person.setCpr(per.getCpr());
				person.setIni(per.getIni());
				person.setPassword(per.getPassword());
				person.setRoles(per.getRoles());
			}
		}
	}
}