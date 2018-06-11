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
import DTO.produktBatchKompDTO;
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
	
	/**
	 * POST a new batch DTO to the database
	 * @param batch
	 * @return a string : "created productbatch"
	 * @throws FoundException
	 * @throws DALException
	 * @throws SQLException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Produktbatch batch) throws FoundException, DALException, SQLException
	{
		proConn.createProduktBatch(batch);		
		String result = "created productbatch";
		return result;
	}
	
	/**
	 * @return an arraylist : of produktbatchkomponents
	 * @throws DALException
	 * @throws SQLException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Produktbatch> getProduktbatch() throws DALException, SQLException
	{
		return (ArrayList<Produktbatch>) proConn.getProduktBatchList();
	}
	
	/**
	 * 
	 * @param id
	 * @return an arraylist: of produktbatchkomponents related to the param id
	 * @throws DALException
	 * @throws SQLException
	 */
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<produktBatchKompDTO> getProduktBatchKomponenter(@PathParam("id") int id) throws DALException, SQLException
	{
		return (ArrayList<produktBatchKompDTO>) pbkomp.getProduktBatchKompList(id);
	}
}