package func;

import java.io.IOException;
import java.net.UnknownHostException;

import DTO.Afvejning;
import DTO.Personer;
import DTO.RaavareBatch;
import user.UserResources;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Afvejning afv = new Afvejning();
		UserResources UsRe = new UserResources();
		UsRe.getPerList().add(new Personer(12, "Hans", "hs", "87654321", "kode", "admin"));
//		Personer pers = new Personer();
//		RaavareBatch RaaBa = new RaavareBatch();
//		TCPSocket tcp = new TCPSocket();
		Weight_IO io = new Weight_IO(afv);
		io.run();
	}

}

