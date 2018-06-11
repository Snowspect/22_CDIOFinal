package func;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.Connection;

import DTO.Afvejning ;
import DTO.Personer;
import DTO.Produktbatch;
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
	private Produktbatch proBa = new Produktbatch();
	private int id;
	private UserResources UsRe = new UserResources();
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

				if(findUserName(foo) == null){
					System.out.println("Ugyldigt ID");
					sendToServer.writeBytes("RM20 8 ”Ugyldigt laborant nr" + "” ”” ”&3”" + '\n');
					responseFromServer = getFromServer.readLine();		
					responseFromServer = getFromServer.readLine();		
				}

			} while (findUserName(foo) == null);


			//Send name to weight
			sendToServer.writeBytes("RM20 8 ”t Navn: " + findUserName(foo) + "” ”” ”&3”" + '\n');

			System.out.println(findUserName(foo));
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
				sendToServer.writeBytes("RM20 4 ”Indtast Produktbatch nr” ”” ”&3”" + '\n');
				responseFromServer = getFromServer.readLine();
				System.out.println("6 " + responseFromServer);

				//Input Produktbatch id on weight
				responseFromServer = getFromServer.readLine();		//Save
				proBa.setPbId(retrieveIdAsInt(responseFromServer)); //Sets productBatch id in DTO.
				String str = findReceptName(proBa.getPbId());
				System.out.println("recept navn " + str);
				System.out.println("7 " + responseFromServer);

				//Send text to weight
				sendToServer.writeBytes("RM20 8 ”Recept navn: " + findReceptName(retrieveIdAsInt(responseFromServer)) + "” ”” ”&3”" + '\n');
				responseFromServer = getFromServer.readLine();
				System.out.println("8 " + responseFromServer);
				System.out.println("Tryk OK ");

				//Press OK on weight
				responseFromServer = getFromServer.readLine();
				System.out.println("9" + responseFromServer);

				if(checkIfDone(proBa.getPbId()) == false){
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

						if(checkTolerance(afv.getRbId(), afv.getNetto(), proBa.getPbId())) {
							//sql transaction
							insertProBaKomRow(proBa.getPbId(), afv.getRbId(),afv.getTara(),afv.getNetto(), foo);
							System.out.println("Success! Gemt i database.");

							sendToServer.writeBytes("RM20 8 ”Afvejnings status: OK” “” “&3”" + '\n');
							responseFromServer = getFromServer.readLine();
							System.out.println("22 " + responseFromServer);
							responseFromServer = getFromServer.readLine();

							updateStatus(proBa.getPbId());
							if(checkIfDone(proBa.getPbId())) {
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

							System.out.println("Nom_netto: " + getNom_netto(afv.getRbId(), proBa.getPbId()));

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
				//TODO
				//Stop loop
				//Send to weight "En mere?"
				//mainRun = okFromWeight()
			}

			//Quit weight
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

		String getName = "Select opr_navn from personer natural join roller where rolle_id = ?;";

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

	//Converts the "int inputs" on the weight into java ints Works on pb_id and rb_id.
	public int retrieveIdAsInt(String ServerResponse) {
		String tempId = ServerResponse.split(" ")[2];
		tempId = tempId.replaceAll("\\D+","");	
		int foo = Integer.parseInt(tempId);
		return foo;
	}

	//Returns the recept name given a pb_id
	public String findReceptName (int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		String recept = null;
		PreparedStatement getReceptName = null;
		ResultSet rs = null;

		String getRecept = "Select recept_navn from produktbatch NATURAL JOIN recept where pb_id = ? group by recept_navn;";

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

	//Checks the status of a produktbatch given the pb_id. Then returns it.
	public int checkStatus(int id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		int status = 0;
		PreparedStatement checkStatus = null;
		ResultSet rs = null;

		String checkProduktStatus = "SELECT status FROM produktbatch WHERE pb_id = ?;";

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

	//Simply sets the status of a produktbatch to 1 or 2.
	public void setStatus(int id, int stat) throws SQLException {
		Connection sqlCon = Connector.getConn();

		int status = 0;
		int update = 0;
		PreparedStatement setStatus1 = null;
		PreparedStatement setStatus2 = null;

		String setProduktStatus1 = "UPDATE produktbatch SET status = 1 WHERE pb_id = ?;";
		String setProduktStatus2 = "UPDATE produktbatch SET status = 2 WHERE pb_id = ?;";

		try {
			if(stat == 1) {
				setStatus1 = sqlCon.prepareStatement(setProduktStatus1);
				setStatus1.setInt(1,id);
				setStatus1.execute();
			} else {
				setStatus2 = sqlCon.prepareStatement(setProduktStatus2);
				setStatus2.setInt(1,id);
				setStatus2.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(setStatus1 != null || setStatus2 != null) {
				setStatus1.close();
				setStatus2.close();
			}
		}
	}

	/*Checks if a produktbatch is done by comparing to SQL columns converted into java arrays 
	  using the values of raavare_id and comparing them value by value*/
	public boolean checkIfDone(int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		PreparedStatement getWeighed = null;
		PreparedStatement getToWeigh = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int count = 0;

		//Det vi har vejet	
		String getWeighedItems = "SELECT raavare_id FROM raavarebatch WHERE rb_id IN (SELECT rb_id FROM produktbatchkomponent WHERE pb_id = ?);";
		//Det vi skal veje
		String getToWeighItems = "SELECT raavare_id FROM receptkomponent WHERE recept_id = (SELECT recept_id FROM produktbatch WHERE pb_id = ?);";

		try {
			//Get first array from database
			getWeighed = sqlCon.prepareStatement(getWeighedItems);
			getWeighed.setInt(1,pb_id);
			rs1 = getWeighed.executeQuery();

			// Go to the last row 
			rs1.last(); 
			int rowCount = rs1.getRow(); 

			// Reset row before iterating to get data 
			rs1.beforeFirst();

			int [] checkerArr1 = new int [rowCount];
			int arrayCount = 0;

			while(rs1.next()) {
				checkerArr1[arrayCount] = rs1.getInt(1);
				arrayCount++;
			}
			System.out.println("Arary 1: \n" + Arrays.toString(checkerArr1));

			//Get second array from database
			getToWeigh = sqlCon.prepareStatement(getToWeighItems);
			getToWeigh.setInt(1,pb_id);
			rs2 = getToWeigh.executeQuery();

			// Go to the last row 
			rs2.last(); 
			int rowCount2 = rs2.getRow(); 

			// Reset row before iterating to get data 
			rs2.beforeFirst();

			int [] checkerArr2 = new int [rowCount2];
			int arrayCount2 = 0;

			while(rs2.next()) {
				checkerArr2[arrayCount2] = rs2.getInt(1);
				arrayCount2++;
			}
			System.out.println("Arary 2: \n" + Arrays.toString(checkerArr2));

			//compare arrays
			for (int i = 0; i < checkerArr1.length; i++) {
				for(int j = 0; j < checkerArr2.length; j++) {
					if(checkerArr1[i] == checkerArr2[j]) {
						count++;
					}
				}
			}
			if(count == checkerArr2.length) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( getWeighed != null || getToWeigh != null) {
				getWeighed.close();
			}
		}
		return false;
	}

	//A method that checks if a status needs updating by calling other methods.
	public int updateStatus(int id) throws SQLException {
		Connection sqlCon = Connector.getConn();
		try {
			switch (checkStatus(id)) {
			case 0:
				setStatus(id, 1);			
				break;
			case 1:
				if (checkIfDone(id)){
					setStatus(id, 2);
				} 
				break;
			case 2:
				break;
			default:
				System.out.println("WHAT!!??");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkStatus(id);
	}

	//Checks if the weighed netto is within tolerance for that raavare. 
	public boolean checkTolerance(int rb_id, double netto, int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		double tolerance = 0.0;
		PreparedStatement checkTolerance = null;
		ResultSet rs = null;

		String checkRaavareTolerance = "SELECT DISTINCT tolerance FROM raavarebatch NATURAL JOIN receptkomponent WHERE rb_id = ?;";

		try {
			checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);

			checkTolerance.setInt(1, rb_id);
			rs = checkTolerance.executeQuery();
			if(rs.first()) {
				tolerance = rs.getDouble(1);
				checkTolerance = sqlCon.prepareStatement(checkRaavareTolerance);
				if (netto >= getNom_netto(rb_id, pb_id) * (1 - tolerance) && netto <= getNom_netto(rb_id, pb_id) * (1 + tolerance)) {
					return true;
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
		return false;
	}

	//Calls a SP in the SQL that will insert a row in produktbatchkomp 
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(row != null) {
				row.close();
			}
		}
	}

	//Returns nom_netto when given a pb_id and a rb_id.
	public double getNom_netto (int rb_id, int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		double netto = 0.0;
		PreparedStatement getNom_netto = null;
		ResultSet rs = null;

		String getNetto = "SELECT nom_netto FROM receptkomponent NATURAL JOIN produktbatch NATURAL JOIN raavarebatch WHERE rb_id=? AND pb_id = ?;";

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