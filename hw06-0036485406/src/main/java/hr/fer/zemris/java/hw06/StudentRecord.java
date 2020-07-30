package hr.fer.zemris.java.hw06;

public class StudentRecord {

	private String jmbag;
	private String prezime;
	private String ime;
	private double bodoviMI;
	private double bodoviZI;
	private double bodoviLV;
	private int ocjena;

	public StudentRecord(String jmbag, String prezime, String ime, double bodoviMI, double bodoviZI, double bodoviLV,
			int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviMI = bodoviMI;
		this.bodoviZI = bodoviZI;
		this.bodoviLV = bodoviLV;
		this.ocjena = ocjena;
	}

	public String getJmbag() {
		return jmbag;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getIme() {
		return ime;
	}

	public double getBodoviMI() {
		return bodoviMI;
	}

	public double getBodoviZI() {
		return bodoviZI;
	}

	public double getBodoviLV() {
		return bodoviLV;
	}

	public double getUkupnoBodova() {
		return bodoviMI + bodoviZI + bodoviLV;
	}

	public int getOcjena() {
		return ocjena;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag + "\t");
		sb.append(prezime + "\t");
		sb.append(ime + "\t");
		sb.append(bodoviMI + "\t");
		sb.append(bodoviZI + "\t");
		sb.append(bodoviLV + "\t");
		sb.append(ocjena + "\t");
		return sb.toString();
	}
}
