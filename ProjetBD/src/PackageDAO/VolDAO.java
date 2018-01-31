package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.Vol;
import oracle.sql.TIMESTAMP;

public class VolDAO extends DAO<Vol> {

	public VolDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Vol obj) {
		// TODO Auto-generated method stub
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Vol (noVol, datedepart, aeroOrigine, aeroDestination, duree, distance, arrive, noAvion) VALUES ('" + obj.getNoVol()+"', TIMESTAMP '" + obj.getDateDepart()+"', '"+obj.getAeroOrigine()
			        +"', '"+obj.getAeroDestination()+"', "+obj.getDuree()+", "+obj.getDistance()+", "+obj.isArrive()+", "+obj.getNoAvion()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Vol obj) {
		// TODO Auto-generated method stub
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Vol WHERE noVol = '" + obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Vol obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Vol SET aeroOrigine = '" + obj.getAeroOrigine()+"', aeroDestination = '"+obj.getAeroDestination()
			        +"', duree = "+obj.getDuree()+", distance = "+obj.getDistance()+", arrive = "+obj.isArrive()+", noAvion = "+obj.getNoAvion()
			        +" WHERE noVol = '"+obj.getNoVol()+"' AND datedepart = TIMESTAMP '"+obj.getDateDepart()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Vol find(Object[] T) {
		String novol = (String) T[0];
		TIMESTAMP dt = (TIMESTAMP) T[1];
		Vol vol = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Vol WHERE noVol = '" + novol+"' AND datedepart =TIMESTAMP '" + dt+"'");
		      if(result.first())

		          vol = new Vol(result.getString("noVol"), new TIMESTAMP(result.getString("dateDepart")),result.getString("aeroOrigine"),result.getString("aeroDestination"),result.getInt("duree"),result.getInt("distance"),result.getInt("arrive"),result.getInt("noAvion"));         
		          
		    } catch (SQLException e) {

		      e.printStackTrace();

		    }
		return vol;
	}


}
