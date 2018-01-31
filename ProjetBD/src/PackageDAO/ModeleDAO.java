package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class ModeleDAO extends DAO<Modele> {

	public ModeleDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Modele obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Modele (noModele, nbPilotes, rayon, volumeMax, poidsMax, nbPlaces, nbPlacesEco, nbPlacesPremiere, nbPlacesAffaire) VALUES ('" + obj.getNoModele()+"', " + obj.getNbPilotes()+", "+obj.getRayon()
			        +", "+obj.getVolumeMax()+", "+obj.getPoidsMax()+", "+obj.getNbPlaces()+", "+obj.getNbPlacesEco()
			        +", "+obj.getNbPlacesPremiere()+", "+obj.getNbPlacesAffaire()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Modele obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Modele WHERE noModele = '" + obj.getNoModele()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Modele obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Modele SET nbPilotes = " + obj.getNbPilotes()+", rayon = "+obj.getRayon()
			        +", volumeMax = "+obj.getVolumeMax()+", poidsMax = "+obj.getPoidsMax()+", nbPlaces = "+obj.getNbPlaces()
			        +", nbPlacesEco = "+obj.getNbPlacesEco()+", nbPlacesPremiere = "+obj.getNbPlacesPremiere()
			        +", nbPlacesAffaire = "+obj.getNbPlacesAffaire()
			        +" WHERE noModele = '"+obj.getNoModele()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Modele find(Object[] tab) {
		String noMod = (String) tab[0];
		Modele Md = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Modele WHERE noModele = '" + noMod +"'");
		      if(result.first())
		      {
		    	  Md = new Modele(result.getString("noModele"),result.getInt("nbPilotes"),result.getInt("rayon"),result.getInt("volumeMax"),result.getInt("poidsMax"),result.getInt("nbPlaces"),result.getInt("nbPlacesEco"),result.getInt("nbPlacesPremiere"),result.getInt("nbPlacesAffaire"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return Md;
	}

}
