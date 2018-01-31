package PackageDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;
import oracle.sql.TIMESTAMP;

public class ReserverFretDAO extends DAO<ReserverFret> {

	public ReserverFretDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(ReserverFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO ReserverFret (volumeResa, poidsResa, noResa, noVol, datedepart) VALUES (" + obj.getVolumeResa()+", " + obj.getPoidsResa()+", "+obj.getNoResa()
			        +", '"+obj.getNoVol()+"', TIMESTAMP '"+obj.getDateDepart()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(ReserverFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM ReserverFret WHERE noResa = " + obj.getNoResa()+" AND noVol = '"+obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(ReserverFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE ReserverFret SET volumeResa = " + obj.getVolumeResa()+", poidsResa = "+obj.getPoidsResa()
			        +" WHERE noResa = "+obj.getNoResa()+" AND noVol = '"+obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ReserverFret find(Object[] tab) {
		int resa = (int) tab[0];
		String NVol = (String) tab[1];
		Date dt = (Date) tab[2];
		ReserverFret rF = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM ReserverFret WHERE noResa = " + resa + " AND noVol = '"+ NVol+"' AND dateDepart = TIMESTAMP '"+dt+"'");
		      if(result.first())
		      {
		    	  rF = new ReserverFret(result.getInt("VolumeResa"),result.getInt("poidsResa"),result.getInt("noResa"),result.getString("noVol"),new TIMESTAMP(result.getString("dateDepart")));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return rF;
	}

}
