package user;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Personer;
import daoimpl01917.MySQLPersonerDAO;
import daointerfaces01917.DALException;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {
	MySQLPersonerDAO pers = new MySQLPersonerDAO();
	private static ArrayList <Personer> perList = new ArrayList<Personer>();
	
	//Inserts new user into system
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(Personer per) throws FoundException, DALException, SQLException 
	{		
		System.out.println("foer createPersoner");
		pers.createPersoner(per);
		
		System.out.println("Created user: " + per.toString()); //LOGGER

		String result = "Bruger tilføjet til databasen";
		return result;
	}

	//Gets list of users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() throws DALException, SQLException {
		System.out.println("Get list: " + pers.getPersonerList().toString()); //LOGGER
		
		return pers.getPersonerList();
	}
	
	//removes user from list
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	//placed inside the parameter list of deleteUser -- @PathParam("id")
	public void deleteUser(@PathParam("id") int id) throws NotFoundException, DALException, SQLException
	{
		pers.deletePersoner(id);
	}

	public static ArrayList<Personer> getPerList() {
		return perList;
	}


	//Updates a user
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(Personer per) throws NotFoundException, DALException, SQLException
	{
				pers.updatePersoner(per);
	}
}