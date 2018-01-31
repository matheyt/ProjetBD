package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class AvionFretDAO extends DAO<AvionFret> {

	public AvionFretDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(AvionFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO AvionFret (volumeMax, poidsMax, noAvion) VALUES (" + obj.getVolumeMax()+", " + obj.getPoidsMax()+", "+obj.getNoAvion()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(AvionFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM AvionFret WHERE noAvion = " + obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(AvionFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE AvionFret SET volumeMax = " + obj.getVolumeMax()+", poidsMax = "+obj.getPoidsMax()	
			        +" WHERE noAvion = "+obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public AvionFret find(Object[] tab) {
		int noAvion = (int) tab[0];
		AvionFret Af=null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM AvionFret WHERE noVol = " + noAvion);
		      if(result.first())
		      {
		    	  Af = new AvionFret(result.getInt("volumeMax"),result.getInt("poidsMax"),result.getInt("noAvion"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();

		    }
				
		return Af;
	}

}
