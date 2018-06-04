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

public class Weight_IO {
	private Connection conn;
	private Socket clientSocket;
	private DataOutputStream sendToServer;
	private BufferedReader getFromServer;
	private String responseFromServer, messageToServer, name = "Marcus", status = "";
	private Afvejning afv;
	private Personer pers;
	private RaavareBatch raav;
	
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
	public void run() throws IOException 
	{
	try {
		sendToServer.writeBytes("RM20 4 ”Indtast operator nr” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();		
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();		
		pers.setUserId(responseFromServer); //saves in DTO
		System.out.println(responseFromServer);
	
		sendToServer.writeBytes("RM20 8 ”t Navn: " + name + "” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();		
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();		
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 4 ”Indtast batch nr” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();		
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();		//Save
		raav.setRbId(responseFromServer); //converts to the corresponding values if it contains chars.
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Vaegten skal ubelastes” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Placer venligst tara” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("S" + '\n');
		responseFromServer = getFromServer.readLine();		//Save
	//	dto.setTaraWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = strip(responseFromServer);
		afv.setTara(Double.parseDouble(responseFromServer));
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Placer venligst netto” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("S" + '\n');
		responseFromServer = getFromServer.readLine();		//Save
	//	dto.setNetWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = strip(responseFromServer);
		afv.setNetto(Double.parseDouble(responseFromServer));
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Fjern venligst brutto” ”” ”&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("S" + '\n');
	//	dto.setBruttoWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
		responseFromServer = getFromServer.readLine();//Save
		responseFromServer = strip(responseFromServer);
		afv.setBrutto(Double.parseDouble(responseFromServer));
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("RM20 8 ”Afvejnings status: OK” “” “&3”" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
		
		sendToServer.writeBytes("T" + '\n');
		responseFromServer = getFromServer.readLine();
		System.out.println(responseFromServer);
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
}
