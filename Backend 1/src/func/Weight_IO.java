package func;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import DTO.Afvejning ;
import DTO.Personer;
import DTO.Produktbatch;
import daoimpl01917.*;
import daoimpl01917.MySQLStatusDAO;

public class Weight_IO {
	private data.socket.Connection conn;
	private Socket clientSocket;
	private DataOutputStream sendToServer;
	private BufferedReader getFromServer;
	private String responseFromServer;
	
	private MySQLStatusDAO sts = new MySQLStatusDAO();
	private MySQLRaavareBatchDAO raaBa = new MySQLRaavareBatchDAO();
	private MySQLPersonerDAO pers = new MySQLPersonerDAO ();
	private MySQLRaavareDAO raa = new MySQLRaavareDAO();
	private MySQLProduktBatchKompDAO ProBaKoDAO = new MySQLProduktBatchKompDAO();
	private MySQLReceptDAO recpt = new MySQLReceptDAO();	
	private MySQLAfvejningDAO afvDAO = new MySQLAfvejningDAO();

	private Produktbatch proBa = new Produktbatch();
	private Afvejning afv = new Afvejning();
	private Personer persDTO = new Personer();
	private boolean run = false;
	private boolean mainRun = false;


	public Weight_IO(Afvejning afv, String host) throws UnknownHostException, IOException {
		this.afv = afv;

		//Starts connection with the weight simulator.
		conn = new data.socket.Connection(host, 8000);
		clientSocket = conn.SocketConn();
		sendToServer = conn.getWriter();
		getFromServer = conn.getReader();
	}

