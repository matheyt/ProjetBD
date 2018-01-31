package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class AvionPassagerDAO extends DAO<AvionPassager> {

	public AvionPassagerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(AvionPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO AvionPassager (nbPlaces, nbPlacesEco, nbPlacesPremiere, nbPlacesAffaire, noAvion) VALUES (" + obj.getNbPlaces()+", " + obj.getNbPlacesEco()+", "+obj.getNbPlacesPremiere()
			        +", "+obj.getNbPlacesAffaire()+", "+obj.getNoAvion()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(AvionPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM AvionPassager WHERE noAvion = " + obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(AvionPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE AvionPassager SET nbPlaces = " + obj.getNbPlaces()+", nbPlacesEco = "+obj.getNbPlacesEco()
			        +", nbPlacesPremiere = "+obj.getNbPlacesPremiere()+", nbPlacesAffaire = "+obj.getNbPlacesAffaire()
			        +" WHERE noAvion = "+obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public AvionPassager find(Object[] tab) {
		int noAvion = (int) tab[0];
		AvionPassager AP=null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AvionPassager WHERE noVol = " + noAvion);
		      if(result.first())
		      {
		    	  AP = new AvionPassager(result.getInt("nbPlaces"),result.getInt("nbPlacesEco"),result.getInt("nbPlacesPremiere"), result.getInt("nbPlacesAffaire"), result.getInt("noAvion"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();

		    }
				
		return AP;
	}

}
