package user;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import DTO.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	//takes a NotFoundException parameter and returns a response message with status 404 with message
	@Override
	public Response toResponse(NotFoundException exception) {
		return Response.status(Status.NOT_FOUND).entity("{msg:"+exception.getMessage()+"}").build();
	}
}