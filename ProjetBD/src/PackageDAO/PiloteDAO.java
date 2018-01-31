package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class PiloteDAO extends DAO<Pilote> {

	public PiloteDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Pilote obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Pilote (idPerso) VALUES (" + obj.getIdPerso()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Pilote obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Pilote WHERE idPerso = " + obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Pilote obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Pilote find(Object[] tab) {
		int idPers = (int) tab[0];
		Pilote Pi = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Pilote WHERE idPerso = " + idPers);
		      if(result.first())
		      {
		    	  Pi = new Pilote(result.getInt("idPerso"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return Pi;
	}

}
