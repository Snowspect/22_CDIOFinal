package func;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import DTO.Afvejning ;
import DTO.Personer;
import DTO.RaavareBatch;
import JDBC.Connector;
import user.UserResources;

public class Weight_IO {
	private data.socket.Connection conn;
	private Socket clientSocket;
	private DataOutputStream sendToServer;
	private BufferedReader getFromServer;
	private String responseFromServer, messageToServer, status = "";
	private Afvejning afv = new Afvejning();
	private Personer pers = new Personer ();
	private RaavareBatch raav = new RaavareBatch();
	private int id;
	private UserResources UsRe = new UserResources();



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

			int foo = retrieveIdAsInt(responseFromServer);

			//		Send name to weight
			sendToServer.writeBytes("RM20 8 ”t Navn: " + findUserName(foo) + "” ”” ”&3”" + '\n');
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
			int tempId = retrieveIdAsInt(responseFromServer);
			String str = findReceptName(tempId);
			System.out.println("recept navn " + str);

			//raav.setRbId(responseFromServer); //converts to the corresponding values if it contains chars.
			System.out.println("7 " + responseFromServer);

			//Send text to weight
			sendToServer.writeBytes("RM20 8 ”Recept navn: " + findReceptName(retrieveIdAsInt(responseFromServer)) + "” ”” ”&3”" + '\n');
			responseFromServer = getFromServer.readLine();
			System.out.println("8 " + responseFromServer);
			System.out.println("Tryk OK ");

			
			//Start loop
			
			
			//Press OK on weight
			responseFromServer = getFromServer.readLine();
			System.out.println("9" + responseFromServer);
			
			
			int tempStat = setStatus(tempId);
			System.out.println("Status blev sat til " + tempStat);
			if (tempStat == 2) {
				System.out.println("Ikke tilladt");
			//break i løkke
			}
			
			//Send text to weight
			sendToServer.writeBytes("RM20 8 ”Vaegten skal ubelastes” ”” ”&3”" + '\n');
			responseFromServer = getFromServer.readLine();
			System.out.println("10" + responseFromServer);
			responseFromServer = getFromServer.readLine();
			System.out.println("Tryk OK ");
			//		System.out.println("10" + responseFromServer);

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
			//	dto.setTaraWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
			responseFromServer = strip(responseFromServer);
			afv.setTara(Double.parseDouble(responseFromServer));
			System.out.println("12.5 " + afv.getTara());
			System.out.println("13 " + responseFromServer);

			sendToServer.writeBytes("RM20 8 ”Indtast råvareBatch nr” ”” ”&3”" + '\n');
			responseFromServer = getFromServer.readLine();
			responseFromServer = getFromServer.readLine();
			afv.setRbId(retrieveIdAsInt(responseFromServer));
			System.out.println("13.5" + afv.getRbId());
			
			
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

			
			
//			sendToServer.writeBytes("S" + '\n');
//			//	dto.setBruttoWeight(Double.parseDouble(responseFromServer.replace("kg", "").replace("S", "")));
//			responseFromServer = getFromServer.readLine();//Save
//			responseFromServer = strip(responseFromServer);
//			afv.setBrutto(Double.parseDouble(responseFromServer));
//			System.out.println("21 " + responseFromServer);

			
			if(checkTolerance(afv.getRbId(), afv.getNetto())) {
				//sql transaction
				insertProBaKomRow(tempId, afv.getRbId(),afv.getTara(),afv.getNetto(), foo);
				System.out.println("Success");
			} else {
				//break loop
				System.out.println("Fejl");
			}
			
			sendToServer.writeBytes("RM20 8 ”Afvejnings status: OK” “” “&3”" + '\n');
			responseFromServer = getFromServer.readLine();
			System.out.println("22 " + responseFromServer);
			responseFromServer = getFromServer.readLine();
//			System.out.println("23 " + responseFromServer);

			
			//Stop loop
			
			
			sendToServer.writeBytes("T" + '\n');
			responseFromServer = getFromServer.readLine();
			System.out.println("24 " +responseFromServer);
		
//			afv.toString();

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


