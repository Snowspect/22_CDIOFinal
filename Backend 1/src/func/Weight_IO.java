package func;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import DTO.Afvejning ;
import DTO.Personer;
import DTO.RaavareBatch;
import data.socket.Connection;
import user.UserResources;

public class Weight_IO {
	private Connection conn;
	private Socket clientSocket;
	private DataOutputStream sendToServer;
	private BufferedReader getFromServer;
	private String responseFromServer, messageToServer, name = "Thomas", status = "";
	private Afvejning afv = new Afvejning();
	private Personer pers = new Personer ();
	private RaavareBatch raav = new RaavareBatch();
	private int id;
	private UserResources UsRe = new UserResources();
	
	
	public Weight_IO(Afvejning afv) throws UnknownHostException, IOException {
		this.afv = afv;
		conn = new Connection("127.0.0.1", 8000);
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
		//Send text to weight
		sendToServer.writeBytes("RM20 8 ”Indtast laborant nr” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();	

		//Input UserId on weight
		System.out.println("1 " + responseFromServer); //Test
		responseFromServer = getFromServer.readLine();		
		System.out.println("2 " + responseFromServer);
		System.out.println("3 " + responseFromServer.split(" ")[2]);
		
//		//Gets userId from weight-response and converts to int
//		String tempId = responseFromServer.split(" ")[2];
//		tempId = tempId.replaceAll("\\D+","");	
//		int foo = Integer.parseInt(tempId);
//		System.out.println("52 " + foo);
//		
//		//TODO get name from data. OBS Metode virker ikke
//		name = getUserNameFromId(int foo);
		
//		Send name to weight
		sendToServer.writeBytes("RM20 8 ”t Navn: " + name + "” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();		
		System.out.println("4 " + responseFromServer);
		
		//Approve
		System.out.println("Tryk OK");
		responseFromServer = getFromServer.readLine();		
		System.out.println("5 " + responseFromServer);
		
		//Send Produktbatch text to weight
		sendToServer.writeBytes("RM20 4 ”Indtast Produktbatch nr” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();		
		System.out.println("6 " + responseFromServer);
		
		//Input Produktbatch id on weight
		responseFromServer = getFromServer.readLine();		//Save
//		raav.setRbId(responseFromServer); //converts to the corresponding values if it contains chars.
		System.out.println("7 " + responseFromServer);
		
		//Send text to weight
		sendToServer.writeBytes("RM20 8 ”Vaegten skal ubelastes” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("8 " + responseFromServer);
		System.out.println("Tryk OK ");
		
		//Press OK on weight
		responseFromServer = getFromServer.readLine();
		System.out.println("9" + responseFromServer);
		
		//Send text to weight
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("10" + responseFromServer);
		
		//Place tara
		sendToServer.writeBytes("RM20 8 ”Placer venligst tara” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("11" + responseFromServer);
		
		//Send tara weight from weight and press OK
		responseFromServer = getFromServer.readLine();
		System.out.println("12" + responseFromServer);
		//Send S to weight to save tara
		sendToServer.writeBytes("S" + '\n');
		responseFromServer = getFromServer.readLine();		//Save
	//	dto.setTaraWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = strip(responseFromServer);
		afv.setTara(Double.parseDouble(responseFromServer));
		System.out.println("13" + responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("14" + responseFromServer);
		//Send text to weight
		sendToServer.writeBytes("RM20 8 ”Placer venligst netto” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("15 " + responseFromServer);
		//Place netto on weight and press OK
		responseFromServer = getFromServer.readLine();
		System.out.println("16" + responseFromServer);
		//Sav netto
		sendToServer.writeBytes("S" + '\n');
		responseFromServer = getFromServer.readLine();		//Save
	//	dto.setNetWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = strip(responseFromServer);
		afv.setNetto(Double.parseDouble(responseFromServer));
		System.out.println("17" + responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("18" + responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Fjern venligst brutto” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("19" + responseFromServer);
		//remove brutto from weight and press ok
		responseFromServer = getFromServer.readLine();
		System.out.println("20 " + responseFromServer);
		
		sendToServer.writeBytes("S" + '\n');
	//	dto.setBruttoWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = getFromServer.readLine();//Save
		responseFromServer = strip(responseFromServer);
		afv.setBrutto(Double.parseDouble(responseFromServer));
		System.out.println("21 " + responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Afvejnings status: OK” “” “&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("22 " + responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println("23 " + responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println("24 " +responseFromServer);
		afv.toString();
		
		sendToServer.writeBytes("Q" + '\n');
		
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
		
	
	//Ikke færdig 
	public String findUserName (int id) {
		this.id = id;
		for (Personer person : UsRe.getPerList()) {
			
		}
		
		return "h";
		
	}
	
}
