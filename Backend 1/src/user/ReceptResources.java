package user;

import java.sql.SQLException;
import java.util.ArrayList;  

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Recept;
import daoimpl01917.MySQLReceptDAO;
import daointerfaces01917.DALException;

@Path("/recept")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ReceptResources {
	MySQLReceptDAO test = new MySQLReceptDAO();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Recept rec) throws FoundException, DALException, SQLException
	{
		String result = null;
		try {
			result = test.createRecept(rec);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	//GET
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Recept> getRecept() throws DALException, SQLException
	{
		return (ArrayList<Recept>) test.getReceptList();
	}
}
