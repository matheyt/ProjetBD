package DonnePOJO;

import oracle.sql.TIMESTAMP;

public class Place {

	private int idPlace;
	private String noPlace;
	private Classe classe;
	private Position position;
	private int prix;
	private String noVol;
	private TIMESTAMP dateDepart;
	private int noResa;
	
	public Place(int idPlace, String noPlace, Classe classe, Position position, int prix, String noVol, TIMESTAMP dateDepart, int noResa) {
		this.idPlace = idPlace;
		this.noPlace = noPlace;
		this.classe = classe;
		this.position = position;
		this.prix = prix;
		this.noVol = noVol;
		this.dateDepart = dateDepart;
		this.noResa = noResa;
	}

	public int getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(int idPlace) {
		this.idPlace = idPlace;
	}
	
	public String getNoPlace() {
		return noPlace;
	}

	public void setNoPlace(String noPlace) {
		this.noPlace = noPlace;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public String getNoVol() {
		return noVol;
	}

	public void setNoVol(String noVol) {
		this.noVol = noVol;
	}

	public TIMESTAMP getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(TIMESTAMP dateDepart) {
		this.dateDepart = dateDepart;
	}

	public int getNoResa() {
		return noResa;
	}

	public void setNoResa(int noResa) {
		this.noResa = noResa;
	}

	@Override
	public String toString() {
		return "Place [noPlace=" + noPlace + ", classe=" + classe + ", position=" + position + ", prix=" + prix
				+ ", noVol=" + noVol + ", DateDepart=" + dateDepart + ", noResa=" + noResa + "]";
	}
	
	
}
