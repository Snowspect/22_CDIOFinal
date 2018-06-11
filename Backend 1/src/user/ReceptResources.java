package user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Recept;
import DTO.ReceptKompDTO;
import daoimpl01917.MySQLReceptDAO;
import daoimpl01917.MySQLReceptKompDAO;
import daointerfaces01917.DALException;

@Path("/recept")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ReceptResources {
	MySQLReceptDAO recpt = new MySQLReceptDAO();
	MySQLReceptKompDAO recptkomp = new MySQLReceptKompDAO();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Recept rec) throws FoundException, DALException, SQLException
	{
		String result = null;
		try {
			result = recpt.createRecept(rec);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Recept> getRecept() throws DALException, SQLException
	{
		return (ArrayList<Recept>) recpt.getReceptList();
	}
	
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ReceptKompDTO> getReceptRaavare(@PathParam("id") int id) throws DALException, SQLException
	{
		return (ArrayList<ReceptKompDTO>) recptkomp.getReceptKompList(id);
	}
}