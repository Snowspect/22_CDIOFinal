package user;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import DTO.FoundException;

@Provider
public class FoundExceptionMapper implements ExceptionMapper<FoundException> {

	//takes a FoundException parameter and returns a response message with status 302 with message
	@Override
	public Response toResponse(FoundException exception) {
		return Response.status(Status.FOUND).entity("{msg:"+exception.getMessage()+"}").build();
	}
}
