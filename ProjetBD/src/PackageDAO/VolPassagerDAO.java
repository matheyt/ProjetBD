package PackageDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.Vol;
import DonnePOJO.VolPassager;
import oracle.sql.TIMESTAMP;

public class VolPassagerDAO extends DAO<VolPassager> {

	public VolPassagerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(VolPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO VolPassager (placesMinEco, placesMinPremiere, placesMinAffaire, noVol, datedepart) VALUES (" + obj.getPlacesMinEco()+", " + obj.getPlacesMinPremiere()+", "+obj.getPlacesMinAffaire()
			        +", '"+obj.getNoVol()+"', TIMESTAMP '"+obj.getDateDepart()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(VolPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM VolPassager WHERE noVol = '" + obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(VolPassager obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE VolPassager SET placesMinEco = " + obj.getPlacesMinEco()+", placesMinPremiere = "+obj.getPlacesMinPremiere()
			        +", placesMinAffaire = "+obj.getPlacesMinAffaire()
			        +" WHERE noVol = '"+obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public VolPassager find(Object[] tab) {
		String novol = (String) tab[0];
		Date dt = (Date) tab[1];
		VolPassager vol = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM VolPassager WHERE noVol = '" + novol+"' AND dateDepart = TIMESTAMP '" + dt+"'");
		      if(result.first())

		          vol = new VolPassager(result.getInt("placesMinEco"),result.getInt("placesMinPremiere"),result.getInt("placesMinAffaire"),result.getString("noVol"),new TIMESTAMP(result.getString("dateDepart")));         
		          
		    } catch (SQLException e) {

		      e.printStackTrace();

		    }
		// TODO Auto-generated method stub
		return vol;
	}

}
