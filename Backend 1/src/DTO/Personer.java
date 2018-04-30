package DTO;

import java.util.List;

public class Personer {
	private int userId;
	private String userName;
	private String ini;
	private String cpr;
	private String password;
	private List <String> roles;
	
	//Stig sagde der også skulle være en tom constructor
	public Personer() {
	}

	public Personer(int userId, String userName, String ini, String cpr, String password, List <String> roles) {
		this.userId = userId;
		this.userName = userName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.roles = roles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public String toString() {
		String str = null;
		str = "ID: " + userId + " , userName: " + userName + " , ini: " + ini + " , cpr: " + cpr + " , password: " + password + " , roles; " + roles;
		return str;
		
	}
}
