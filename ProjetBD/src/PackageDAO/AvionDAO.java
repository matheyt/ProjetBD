package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class AvionDAO extends DAO<Avion> {

	public AvionDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Avion obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Avion (noAvion, rayon, noModele) VALUES (" + obj.getNoAvion()+", " + obj.getRayon()+", '"+obj.getNoModele()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Avion obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Avion WHERE noAvion = " + obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Avion obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Avion SET rayon = " + obj.getRayon()+", noModele = '"+obj.getNoModele()
			        +"' WHERE noAvion = "+obj.getNoAvion());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Avion find(Object[] tab) {
		int noavion = (int) tab[0];
		Avion Av = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Avion WHERE noAvion = " + noavion);
		      if(result.first())
		      {
		    	  Av = new Avion(result.getInt("noAvion"),result.getInt("rayon"),result.getString("noModele"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();

		    }
		return Av;
	}

}
