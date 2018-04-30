package dto;

public class bruger {
	private int id;
	private String brugernavn;
	private String initial;
	private int CPR;


	public void bruger(int id, String brugernavn, String initial, int CPR) {
		this.setId(id);
		this.setBrugernavn(brugernavn);
		this.setInitial(initial);
		this.setCPR(CPR);
	}


	public int getId() {
		return this.id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getBrugernavn() {
		return brugernavn;
	}


	public void setBrugernavn(String brugernavn) {
		this.brugernavn = brugernavn;
	}


	public String getInitial() {
		return initial;
	}


	public void setInitial(String initial) {
		this.initial = initial;
	}


	public int getCPR() {
		return CPR;
	}


	public void setCPR(int CPR) {
		this.CPR = CPR;
	}
}