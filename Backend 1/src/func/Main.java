package func;

import java.io.IOException;
import java.net.UnknownHostException;

import DTO.Afvejning;
import DTO.Personer;
import DTO.RaavareBatch;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Afvejning afv = new Afvejning();
		Personer pers = new Personer();
		RaavareBatch RaaBa = new RaavareBatch();
//		TCPSocket tcp = new TCPSocket();
		Weight_IO io = new Weight_IO(afv);
		io.run();
	}

}

