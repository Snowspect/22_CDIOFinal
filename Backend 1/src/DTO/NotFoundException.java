package DTO;

//calls the superclass that is the exceptionMapper defined in the resources folder
public class NotFoundException extends Exception {

	public NotFoundException(String string) {
		super(string);
	}

}

