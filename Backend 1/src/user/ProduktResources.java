package user;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Produktbatch;
import DTO.ProduktBatchKompDTO;
import daoimpl01917.MySQLPersonerDAO;
import daoimpl01917.MySQLProduktBatchDAO;
import daoimpl01917.MySQLProduktBatchKompDAO;
import daointerfaces01917.DALException;

@Path("/produktbatch")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProduktResources {
	MySQLProduktBatchDAO proConn = new MySQLProduktBatchDAO();
	MySQLProduktBatchKompDAO pbkomp = new MySQLProduktBatchKompDAO();
	private static ArrayList <Produktbatch> proConnList = new ArrayList<>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Produktbatch batch) throws FoundException, DALException, SQLException
	{
		proConn.createProduktBatch(batch);
		
		String result = "created productbatch";
		return result;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Produktbatch> getProduktbatch() throws DALException, SQLException
	{

		proConnList = (ArrayList<Produktbatch>) proConn.getProduktBatchList();
		return proConnList;
	}
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ProduktBatchKompDTO> getProduktBatchKomponenter(@PathParam("id") int id) throws DALException, SQLException
	{
		return (ArrayList<ProduktBatchKompDTO>) pbkomp.getProduktBatchKompList(id);
	}
}