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
import DTO.NotFoundException;
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
	
	// inserts a recept into database
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Recept rec) throws FoundException, DALException, SQLException
	{
		return recpt.createRecept(rec);
	}

	// gets a list of recepts from database without their attached raavare components 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Recept> getRecept() throws DALException, SQLException
	{
		return (ArrayList<Recept>) recpt.getReceptList();
	}
	
	// gets a list of raavare components attached to a recept based on a recept id.
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ReceptKompDTO> getReceptRaavare(@PathParam("id") int id) throws DALException, SQLException, NotFoundException
	{
		return (ArrayList<ReceptKompDTO>) recptkomp.getReceptKompList(id);
	}
}