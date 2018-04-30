package rest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/ingredient")
public class ingredient {

	@POST
	@Path("/form")
	@Consumes("application/x-www-form-urlencoded")
	public String setIngredient(@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("amount") String amount) {

		String result = "The ingredient added has id-number: " + id + " and the name " + name +
				". The amount added was " + amount + ".";

		return result;
	}
	
	@POST
	@Path("/query")
	@Consumes("application/x-www-form-urlencoded")
	public String setQueryIngredient(@QueryParam("id") String id,
			@QueryParam("name") String name,
			@QueryParam("amount") String amount) {

		String result = "The ingredient added has id-number: " + id + " and the name " + name +
				". The amount added was " + amount + ".";

		return result;
	}
}