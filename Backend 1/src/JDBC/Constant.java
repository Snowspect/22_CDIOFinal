package JDBC;


// Constant value sthat define the databasse connection information

public abstract class Constant
{
	public static final String
		server					= "diplomportal.c2nouactg6m6.eu-west-1.rds.amazonaws.com",  // database-serveren
		database				=  "s155384",  //"jdbcdatabase", // navnet paa din database = dit studienummer
		username				= "s155384", // dit brugernavn = dit studienummer 
		password				= "6x6yPWuu4lS81QIdatabasestatus"; // dit password som du har valgt til din database
	
	public static final int
		port					= 3306;
}