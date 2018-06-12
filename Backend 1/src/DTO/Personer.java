package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import JDBC.Connector;

public class Personer {
	private int userId;
	private String userName;
	private String ini;
	private String cpr;
	private String roles;
	private boolean status;

	//tom constructor for god ordens skyld
	public Personer() {
	}

	public Personer(int userId, String userName, String ini, String cpr, String roles, boolean status) {
		this.userId = userId;
		this.userName = userName;
		this.ini = ini;
		this.cpr = cpr;
		this.roles = roles;
		this.status = status;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String toString() {
		String str = null;
		str = "ID: " + userId + " , userName: " + userName + " , ini: " + ini + " , cpr: " + cpr + " , roles " + roles + ", status: " + status;
		return str;
	}


}
