package func;

import DTO.Personer;
import user.ReceptResources;
import user.UserResources;

public class testMain {

	public static void main(String[] args) {
		UserResources UsRe = new UserResources();
		UsRe.getPerList().add(new Personer(22, "Hans", "hs", "87654321", "kode", "admin"));
		UsRe.getPerList().add(new Personer(22, "Hans", "hs", "87654321", "kode", "admin"));
		String str = UserResources.getPerList().toString();
		System.out.println(str);
	}

}
