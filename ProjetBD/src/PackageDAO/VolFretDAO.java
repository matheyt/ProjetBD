package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;
import oracle.sql.TIMESTAMP;

public class VolFretDAO extends DAO<VolFret> {

	public VolFretDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(VolFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO VolFret (volumeMin, poidsMin, prixMetreCube, noVol, datedepart) VALUES (" + obj.getVolumeMin()+", " + obj.getPoidsMin()+", "+obj.getPrixMetreCube()
			        +", '"+obj.getNoVol()+"', TIMESTAMP '"+obj.getDateDepart()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(VolFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM VolFret WHERE noVol = '" + obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(VolFret obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE VolFret SET volumeMin = " + obj.getVolumeMin()+", poidsMin = "+obj.getPoidsMin()
			        +", prixMetreCube = "+obj.getPrixMetreCube()
			        +" WHERE noVol = '"+obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public VolFret find(Object[] tab) {
		String novol = (String) tab[0];
		TIMESTAMP dt = (TIMESTAMP) tab[1];
		VolFret vol = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM VolFret WHERE noVol = '" + novol+"' AND datedepart = TIMESTAMP '" + dt+"'");
		      if(result.first())

		          vol = new VolFret(result.getInt("volumeMin"),result.getInt("poidsMin"),result.getFloat("prixMetreCube"),result.getString("noVol"),new TIMESTAMP(result.getString("dateDepart")));         
		          
		    } catch (SQLException e) {

		      e.printStackTrace();

		    }
		// TODO Auto-generated method stub
		return vol;
	}

	
}
