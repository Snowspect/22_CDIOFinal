package func;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import DTO.Afvejning ;
import DTO.Personer;
import DTO.Produktbatch;
import DTO.RaavareBatch;
import user.UserResources;
import DTO.*;

public class Weight_IO {
	private data.socket.Connection conn;
	private Socket clientSocket;
	private DataOutputStream sendToServer;
	private BufferedReader getFromServer;
	private String responseFromServer, messageToServer, status = "";
	private Afvejning afv = new Afvejning();
	private StatusDTO sts = new StatusDTO();
	private Personer pers = new Personer ();
	private RaavareBatch raav = new RaavareBatch();
	private Produktbatch proBa = new Produktbatch();
	private Recept recpt = new Recept();	
	private int id;
	private UserResources UsRe = new UserResources();
	private produktBatchKompDTO ProBaKoDTO = new produktBatchKompDTO();
	private boolean run = false;
	private boolean mainRun = false;
	private int foo;



	public Weight_IO(Afvejning afv) throws UnknownHostException, IOException {
		this.afv = afv;

		conn = new data.socket.Connection("127.0.0.1", 8000);
		clientSocket = conn.SocketConn();
		sendToServer = conn.getWriter();
		getFromServer = conn.getReader();
	}

	/**
	 * In RM20 8 there needs to be exactly 3 words in the message for some reason
	 * @throws IOException
	 */
	public void run() throws IOException //Run() skal skrives om.
	{
		try {

			do {
				//Send text to weight
				sendToServer.writeBytes("RM20 8 ”Indtast laborant nr” ”” ”&3”" + '\n');
				responseFromServer = getFromServer.readLine();

				//Input UserId on weight
				System.out.println("1 " + responseFromServer); //Test
				responseFromServer = getFromServer.readLine();		
				System.out.println("2 " + responseFromServer);
				foo = retrieveIdAsInt(responseFromServer);

				if(pers.findUserName(foo) == null){
					System.out.println("Ugyldigt ID");
					sendToServer.writeBytes("RM20 8 ”Ugyldigt laborant nr" + "” ”” ”&3”" + '\n');
					responseFromServer = getFromServer.readLine();		
					responseFromServer = getFromServer.readLine();		
				}

			} while (pers.findUserName(foo) == null);

			//Send name to weight
			sendToServer.writeBytes("RM20 8 ”t Navn: " + pers.findUserName(foo) + "” ”” ”&3”" + '\n');

			System.out.println(pers.findUserName(foo));
			responseFromServer = getFromServer.readLine();		
			System.out.println("4 " + responseFromServer);

			//Approve
			System.out.println("Tryk OK");
			responseFromServer = getFromServer.readLine();		
			System.out.println("5 " + responseFromServer);
			mainRun = true;

			//Start outer loop
			while (mainRun == true) {	
				//Send Produktbatch text to weight
				do {
				sendToServer.writeBytes("RM20 4 ”Indtast Produktbatch nr” ”” ”&3”" + '\n');
				responseFromServer = getFromServer.readLine();
				System.out.println("6 " + responseFromServer);

				//Input Produktbatch id on weight
				responseFromServer = getFromServer.readLine();		//Save
				proBa.setPbId(retrieveIdAsInt(responseFromServer)); //Sets productBatch id in DTO.
				String str = recpt.findReceptName(proBa.getPbId());
				System.out.println("recept navn " + str);
				System.out.println("7 " + responseFromServer);

				
				if(recpt.findReceptName(proBa.getPbId()) == null){
					System.out.println("Ugyldigt ID");
					sendToServer.writeBytes("RM20 8 ”Ugyldigt produktbatch nr " + "” ”” ”&3”" + '\n');
					responseFromServer = getFromServer.readLine();		
					responseFromServer = getFromServer.readLine();		
				}
				
				} while (recpt.findReceptName(proBa.getPbId()) == null);
				
				//Send text to weight
				sendToServer.writeBytes("RM20 8 ”Recept navn: " + recpt.findReceptName(retrieveIdAsInt(responseFromServer)) + "” ”” ”&3”" + '\n');
				responseFromServer = getFromServer.readLine();
				System.out.println("8 " + responseFromServer);
				System.out.println("Tryk OK ");

				//Press OK on weight
				responseFromServer = getFromServer.readLine();
				System.out.println("9" + responseFromServer);

				if(sts.checkIfDone(proBa.getPbId()) == false){
					run = true;
					while(run) {
						//Send text to weight
						sendToServer.writeBytes("RM20 8 ”Vaegten skal ubelastes” ”” ”&3”" + '\n');
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
						sendToServer.writeBytes("RM20 8 ”Placer venligst tara” ”” ”&3”" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("11.5 " + responseFromServer);

						//Send tara weight from weight and press OK
						responseFromServer = getFromServer.readLine();
						System.out.println("12" + responseFromServer);

						//Send S to weight to save tara
						sendToServer.writeBytes("S" + '\n');
						responseFromServer = getFromServer.readLine();		//Save						
						responseFromServer = strip(responseFromServer);
						afv.setTara(Double.parseDouble(responseFromServer));
						System.out.println("12.5 " + afv.getTara());

						do {
						sendToServer.writeBytes("RM20 8 ”Indtast råvareBatch nr” ”” ”&3”" + '\n');
						responseFromServer = getFromServer.readLine();
						responseFromServer = getFromServer.readLine();
						afv.setRbId(retrieveIdAsInt(responseFromServer));
						System.out.println("13.5" + afv.getRbId());

						sendToServer.writeBytes("T" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("14" + responseFromServer);	
						
						if(!raav.iterateRb(afv.getRbId())){
							System.out.println("Ugyldigt ID");
							sendToServer.writeBytes("RM20 8 ”Ugyldigt råvarebatch nr " + "” ”” ”&3”" + '\n');
							responseFromServer = getFromServer.readLine();		
							responseFromServer = getFromServer.readLine();		
						}
						
						} while (!raav.iterateRb(afv.getRbId()));
						
						
						//Send text to weight
						sendToServer.writeBytes("RM20 8 ”Placer venligst netto” ”” ”&3”" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("15 " + responseFromServer);
						//Place netto on weight and press OK
						responseFromServer = getFromServer.readLine();
						System.out.println("16" + responseFromServer);
						//Save netto
						sendToServer.writeBytes("S" + '\n');
						responseFromServer = getFromServer.readLine();		//Save
						responseFromServer = strip(responseFromServer);
						afv.setNetto(Double.parseDouble(responseFromServer));
						System.out.println("17.5" + afv.getNetto());

						sendToServer.writeBytes("T" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("18" + responseFromServer);

						sendToServer.writeBytes("RM20 8 ”Fjern venligst brutto” ”” ”&3”" + '\n');
						responseFromServer = getFromServer.readLine();
						System.out.println("19" + responseFromServer);

						//remove brutto from weight and press ok
						responseFromServer = getFromServer.readLine();
						System.out.println("20 " + responseFromServer);

						if(afv.checkTolerance(afv.getRbId(), afv.getNetto(), proBa.getPbId())) {
							//sql transaction
							ProBaKoDTO.insertProBaKomRow(proBa.getPbId(), afv.getRbId(),afv.getTara(),afv.getNetto(), foo);
							System.out.println("Success! Gemt i database.");

							sendToServer.writeBytes("RM20 8 ”Afvejnings status: OK” “” “&3”" + '\n');
							responseFromServer = getFromServer.readLine();
							System.out.println("22 " + responseFromServer);
							responseFromServer = getFromServer.readLine();

							sts.updateStatus(this, proBa, proBa.getPbId());
							if(sts.checkIfDone(proBa.getPbId())) {
								run = false;
							} else {
								run = true;
							}

						} else {
							//break loop
							System.out.println("Fejl");
							//Send error message to weight.
							sendToServer.writeBytes("RM20 8 ”Afvejnings status: Fejl” “” “&3”" + '\n');
							responseFromServer = getFromServer.readLine();
							System.out.println("22 " + responseFromServer);
							responseFromServer = getFromServer.readLine();

							System.out.println("Nom_netto: " + afv.getNom_netto(afv.getRbId(), proBa.getPbId()));

							run = false;
						}
					}
				} else {
					System.out.println("Ikke tilladt");

					sendToServer.writeBytes("RM20 8 ”Er allerede færdig!” “” “&3”" + '\n');
					responseFromServer = getFromServer.readLine();
					System.out.println("22 " + responseFromServer);
					responseFromServer = getFromServer.readLine();

					run = false;
				}
				sendToServer.writeBytes("RM20 8 ”Fortsæt? Tryk 1” “” “&3”" + '\n');
				responseFromServer = getFromServer.readLine();
				System.out.println(responseFromServer);
				responseFromServer = getFromServer.readLine();
				int respns = retrieveIdAsInt(responseFromServer);
				if (respns == 1) {
					mainRun = true;
				} else {
					//Quit weight
					sendToServer.writeBytes("Q" + '\n');
					mainRun = false;
				}
//				System.out.println("22 " + responseFromServer);
//				responseFromServer = getFromServer.readLine();
				
				//TODO
				//Stop loop
				//Send to weight "En mere?"
				//mainRun = okFromWeight()
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			sendToServer.writeBytes("RM20 8 ”Afvejnings status: Kasseret” “” “&3”" + '\n');
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

	//Converts the "int inputs" on the weight into java ints Works on pb_id and rb_id.
	public int retrieveIdAsInt(String ServerResponse) {
		String tempId = ServerResponse.split(" ")[2];
		tempId = tempId.replaceAll("\\D+","");	
		int foo = Integer.parseInt(tempId);
		return foo;
	}
}