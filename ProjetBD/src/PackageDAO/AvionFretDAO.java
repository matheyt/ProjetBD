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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(AvionFret obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(AvionFret obj) {
		// TODO Auto-generated method stub
		return false;
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
