package rest;

import java.util.ArrayList;

import DTO.Personer;

public class UserList {
	private ArrayList <Personer> perList = new ArrayList<Personer>();

	public ArrayList<Personer> getPerList() {
		return perList;
	}

	public void setPerList(ArrayList<Personer> perList) {
		this.perList = perList;
	}

	public void addPerToList(Personer per) {
		this.perList.add(per);
	}
}
