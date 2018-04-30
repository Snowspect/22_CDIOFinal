package rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("hello")
public class HelloService {
	
	@GET
	public String getHello() {
		return "Hello";
	}

//	@POST
//	public String setHello(String str) {
//		return "Hello " +  str;
//	}
	@POST
	public String setIngredient() {
		String result = "";
		//Error
		@FormParam("id") String id;
		@FormParam("name") String name;
		@FormParam("amount") String amount;
		
		return result;
	}
}
