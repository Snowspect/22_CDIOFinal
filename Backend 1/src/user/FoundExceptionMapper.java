package user;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import DTO.FoundException;

@Provider

public class FoundExceptionMapper implements ExceptionMapper<FoundException> {

	@Override
	public Response toResponse(FoundException exception) {
		// TODO Auto-generated method stub
		return Response.status(Status.FOUND).entity("{msg:"+exception.getMessage()+"}").build();
	}
	//if FoundException called then return 302 http code
	
}
