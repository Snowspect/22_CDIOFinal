package DTO;

import java.util.ArrayList;
import java.util.Arrays;

public class Recept {
	/** recept id i området 1-99999999 */
	private int receptId;

	/** Receptnavn min. 2 max. 20 karakterer */
	private String receptNavn;

	/** Ingredienser i recept */
	private ArrayList<Ingrediens> ingrediens;

	public Recept(int receptId, String receptNavn) {
		// TODO Auto-generated constructor stub
		this.receptId = receptId;
		this.receptNavn = receptNavn;
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

	public ArrayList<Ingrediens> getIngrediens() {
		return ingrediens;
	}

	public void setIngrediens(ArrayList<Ingrediens> ingrediens) {
		this.ingrediens = ingrediens;
	}
	public String toString() {
		return "receptId: " + receptId + ", receptNavn: " + receptNavn + 
				", ingredienses: " + Arrays.toString(ingrediens.toArray());
	}
}
