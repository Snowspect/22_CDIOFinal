package data.socket;

import java.io.*;
import java.net.*;

public class Connection 
{
	private BufferedReader br;
	private DataOutputStream dos;
	private int port = 8000;
	private String LocalHost = "127.0.0.1";
	private Socket clientSocket = null;

	/**
	 * Doesn't create connection but fills out values for when you want to make
	 * connection.
	 */
	public Connection(String localHost, int port)
	{
		this.LocalHost = localHost; this.port = port;		 
	}

	/**
	 * Makes connection and returns the connection socket.
	 */
	public Socket SocketConn () throws UnknownHostException, IOException
	{
		clientSocket = new Socket(LocalHost, port);
		return clientSocket;
	}
	
	public BufferedReader getReader() throws IOException {
		br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		return br;
	}
	
	public DataOutputStream getWriter() throws IOException{
		dos = new DataOutputStream(clientSocket.getOutputStream());
		return dos;
	}
}