	//Finds the username in the database with the ID given from the weight.
	public String findUserName (int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		String name = null;
		PreparedStatement getUserName = null;
		ResultSet rs = null;

		String getName = "Select opr_navn from personer natural join roller where rolle_id = ?";


		try {
			getUserName = sqlCon.prepareStatement(getName);

			getUserName.setInt(1, id);
			rs = getUserName.executeQuery();
			if(rs.first()) {
				name = rs.getString("opr_navn");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(getUserName != null) {
				getUserName.close();
			}
		}
		return name;
	}

	public int retrieveIdAsInt(String ServerResponse) {
		String tempId = ServerResponse.split(" ")[2];
		tempId = tempId.replaceAll("\\D+","");	
		int foo = Integer.parseInt(tempId);
		return foo;

	}


	public String findReceptName (int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		String recept = null;
		PreparedStatement getReceptName = null;
		ResultSet rs = null;

		String getRecept = "Select recept_navn from produktbatch NATURAL JOIN recept where pb_id = ? group by recept_navn";


		try {
			getReceptName = sqlCon.prepareStatement(getRecept);

			getReceptName.setInt(1, id);
			rs = getReceptName.executeQuery();
			if(rs.first()) {
				recept = rs.getString("recept_navn");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(getReceptName != null) {
				getReceptName.close();
			}
		}
		return recept;
	}

	public int setStatus(int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		int status = 0;
		PreparedStatement checkStatus = null;
		ResultSet rs = null;

		String checkProduktStatus = "SELECT status FROM produktbatch WHERE pb_id = ? ";


		try {
			checkStatus = sqlCon.prepareStatement(checkProduktStatus);

			checkStatus.setInt(1,id);
			rs = checkStatus.executeQuery();
			if(rs.first()) {
				status = rs.getInt("status");	
				if (status != 2) {
					status = 1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(checkStatus != null) {
				checkStatus.close();
			}
		}
		return status;
	}
	
	public int updateStatus(int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		int status = 0;
		int update = 0;
		PreparedStatement updateStatus1 = null;
		PreparedStatement updateStatus2 = null;
		ResultSet rs = null;

		String updateProduktStatus1 = "UPDATE produktbatch SET status = 1 WHERE pb_id = ?";
		String updateProduktStatus2 = "UPDATE produktbatch SET status = 2 WHERE pb_id = ?";

		try {
			
			
			updateStatus = sqlCon.prepareStatement(updateProduktStatus);
			
			updateStatus.setInt(1,id);
			rs = updateStatus.executeQuery();
			if(rs.first()) {
				status = rs.getInt("status");	
				if (status != 2) {
					updateStatus = sqlCon.prepareStatement(updateProduktStatus);
					updateStatus.setInt(1, id);
					update = updateStatus.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(updateStatus != null) {
				updateStatus.close();
			}
		}
		return update;
	}
	

	public boolean checkTolerance(int rb_id, double netto) throws SQLException {
		Connection sqlCon = Connector.getConn();

		double tolerance = 0.0;
		PreparedStatement checkTolerance = null;
		ResultSet rs = null;
		boolean flag = false; 

		String checkRaavareTolerance = "SELECT DISTINCT tolerance FROM raavarebatch NATURAL JOIN receptkomponent WHERE rb_id = ?;";

		try {
			checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);

			checkTolerance.setInt(1, rb_id);
			rs = checkTolerance.executeQuery();
			if(rs.first()) {
				tolerance = rs.getDouble(1);
				checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);
//				checkTolerance.setInt(1, rb_id);
				if (netto * (1 - tolerance) <= getNom_netto(rb_id, afv.getUserId()) && getNom_netto(rb_id, afv.getUserId()) <= netto * (1 + tolerance) ) {
					flag = true;
					return flag;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(checkTolerance != null) {
				checkTolerance.close();
			}
		}
		return flag;
	}

	public void insertProBaKomRow(int pd_id, int rb_id, double tara, double netto, int oprId) throws SQLException {
		Connection sqlCon = Connector.getConn();

		PreparedStatement row = null;
		ResultSet rs = null;

		String insertRow = "CALL MakeProBaKompRow(?,?,?,?,?) ";

		try {
			row = sqlCon.prepareStatement(insertRow);

			row.setInt(1,pd_id);
			row.setInt(2,rb_id);
			row.setDouble(3,tara);
			row.setDouble(4,netto);
			row.setInt(5,oprId);
			
			row.execute();
			if(rs.first()) {
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(row != null) {
				row.close();
			}
		}
	}

	public double getNom_netto (int rb_id, int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		double netto = 0.0;
		PreparedStatement getNom_netto = null;
		ResultSet rs = null;

		String getNetto = "SELECT nom_netto FROM receptkompoent WHERE rb_id = ? AND pb_id = ?";


		try {
			getNom_netto = sqlCon.prepareStatement(getNetto);

			getNom_netto.setInt(1, rb_id);
			getNom_netto.setInt(2, pb_id);
			rs = getNom_netto.executeQuery();
			if(rs.first()) {
				netto = rs.getDouble(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(getNom_netto != null) {
				getNom_netto.close();
			}
		}
		return netto;
	}
	
	
}