	/**
	 * In RM20 8 there needs to be exactly 3 words in the message for some reason
	 * @throws IOException
	 */
	public void run() throws IOException {
		try {

			//Do-while loop that keeps asking for user Id until a valid one is entered.
			do {
				//Send text ”Indtast laborant nr” to weight
				sendToServer.writeBytes("RM20 8 \"Indtast laborant nr\"" + " \"\"" + " \"&3\"" + "\r\n");
				responseFromServer = getFromServer.readLine();

				//Input UserId on weight and save it to DTO
				System.out.println("1 " + responseFromServer); //Test
				responseFromServer = getFromServer.readLine();		
				System.out.println("2 " + responseFromServer);
				persDTO.setUserId(retrieveIdAsInt(responseFromServer));

				//If-statement that makes sure the returned name from the database is not null.
				if(pers.findUserName(persDTO.getUserId()) == null){
					System.out.println("Ugyldigt ID");
					sendToServer.writeBytes("RM20 8 ”Ugyldigt laborant nr" + "” ”” ”&3”" + '\n');
					responseFromServer = getFromServer.readLine();		
					responseFromServer = getFromServer.readLine();		
				}

			} while (pers.findUserName(persDTO.getUserId()) == null);

			//Send name from database to weight.
			sendToServer.writeBytes("RM20 8 \"Bruger Navn: \" \"" + pers.findUserName(persDTO.getUserId()) + "\" \"&3\"" + "\r\n");

			System.out.println(pers.findUserName(persDTO.getUserId()));
			responseFromServer = getFromServer.readLine();		
			System.out.println("4 " + responseFromServer);

			//Approve on weight.
			System.out.println("Tryk OK");
			responseFromServer = getFromServer.readLine();		
			System.out.println("5 " + responseFromServer);
			mainRun = true;

			//Start outer loop.
			while (mainRun == true) {	
				//Send Produktbatch text to weight
				do {
					sendToServer.writeBytes("RM20 8 \"Indtast produktbatch nr\"" + " \"\"" + " \"&3\"" + "\r\n");
					responseFromServer = getFromServer.readLine();
					System.out.println("6 " + responseFromServer);

					//Input Produktbatch id on weight
					responseFromServer = getFromServer.readLine();		//Save
					proBa.setPbId(retrieveIdAsInt(responseFromServer)); //Sets productBatch id in DAO.
					String str = recpt.findReceptName(proBa.getPbId());
					System.out.println("recept navn " + str);
					System.out.println("7 " + responseFromServer);

					//Checks if pb_id is valid.
					if(recpt.findReceptName(proBa.getPbId()) == null){
						System.out.println("Ugyldigt ID");
						sendToServer.writeBytes("RM20 8 \"Ugyldigt produktbatch nr\"" + " \"\"" + " \"&3\"" + "\r\n");
						responseFromServer = getFromServer.readLine();		
						responseFromServer = getFromServer.readLine();		
					}

				} while (recpt.findReceptName(proBa.getPbId()) == null);

				//Send recept name from database to weight
				sendToServer.writeBytes("RM20 8 \"Recept navn: \" \"" + recpt.findReceptName(retrieveIdAsInt(responseFromServer)) + "\" \"&3\"" + "\r\n");
				responseFromServer = getFromServer.readLine();
				System.out.println("8 " + responseFromServer);
				System.out.println("Tryk OK ");

				//Press OK on weight
				responseFromServer = getFromServer.readLine();
				System.out.println("9" + responseFromServer);

				//Statement that makes sure we can't start with a productbatch with status = 2.
				if(sts.checkIfDone(proBa.getPbId()) == false){
					run = true;
					while(run) {
						//Send text to weight
						sendToServer.writeBytes("RM20 8 \"Vaegten skal ubelastes\"" + " \"\"" + " \"&3\"" + "\r\n");
						responseFromServer = getFromServer.readLine();
						System.out.println("10" + responseFromServer);
						responseFromServer = getFromServer.readLine();
						System.out.println("Tryk OK ");

						//Send tara command to weight 
						sendToServer.writeBytes("T" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("11" + responseFromServer);
						System.out.println("Tryk OK ");

						//Place tara
						sendToServer.writeBytes("RM20 8 \"Afvej venligst tara\"" + " \"\"" + " \"&3\"" + "\r\n");
						responseFromServer = getFromServer.readLine();
						System.out.println("11.5 " + responseFromServer);

						//Send tara weight from weight and press OK
						responseFromServer = getFromServer.readLine();
						System.out.println("12" + responseFromServer);

						//Send S to weight to save tara in DTO
						sendToServer.writeBytes("S" + '\n');
						responseFromServer = getFromServer.readLine();		//Save						
						responseFromServer = strip(responseFromServer);
						afv.setTara(Double.parseDouble(responseFromServer));
						System.out.println("12.5 " + afv.getTara());

						do {
							//Send name of raavare that needs to be weighed from the database to the weight.
							sendToServer.writeBytes("RM20 8 \"Afvej raavaren: \" \"" + raa.findRaavareName(proBa.getPbId()) + "\" \"&3\"" + "\r\n");
							responseFromServer = getFromServer.readLine();
							responseFromServer = getFromServer.readLine();

							//Asks for rb_id from user on weight and saves it in DTO.
							sendToServer.writeBytes("RM20 8 \"Indtast raavareBatch nr\"" + " \"\"" + " \"&3\"" + "\r\n");
							responseFromServer = getFromServer.readLine();
							responseFromServer = getFromServer.readLine();
							afv.setRbId(retrieveIdAsInt(responseFromServer));
							System.out.println("RaavareBatch ID: " + afv.getRbId());

							//Makes sure that the rb_id is valid and sends ”Ugyldigt raavarebatch nr " to the weight if not.
							if(!raaBa.iterateRb(afv.getRbId())){
								System.out.println("Ugyldigt ID");
								sendToServer.writeBytes("RM20 8 \"Ugyldigt raavarebatch nr\"" + " \"\"" + " \"&3\"" + "\r\n");
								responseFromServer = getFromServer.readLine();		
								responseFromServer = getFromServer.readLine();		
							}

						} while (!raaBa.iterateRb(afv.getRbId()));

						//Send text to weight
						sendToServer.writeBytes("RM20 8 \"Afvej venligst netto\"" + " \"\"" + " \"&3\"" + "\r\n");
						responseFromServer = getFromServer.readLine();
						System.out.println("15 " + responseFromServer);
						
						//Place netto on weight and press OK
						responseFromServer = getFromServer.readLine();
						System.out.println("16" + responseFromServer);
						
						//Save netto from weight to DTO
						sendToServer.writeBytes("S" + '\n');
						responseFromServer = getFromServer.readLine();
						responseFromServer = strip(responseFromServer);
						afv.setNetto(Double.parseDouble(responseFromServer));
						System.out.println("17.5" + afv.getNetto());
						
						//Asks weight for current brutto and saves it in bruttoDTO.
						sendToServer.writeBytes("T" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("18" + responseFromServer);
						afv.setBrutto(Double.parseDouble(strip(responseFromServer)));
						System.out.println("Double parsed as: " + afv.getBrutto() );
						
						//Asks user to remove brutto on weight.
						sendToServer.writeBytes("RM20 8 \"Fjern venligst brutto\"" + " \"\"" + " \"&3\"" + "\r\n");
						responseFromServer = getFromServer.readLine();
						System.out.println("19" + responseFromServer);

						//Remove brutto from weight and press ok.
						responseFromServer = getFromServer.readLine();
						System.out.println("20 " + responseFromServer);

						//Checks if the weighed netto is within tolerance of the nom_netto in the recept.
						if(afvDAO.checkTolerance(afv.getRbId(), afv.getNetto(), proBa.getPbId()) || bruttoCheck(afv.getBrutto())) {
//						if(true) {
							//Starts SQL transaction.
							ProBaKoDAO.insertProBaKomRow(proBa.getPbId(), afv.getRbId(),afv.getTara(),afv.getNetto(), persDTO.getUserId());
							System.out.println("Success! Gemt i database.");
							
							//Prints succes to console and weight
							sendToServer.writeBytes("RM20 8 \"Afvejnings status OK\"" + " \"\"" + " \"&3\"" + "\r\n");
							responseFromServer = getFromServer.readLine();
							System.out.println("22 " + responseFromServer);
							responseFromServer = getFromServer.readLine();
							
							//Update status and check if done. Then set run = true or false.
							sts.updateStatus(proBa.getPbId());
							run = !(sts.checkIfDone(proBa.getPbId()));
							
						} else {
							//break loop
							System.out.println("Fejl");
							//Send error message to weight.
							sendToServer.writeBytes("RM20 8 \"Afvejnings status: Fejl\"" + " \"\"" + " \"&3\"" + "\r\n");
							responseFromServer = getFromServer.readLine();
							System.out.println("22 " + responseFromServer);
							responseFromServer = getFromServer.readLine();
							
							System.out.println("Nom_netto: " + afvDAO.getNom_netto(afv.getRbId(), proBa.getPbId()));
							//Sets run = false to break while loop.
							run = false;
						}
					}
					//Else statement for trying to weigh a produktbatch that is already complete. 
				} else {
					System.out.println("Ikke tilladt");

					sendToServer.writeBytes("RM20 8 \"Er allerede faerdig!\"" + " \"\"" + " \"&3\"" + "\r\n");
					responseFromServer = getFromServer.readLine();
					System.out.println("22 " + responseFromServer);
					responseFromServer = getFromServer.readLine();

					run = false;
				}
				//Asks the user if they want to continue.
				sendToServer.writeBytes("RM20 8 \"Fortsaet? Tryk 1\"" + " \"\"" + " \"&3\"" + "\r\n");
				responseFromServer = getFromServer.readLine();
				System.out.println(responseFromServer);
				responseFromServer = getFromServer.readLine();
				int respns = retrieveIdAsInt(responseFromServer);
				if (respns == 1) {
					mainRun = true;
				} else {
					//Sends quit command to weight
					sendToServer.writeBytes("Q" + '\n');
					mainRun = false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			sendToServer.writeBytes("RM20 8 \"Afvejnings status: Kasseret\"" + " \"\"" + " \"&3\"" + "\r\n");
			responseFromServer = getFromServer.readLine();
			System.out.println(responseFromServer);
			responseFromServer = getFromServer.readLine();
			System.out.println(responseFromServer);
		} finally {
			clientSocket.close();
			sendToServer.close();
			getFromServer.close();
		}
	}

	/*
	 * method made for taking in a string
	 * and extracting doubles as a string
	 * ready for Double.parseDouble()
	 * */
	public String strip(String value) {
		String stripped = "";
		for(int i = 0; i< value.length(); i++) {
			if((value.charAt(i) >= '0' && value.charAt(i) <= '9') || value.charAt(i) == '.' )
				stripped += value.charAt(i);
		}
		return stripped;
	}

	//Converts the "int inputs" on the weight into java ints. Works on pb_id and rb_id.
	public int retrieveIdAsInt(String ServerResponse) {
		String tempId = ServerResponse.split(" ")[2];
		tempId = tempId.replaceAll("\\D+","");	
		int foo = Integer.parseInt(tempId);
		return foo;
	}

	//Checks if the registered brutto matches netto + tara.
	public boolean bruttoCheck (double brutto) {
		if(brutto == (afv.getNetto() + afv.getTara())) {
			return true;
		} else {
			return false;			
		}
	}
}