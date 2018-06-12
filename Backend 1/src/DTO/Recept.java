package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import JDBC.Connector;

public class Recept {
	/** recept id i området 1-99999999 */
	private int receptId;

	/** Receptnavn min. 2 max. 20 karakterer */
	private String receptNavn;

	/** Ingredienser i recept */
	private ArrayList<ReceptKompDTO> receptKomponent = new ArrayList<>();

	public Recept() {}
	
	public Recept(int receptId, String receptNavn) {
		// TODO Auto-generated constructor stub
		this.receptId = receptId;
		this.receptNavn = receptNavn;
	}


	public String toString() {
		return "receptId: " + receptId + ", receptNavn: " + receptNavn + 
				", recept komponents: " + Arrays.toString(receptKomponent.toArray());
	}

	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public String getReceptNavn() {
		return receptNavn;
	}

	public void setReceptNavn(String receptNavn) {
		this.receptNavn = receptNavn;
	}

	public ArrayList<ReceptKompDTO> getReceptKomponent() {
		return receptKomponent;
	}

	public void setReceptKomponent(ArrayList<ReceptKompDTO> receptKomponent) {
		this.receptKomponent = receptKomponent;
	}

}
